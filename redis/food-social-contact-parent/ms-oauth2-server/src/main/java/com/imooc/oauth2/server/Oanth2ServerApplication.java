package com.imooc.oauth2.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: redis
 * @description:
 * @author: 谢泽毅
 * @create: 2021-08-02 09:09
 **/
@MapperScan("com.imooc.oauth2.server.mapper")
@SpringBootApplication
public class Oanth2ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(Oanth2ServerApplication.class,args);
    }
}
