package com.bamboo.firstdemo.util;

import org.springframework.stereotype.Component;

@Component
public class DoubleLayerLimiter {
    
    private final SlidingWindowRateLimiter slidingWindowLimiter;
    private final TokenBucketLimiter tokenBucketLimiter;
    
    public DoubleLayerLimiter() {
        this.slidingWindowLimiter = new SlidingWindowRateLimiter();
        this.tokenBucketLimiter = new TokenBucketLimiter();
        
        // 启动清理线程
        startCleanupThread();
    }
    
    public boolean tryAcquire(String key, 
                             int slidingWindowSize, int slidingMaxRequests,
                             int tokenCapacity, int tokenRefillRate) {
        
        // 第一层：滑动窗口限流（应对突发流量）
        boolean slidingAllowed = slidingWindowLimiter.tryAcquire(
            key + ":sliding", 
            slidingWindowSize, 
            slidingMaxRequests  ,
                500
        );
        
        if (!slidingAllowed) {
            return false;
        }
        
        // 第二层：令牌桶限流（平滑流量）
        boolean tokenAllowed = tokenBucketLimiter.tryAcquire(
            key + ":token",
            tokenCapacity,
            tokenRefillRate
        );
        
        return tokenAllowed;
    }
    
    private void startCleanupThread() {
        Thread cleanupThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(60000); // 每分钟清理一次
                    tokenBucketLimiter.cleanup();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        cleanupThread.setDaemon(true);
        cleanupThread.start();
    }
    
    // 获取当前状态（用于监控）
    public String getStatus(String key) {
        return String.format(
            "SlidingWindow: %s, TokenBucket: %s",
            getSlidingWindowStatus(key),
            getTokenBucketStatus(key)
        );
    }
    
    private String getSlidingWindowStatus(String key) {
        // 实现状态查询逻辑
        return "active";
    }
    
    private String getTokenBucketStatus(String key) {
        // 实现状态查询逻辑
        return "active";
    }
}