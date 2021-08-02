package com.imooc.gateway.filter;

import com.imooc.gateway.component.HandleException;
import com.imooc.gateway.config.IgnoreUrlsConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @program: redis
 * @description: 网关全局过滤器
 * @author: 谢泽毅
 * @create: 2021-08-02 15:07
 **/
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Resource
    private IgnoreUrlsConfig ignoreUrlsConfig;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private HandleException handleException;

    /**
     * 具体实现的业务：
     * 身份校验处理，即application中除了白名单外的请求全都拦截
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 判断请求是否在白名单中
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        boolean flag = false;
        String path = exchange.getRequest().getURI().getPath();
        for (String url : ignoreUrlsConfig.getUrls()) {
            if (antPathMatcher.match(url, path)) {
                flag = true;
                break;
            }
        }
        // 白名单放行
        if (flag) {
            return chain.filter(exchange);
        }
        // 获取 access_token
        String access_token = exchange.getRequest().getQueryParams().getFirst("access_token");

        // 判断 access_token 是否为空
        if (StringUtils.isBlank(access_token)) {
            return handleException.writeError(exchange, "请登录");
        }
        // 校验 token 是否有效
        String checkTokenUrl = "http://ms-oauth2-server/oauth/check_token?token=".concat(access_token);
        try {
            // 发送远程请求，验证token
            ResponseEntity<String> entity = restTemplate.getForEntity(checkTokenUrl, String.class);

            // token 无效的业务逻辑处理
            if (entity.getStatusCode() != HttpStatus.OK){
                return handleException.writeError(exchange,
                        "Token was not recognised,token:".concat(access_token));
            }
            if (StringUtils.isBlank(entity.getBody())) {
                return handleException.writeError(exchange,
                        "This token is invalid:".concat(access_token));
            }
        } catch (Exception e) {
            return handleException.writeError(exchange,
                    "Token was not recognised,token:".concat(access_token));
        }
        // 放行
        return chain.filter(exchange);
    }

    /**
     * 网关过滤器的排序，数字越小优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
