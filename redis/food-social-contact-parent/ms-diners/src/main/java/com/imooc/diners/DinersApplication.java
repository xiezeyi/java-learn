package com.imooc.diners;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: redis
 * @description:
 * @author: 谢泽毅
 * @create: 2021-07-29 19:38
 **/
@MapperScan("com.imooc.diners.mapper")
@SpringBootApplication
public class DinersApplication {
    public static void main(String[] args) {
        SpringApplication.run(DinersApplication.class, args);
    }
}
