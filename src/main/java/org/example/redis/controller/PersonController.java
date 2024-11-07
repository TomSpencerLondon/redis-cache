package org.example.redis.controller;

import jakarta.annotation.PostConstruct;
import org.example.redis.dto.PersonDTO;
import org.example.redis.dto.RangeDTO;
import org.example.redis.service.RedisListCache;
import org.example.redis.service.RedisValueCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private final RedisValueCache valueCache;
    private final RedisListCache listCache;


    @Autowired
    public PersonController(RedisValueCache valueCache, RedisListCache listCache) {
        this.valueCache = valueCache;
        this.listCache = listCache;
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

    @PostMapping("/list/{key}")
    public void cachePersons(@PathVariable String key, @RequestBody List<PersonDTO> persons) {
        listCache.cachePersons(key, persons);
    }

    @GetMapping("/list/{key}")
    public List<PersonDTO> getPersonsInRange(@PathVariable String key, @RequestBody RangeDTO range) {
        return listCache.getPersonsInRange(key, range);
    }

    @GetMapping("/list/last/{key}")
    public PersonDTO getLastElement(@PathVariable String key) {
        return listCache.getLastElement(key);
    }

    @DeleteMapping("/list/{key}")
    public void trim(@PathVariable String key, @RequestBody RangeDTO range) {
        listCache.trim(key, range);
    }

}
