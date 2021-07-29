package com.example.springdatademo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest(classes = {SpringdataDemoApplication.class})//连接的启动类
class SpringdataDemoApplicationTests {

    //这里可以定义去定义泛型，此时默认不写自动集成开发环境的泛型了
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void testInit() {
        String pong = redisTemplate.getConnectionFactory().getConnection().ping();
        System.out.println("pong = " + pong);
    }

    @Test
    public void testString() {
        // 插入一条数据
        redisTemplate.opsForValue().set("username", "zhangsan");
        // 获取一条数据
        Object username = redisTemplate.opsForValue().get("username");
        System.out.println("username = " + username);
    }
}
