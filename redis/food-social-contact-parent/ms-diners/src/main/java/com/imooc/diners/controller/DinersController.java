package com.imooc.diners.controller;

import com.imooc.comons.model.domain.domain.ResultInfo;
import com.imooc.diners.service.DinersService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @program: redis
 * @description: 食客服务控制层
 * @author: 谢泽毅
 * @create: 2021-08-02 11:16
 **/
@RestController
@Api(tags = "食客相关接口")
public class DinersController {

    @Resource
    private DinersService dinersService;

    @Resource
    private HttpServletRequest request;

    @GetMapping("signin")
    public ResultInfo signIn(String account, String password) {
        return dinersService.signIn(account, password, request.getServletPath());
    }

}