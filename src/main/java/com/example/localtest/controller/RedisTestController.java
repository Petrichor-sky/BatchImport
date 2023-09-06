package com.example.localtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class RedisTestController {
    @Autowired
    RedisTemplate<String,String> redisTemplate;


    @RequestMapping("/redis")
    public String setValue(){
        redisTemplate.opsForValue().set("测试key","测试value");
        return redisTemplate.opsForValue().get("测试key");
    }

}
