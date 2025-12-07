package com.bamboo.firstdemo.util;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class TokenBucketLimiter{
    
    // 存储每个key的令牌桶状态
    private final Map<String, TokenBucket> bucketMap = new ConcurrentHashMap<>();
    
    static class TokenBucket {
        // 当前令牌数量
        AtomicLong tokens = new AtomicLong(0);
        // 最后更新时间
        AtomicLong lastRefillTime = new AtomicLong(System.currentTimeMillis());
        // 令牌填充速率（令牌/秒）
        final long refillRate;
        // 桶容量
        final long capacity;
        
        TokenBucket(long capacity, long refillRate) {
            this.capacity = capacity;
            this.refillRate = refillRate;
            this.tokens.set(capacity); // 初始时桶是满的
        }
    }

    public boolean tryAcquire(String key, int capacity, int refillRate) {
        TokenBucket bucket = bucketMap.computeIfAbsent(
            key, 
            k -> new TokenBucket(capacity, refillRate)
        );
        
        return tryAcquireToken(bucket, 1);
    }
    
    private boolean tryAcquireToken(TokenBucket bucket, long tokensRequested) {
        while (true) {
            long currentTime = System.currentTimeMillis();
            long lastTime = bucket.lastRefillTime.get();
            long timePassed = currentTime - lastTime;
            
            // 计算这段时间内应该添加的令牌
            long newTokens = timePassed * bucket.refillRate / 1000;
            
            if (newTokens > 0) {
                // 尝试更新时间戳
                if (bucket.lastRefillTime.compareAndSet(lastTime, currentTime)) {
                    // 添加新令牌，但不超过容量
                    long currentTokens = bucket.tokens.get();
                    long updatedTokens = Math.min(currentTokens + newTokens, bucket.capacity);
                    bucket.tokens.set(updatedTokens);
                }
            }
            
            // 尝试获取令牌
            long currentTokens = bucket.tokens.get();
            if (currentTokens < tokensRequested) {
                return false;
            }
            
            // CAS更新令牌数量
            if (bucket.tokens.compareAndSet(currentTokens, currentTokens - tokensRequested)) {
                return true;
            }
        }
    }

    public void cleanup() {
        long now = System.currentTimeMillis();
        bucketMap.entrySet().removeIf(entry -> {
            TokenBucket bucket = entry.getValue();
            // 清理超过30分钟未使用的桶
            return now - bucket.lastRefillTime.get() > TimeUnit.MINUTES.toMillis(30);
        });
    }
}