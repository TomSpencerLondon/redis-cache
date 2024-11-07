package org.example.redis.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisListCache {
    private RedisTemplate<String, Object> redisTemplate;
    private ListOperations<String, Object> listOperations;

    public RedisListCache(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.listOperations = redisTemplate.opsForList();
    }

    @PostConstruct
    public void setup() {
        listOperations.rightPush("key", "hello there");
        listOperations.rightPush("key2", "goodbye");

        System.out.println(listOperations.leftPop("key2"));
    }
}
