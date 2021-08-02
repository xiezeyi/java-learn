package com.imooc.oauth2.server.controller;

import com.imooc.comons.model.domain.domain.ResultInfo;
import com.imooc.comons.model.domain.domain.SignInIdentity;
import com.imooc.comons.model.domain.vo.SignInDinerInfo;
import com.imooc.comons.utils.ResultInfoUtil;
import com.mysql.cj.PreparedQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
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

//    http://localhost:8082/user/me?access_token=84b848b8-5aeb-4270-86cf-e390267aa4c6
}
