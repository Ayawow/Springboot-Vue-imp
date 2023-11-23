package org.example;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest //添加此注解会在单元测试方法执行前先初始化Spring容器
public class RedisTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void  testSet(){
        //往redis中存储一个键值对 StringRedisTemplate
       ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
       operations.set("username","zs");
    }

    @Test
    public void testGet(){
        ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
        System.out.println(operations.get("username"));
    }

}
