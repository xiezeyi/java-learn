package com.imooc.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @program: redis
 * @description:
 * @author: 谢泽毅
 * @create: 2021-07-29 19:25
 **/
//@EnableDiscoveryClient  在某版本后一直到Hosy版本这个约定大于集成了，默认存在
@SpringBootApplication
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class,args);
    }
}
