package org.example.redis.controller;

import jakarta.annotation.PostConstruct;
import org.example.redis.dto.PersonDTO;
import org.example.redis.service.RedisValueCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private final RedisValueCache valueCache;


    @Autowired
    public PersonController(RedisValueCache valueCache) {
        this.valueCache = valueCache;
    }

    @PostMapping
    public void cachePerson(@RequestBody PersonDTO dto) {
        valueCache.cache(dto.getId(), dto);
    }

    @GetMapping("/{id}")
    public PersonDTO getPerson(@PathVariable String id) {
        return (PersonDTO) valueCache.getCachedValue(id);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable String id) {
         valueCache.deleteCacheValue(id);
    }
}
