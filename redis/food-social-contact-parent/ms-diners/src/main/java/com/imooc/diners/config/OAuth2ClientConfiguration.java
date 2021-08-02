package com.imooc.diners.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: redis
 * @description: 客户端配置类
 * @author: 谢泽毅
 * @create: 2021-08-02 10:35
 **/

@Component
@ConfigurationProperties(prefix = "oauth2.client") //加载前缀
@Getter
@Setter
public class OAuth2ClientConfiguration {

    //模拟登录业务中Post请求参数
    private String clientId;
    private String secret;
    private String grant_type;
    private String scope;

}
