package com.imooc.oauth2.server.controller;

import com.imooc.comons.model.domain.domain.ResultInfo;
import com.imooc.comons.model.domain.domain.SignInIdentity;
import com.imooc.comons.model.domain.vo.SignInDinerInfo;
import com.imooc.comons.utils.ResultInfoUtil;
import com.mysql.cj.PreparedQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @program: redis
 * @description: 用户中心
 * @author: 谢泽毅
 * @create: 2021-08-02 11:40
 **/
@RestController
public class UserController {

    @Resource
    private HttpServletRequest request;

    @Resource
    private RedisTokenStore redisTokenStore;


    /**
     * 获取当前用户信息
     * @param authentication
     * @return
     */
    @GetMapping("user/me")
    public ResultInfo getCurrentUser(Authentication authentication){
        // 获取登录用户的信息，然后设置
        SignInIdentity signInIdentity = (SignInIdentity) authentication.getPrincipal();
        // 转为前端可用的视图对象
        SignInDinerInfo dinerInfo = new SignInDinerInfo();
        BeanUtils.copyProperties(signInIdentity,dinerInfo);
        return ResultInfoUtil.buildSuccess(request.getServletPath(), dinerInfo);
    }

//   认证方式一 通过access_token http://localhost:8082/user/me?access_token=84b848b8-5aeb-4270-86cf-e390267aa4c6
//   方式二 通过authorization
    /**
     * 安全退出
     *
     * @param access_token
     * @param authorization
     * @return
     */
    @GetMapping("user/logout")
    public ResultInfo Logout(String access_token, String authorization) {
        // 判断access_token 是否为空，为空将authorization 赋值给 access_token
        if (StringUtils.isBlank(access_token)) {
            access_token = authorization;
        }
        // 判断 authorization 是否为空
        if (StringUtils.isBlank(access_token)) {
            return ResultInfoUtil.buildSuccess(request.getServletPath());
        }
        // 判断 bearer token 是否为空
        if (access_token.toLowerCase().contains("bearer".toLowerCase())) {
            access_token = access_token.toLowerCase().replace("bearer","");

        }
        // 清除 redis token 信息
        OAuth2AccessToken oAuth2AccessToken = redisTokenStore.readAccessToken(access_token);
        if (oAuth2AccessToken != null) {
            redisTokenStore.removeAccessToken(oAuth2AccessToken);
            OAuth2RefreshToken refreshToken = oAuth2AccessToken.getRefreshToken();
            redisTokenStore.removeRefreshToken(refreshToken);
        }
        return ResultInfoUtil.buildSuccess(request.getServletPath(), "退出成功");
    }
}
