package com.imooc.diners.controller;

import com.imooc.comons.model.domain.domain.ResultInfo;
import com.imooc.comons.utils.ResultInfoUtil;
import com.imooc.diners.service.SendVerityCodeService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @program: redis
 * @description: 发送验证码控制层
 * @author: 谢泽毅
 * @create: 2021-08-02 17:12
 **/
@RestController
@Api(tags = "发送验证码")
public class SendVerityCodeController {

    @Resource
    private SendVerityCodeService sendVerityCodeService;

    @Resource
    private HttpServletRequest request;

    @GetMapping("send")
    public ResultInfo send(String phone){
        sendVerityCodeService.send(phone);
        return ResultInfoUtil.buildSuccess("发送成功",request.getServletPath());
    }
}
