package com.bamboo.firstdemo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class CacheService {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    private HashOperations<String, String, Object> hashOperations;

    public void setHashMap(String key, Map<String, String> map) {
        this.hashOperations = redisTemplate.opsForHash();
        hashOperations.putAll(key, map);
        redisTemplate.expire(key, 30, TimeUnit.MINUTES);
    }

}
