package com.bamboo.firstdemo.aspect;

import com.bamboo.firstdemo.annotation.RateLimit;
import com.bamboo.firstdemo.exception.RateLimitException;
import com.bamboo.firstdemo.util.DoubleLayerLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Order(1) // 高优先级
public class RateLimitAspect {
    
    @Autowired
    private DoubleLayerLimiter doubleLayerLimiter;
    
    @Around("@annotation(rateLimit)")
    public Object around(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        // 生成限流key（类名+方法名+参数）
        String key = generateKey(joinPoint, rateLimit);
        
        boolean allowed;
        
        if (rateLimit.enableSliding() && rateLimit.enableTokenBucket()) {
            // 双层限流
            allowed = doubleLayerLimiter.tryAcquire(
                key,
                rateLimit.slidingWindowSize(),
                rateLimit.slidingMaxRequests(),
                rateLimit.tokenCapacity(),
                rateLimit.tokenRefillRate()
            );
        } else if (rateLimit.enableSliding()) {
            // 仅滑动窗口
            // 调用滑动窗口限流器
            allowed = true; // 简化实现
        } else if (rateLimit.enableTokenBucket()) {
            // 仅令牌桶
            // 调用令牌桶限流器
            allowed = true; // 简化实现
        } else {
            allowed = true;
        }
        
        if (!allowed) {
            throw new Exception(
                rateLimit.message()
            );
        }
        
        return joinPoint.proceed();
    }
    
    private String generateKey(ProceedingJoinPoint joinPoint, RateLimit rateLimit) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();
        
        // 如果注解中指定了key，使用指定的key
        if (!rateLimit.slidingKey().isEmpty()) {
            return rateLimit.slidingKey();
        }
        
        // 默认使用类名+方法名
        return String.format("%s.%s", className, methodName);
    }
    
    private String getMethodName(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod().getName();
    }
}