package com.imooc.diners.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.imooc.comons.constant.ApiConstant;
import com.imooc.comons.model.domain.domain.ResultInfo;
import com.imooc.comons.model.domain.dto.DinersDTO;
import com.imooc.comons.model.domain.pojo.Diners;
import com.imooc.comons.model.domain.vo.ShortDinerInfo;
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
import java.util.List;

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
    @Resource
    private SendVerityCodeService sendVerityCodeService;


    /**
     * 根据 ids 查询食客信息
     *
     * @param ids 主键 id，多个以逗号分隔，逗号之间不用空格
     * @return
     */
    public List<ShortDinerInfo> findByIds(String ids) {
        AssertUtil.isNotEmpty(ids);
        String[] idArr = ids.split(",");
        List<ShortDinerInfo> dinerInfos = dinersMapper.findByIds(idArr);
        return dinerInfos;
    }

    /**
     * 用户注册
     *
     * @param dinersDTO
     * @param path
     * @return
     */
    public ResultInfo register(DinersDTO dinersDTO,String path){
        // 参数非空校验
        String username = dinersDTO.getUsername();
        AssertUtil.isNotEmpty(username,"请输入验证码");
        String password = dinersDTO.getPassword();
        AssertUtil.isNotEmpty(password,"请输入验证码");
        String phone = dinersDTO.getPhone();
        AssertUtil.isNotEmpty(phone,"请输入验证码");
        String verifyCode = dinersDTO.getVerifyCode();
        AssertUtil.isNotEmpty(verifyCode,"请输入验证码");

        // 获取验证码
        String code = sendVerityCodeService.getCodeByPhone(phone);
        // 验证码是否过期
        AssertUtil.isNotEmpty(code,"验证码已过期,请重新发送");
        // 验证码一致性校验
        AssertUtil.isTrue(!dinersDTO.getVerifyCode().equals(code),"输入的验证码不一致，请重新输入");
        // 验证用户名是否已注册
        Diners diners = dinersMapper.selectByUseranme(username.trim());
        AssertUtil.isTrue(diners != null ,"用户名已存在，请重新输入");

        // 注册
        // 密码加密
        dinersDTO.setPassword(DigestUtil.md5Hex(password.trim()));
        dinersMapper.save(dinersDTO);
        // 自动登录
        ResultInfo resultInfo = signIn(username.trim(), password.trim(), path.trim());
        return resultInfo;

    }

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
