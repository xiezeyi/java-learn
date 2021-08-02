package com.imooc.diners.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @program: redis
 * @description: Rest 配置类
 * @author: 谢泽毅
 * @create: 2021-08-02 10:38
 **/
@Configuration
public class RestTemplateConfiguration {
    //HTTP客户端的封装，模板对象，这里可以发送get post等各种请求
    @LoadBalanced //拥有负载均衡的能力
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
