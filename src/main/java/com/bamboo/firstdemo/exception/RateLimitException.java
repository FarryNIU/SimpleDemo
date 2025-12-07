package com.bamboo.firstdemo.exception;

/**
     * 限流异常类
     */
    public class RateLimitException extends RuntimeException {
        
        public RateLimitException(String message, long waitSeconds) {
            super(message);
        }

    }
