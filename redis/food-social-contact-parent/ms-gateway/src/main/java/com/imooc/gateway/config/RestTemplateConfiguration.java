package com.imooc.gateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @program: redis
 * @description:  检查是否携带令牌和令牌有没有效，要通过认证服务checkToken检查令牌
 * @author: 谢泽毅
 * @create: 2021-08-02 15:02
 **/
@Configuration
public class RestTemplateConfiguration {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}