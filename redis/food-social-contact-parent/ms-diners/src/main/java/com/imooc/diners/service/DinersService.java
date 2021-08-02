package com.imooc.diners.service;

import cn.hutool.core.bean.BeanUtil;
import com.imooc.comons.constant.ApiConstant;
import com.imooc.comons.model.domain.domain.ResultInfo;
import com.imooc.comons.model.domain.pojo.Diners;
import com.imooc.comons.utils.AssertUtil;
import com.imooc.comons.utils.ResultInfoUtil;
import com.imooc.diners.config.OAuth2ClientConfiguration;
import com.imooc.diners.domain.OAuthDinerInfo;
import com.imooc.diners.mapper.DinersMapper;
import com.imooc.diners.vo.LoginDinerInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.LinkedHashMap;

/**
 * @program: redis
 * @description: 食客服务的业务逻辑层
 * @author: 谢泽毅
 * @create: 2021-08-02 10:41
 **/
@Service
public class DinersService {

    @Resource
    private RestTemplate restTemplate;
    @Value("${service.name.ms-oauth-server}")
    private  String oauthServerName;
    @Resource
    private OAuth2ClientConfiguration oAuth2ClientConfiguration;
    @Resource
    private DinersMapper dinersMapper;
    /**
     * 校验手机号是否注册
     * @param phone
     */
    public void checkPhoneIsRegistered(String phone) {
        AssertUtil.isNotEmpty(phone,"手机号不能为空");
        Diners diners = dinersMapper.selectByPhone(phone);
        AssertUtil.isTrue(diners==null,"该手机号未注册");
        AssertUtil.isTrue(diners.getIsValid()==0,"该用户已锁定，请先解锁/联系管理员");

    }

    /**
     * 登录
     * @param account   账号，用户名手机邮箱
     * @param password  密码
     * @param path      请求路径
     * @return
     */
    public ResultInfo signIn(String account, String password, String path) {
        // 参数校验
        AssertUtil.isNotEmpty(account,"请输入登录账号");
        AssertUtil.isNotEmpty(password,"请输入登录密码");
        // 构建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 构建请求体(请求参数)
        MultiValueMap<String,Object> body = new LinkedMultiValueMap<>();
        body.add("username",account);
        body.add("password",password);
        body.setAll(BeanUtil.beanToMap(oAuth2ClientConfiguration));
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
        // 设置 Authorization
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(
                oAuth2ClientConfiguration.getClientId(),
                oAuth2ClientConfiguration.getSecret()));

        // 发送请求
        ResponseEntity<ResultInfo> result = restTemplate.postForEntity(oauthServerName + "oauth/token", entity, ResultInfo.class);


        // 处理返回结果
        AssertUtil.isTrue(result.getStatusCode()!= HttpStatus.OK,"登录失败");
        ResultInfo resultInfo = result.getBody();
        if (resultInfo.getCode() != ApiConstant.SUCCESS_CODE) {
            //登录失败
            resultInfo.setData(resultInfo.getMessage());
            return resultInfo;
        }
        //  这里的Data是一个LinkedHashMap 转成了域对象 OAuthDinerInfo
        OAuthDinerInfo dinerInfo = BeanUtil.fillBeanWithMap((LinkedHashMap)resultInfo.getData(),
            new OAuthDinerInfo(),false);
        // 根据业务需求返回视图对象
        LoginDinerInfo loginDinerInfo = new LoginDinerInfo();
        loginDinerInfo.setToken(dinerInfo.getAccessToken());
        loginDinerInfo.setAvatarUrl(dinerInfo.getAvatarUrl());
        loginDinerInfo.setNickname(dinerInfo.getNickname());
        return ResultInfoUtil.buildSuccess(path,loginDinerInfo);


    }
}
