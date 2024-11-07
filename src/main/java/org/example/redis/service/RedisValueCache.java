package org.example.redis.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisValueCache {
    private final ValueOperations<String, Object> valueOperations;

    public RedisValueCache(RedisTemplate<String, Object> redisTemplate) {
        valueOperations = redisTemplate.opsForValue();
    }

    public void cache(String key, Object data) {
//        valueOperations.set(key, data);
        valueOperations.set(key, data, 4000, TimeUnit.MILLISECONDS);
    }

    public Object getCachedValue(String key) {
        return valueOperations.get(key);
    }

    public void deleteCacheValue(String key) {
        valueOperations.getOperations().delete(key);
    }
}
