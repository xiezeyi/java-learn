package com.imooc.oauth2.server.config;

import com.imooc.oauth2.server.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;

/**
 * @program: redis
 * @description: 授权服务
 * @author: 谢泽毅
 * @create: 2021-07-30 15:16
 **/
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    // RedisTokenStore
    @Resource
    private RedisTokenStore redisTokenStore;

    //认证管理对象
    @Resource
    private AuthenticationManager authenticationManager;

    //密码编码器
    @Resource
    private PasswordEncoder passwordEncoder;

    //添加客户端的配置类
    @Resource
    private ClientOAuth2DataConfiguration clientOAuth2DataConfiguration;

    //登录校验
    @Resource
    private UserService userService;

    /**
     * 配置令牌端点安全约束：
     *    刚才在SecurityConfiguration中有一些默认的端点，如其中配置的oauth
     *    开头的端点，这些请求默认是受保护的，此时需要给这些端点放行一下，允许访问。
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 允许访问 token 的公钥  默认 /oauth/token_key 是受保护的
        security.tokenKeyAccess("permitAll()")
                // 允许检查 token 的状态 , 默认/auto/check_token 是受保护的
                .checkTokenAccess("permitAll");
    }

    /**
     * 客户端配置- 授权模型
     *      主要是将application.yml中Oauth2下的配置加载到环境里
     *      如何将application.yml加载到这里？新增一个客户端配置类：ClientOAuth2DataConfiguration
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //client 存到内存中  开始配置
        clients.inMemory().withClient(clientOAuth2DataConfiguration.getClientId()) //客户端标识ID
                .secret(passwordEncoder.encode(clientOAuth2DataConfiguration.getSecret()))
                .authorizedGrantTypes(clientOAuth2DataConfiguration.getGrantTypes()) //授权类型
                .accessTokenValiditySeconds(clientOAuth2DataConfiguration.getTokenValidityTime())//token有效期
                .refreshTokenValiditySeconds(clientOAuth2DataConfiguration.getRefreshTokenValidityTime()) //刷新token的有效期
                .scopes(clientOAuth2DataConfiguration.getScopes());//客户端访问范围
    }

    /**
     * 配置授权以及令牌的访问端点和令牌服务
     *  因为token最终令牌生成后，需要有一个登录方法，逻辑通过后生成令牌
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 认证器
        endpoints.authenticationManager(authenticationManager)
                // 具体登录的方法
                .userDetailsService(userService)
                // token 存储的方式
                .tokenStore(redisTokenStore);
    }
}
