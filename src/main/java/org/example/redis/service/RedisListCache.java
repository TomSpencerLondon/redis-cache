package org.example.redis.service;

import jakarta.annotation.PostConstruct;
import org.example.redis.dto.PersonDTO;
import org.example.redis.dto.RangeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class RedisListCache {
    private ListOperations<String, Object> listOperations;

    public RedisListCache(RedisTemplate<String, Object> redisTemplate) {
        this.listOperations = redisTemplate.opsForList();
    }

    public void cachePersons(String key, List<PersonDTO> persons) {
        for (PersonDTO person : persons) {
            listOperations.leftPush(key, person);
//            -> [5 4 3 2 1]
        }
    }

    public List<PersonDTO> getPersonsInRange(String key, RangeDTO range) {
        List<Object> objects = listOperations.range(key, range.getFrom(), range.getTo());

        if (CollectionUtils.isEmpty(objects)) {
            return Collections.emptyList();
        }

        return objects.stream()
                .map(x -> (PersonDTO) x)
                .toList();
    }

    public PersonDTO getLastElement(String key) {
        Object o = listOperations.rightPop(key);
        if (o == null) {
            return null;
        }

        return (PersonDTO) o;
    }

    public void trim(String key, RangeDTO range) {
        listOperations.trim(key, range.getFrom(), range.getTo());
    }
}
