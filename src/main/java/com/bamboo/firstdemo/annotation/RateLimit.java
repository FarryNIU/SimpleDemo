package com.bamboo.firstdemo.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {
    // 滑动窗口配置
    String slidingKey() default "";           // 滑动窗口key
    int slidingWindowSize() default 10;       // 窗口大小（秒）
    int slidingMaxRequests() default 100;     // 窗口内最大请求数
    
    // 令牌桶配置
    String tokenKey() default "";             // 令牌桶key
    int tokenCapacity() default 1000;         // 桶容量
    int tokenRefillRate() default 100;        // 每秒填充令牌数
    
    // 限流策略
    boolean enableSliding() default true;     // 启用滑动窗口
    boolean enableTokenBucket() default true; // 启用令牌桶
    String message() default "系统繁忙，请稍后重试"; // 限流提示
}