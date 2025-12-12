package com.bamboo.firstdemo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoubleLayerLimiter {

    @Autowired
    private SlidingWindowRateLimiter slidingWindowLimiter;
    @Autowired
    private TokenBucketLimiter tokenBucketLimiter;
    
    public DoubleLayerLimiter() {
        
        // 启动清理线程
        startCleanupThread();
    }
    
    public boolean tryAcquire(String user, String method,
                             int slidingWindowSize, int slidingMaxRequests,
                             int tokenCapacity, int tokenRefillRate) {
        
        // 第一层：滑动窗口限流（应对突发流量）
        boolean slidingAllowed = slidingWindowLimiter.tryAcquire(
            user + ":sliding",
            slidingWindowSize, 
            slidingMaxRequests  ,
                500
        );
        
        if (!slidingAllowed) {
            return false;
        }
        
        // 第二层：令牌桶限流（平滑流量）
        boolean tokenAllowed = tokenBucketLimiter.tryAcquire(
                method + ":token",
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
}