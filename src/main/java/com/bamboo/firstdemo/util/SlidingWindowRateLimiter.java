package com.bamboo.firstdemo.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class SlidingWindowRateLimiter {
    
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    
    // Lua脚本保证原子性
    private static final String SLIDING_WINDOW_SCRIPT = 
        "local key = KEYS[1] " +
        "local now = tonumber(ARGV[1]) " +
        "local window = tonumber(ARGV[2]) " +
        "local limit = tonumber(ARGV[3]) " +
        "local expire = tonumber(ARGV[4]) " +
        " " +
        "local clearBefore = now - window * 1000 " +
        "redis.call('ZREMRANGEBYSCORE', key, 0, clearBefore) " +
        " " +
        "local current = redis.call('ZCARD', key) " +
        "if current < limit then " +
        "    redis.call('ZADD', key, now, now) " +
        "    redis.call('EXPIRE', key, expire) " +
        "    return 1 " +
        "else " +
        "    local oldest = redis.call('ZRANGE', key, 0, 0, 'WITHSCORES') " +
        "    if #oldest > 0 then " +
        "        return {'0', oldest[2]} " +
        "    else " +
        "        return {'0', '0'} " +
        "    end " +
        "end";
    
    private final RedisScript<List> script;
    
    public SlidingWindowRateLimiter() {
        this.script = new DefaultRedisScript<>(SLIDING_WINDOW_SCRIPT, List.class);
    }
    
    /**
     * 滑动窗口限流
     * @param key 限流key
     * @param windowSec 窗口大小（秒）
     * @param limit 窗口内允许的最大请求数
     * @param expireSec Redis key过期时间
     * @return 限流结果（是否允许 + 最早请求时间戳）
     */
    public Boolean tryAcquire(String key, int windowSec, int limit, int expireSec) {
        long now = System.currentTimeMillis();
        
        List<Object> result = redisTemplate.execute(
            script,
            Collections.singletonList(buildKey(key)),
            now,
            windowSec,
            limit,
            expireSec
        );
        
        if (result == null || result.isEmpty()) {
            return false;
        }
        
        if ("1".equals(result.get(0).toString())) {
            return true;
        } else {
            return false;
        }
    }
    
    private String buildKey(String key) {
        return "rate:sliding:" + key;
    }
    
    @Data
    @AllArgsConstructor
    public static class LimitConfig {
        private int windowSec;  // 窗口大小（秒）
        private int limit;      // 限制数量
        private int expireSec;  // 过期时间
    }

}