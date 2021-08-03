package com.imooc.diners.service;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.imooc.comons.constant.RedisKeyConstant;
import com.imooc.comons.utils.AssertUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @program: redis
 * @description: 发送验证码的业务逻辑层
 * @author: 谢泽毅
 * @create: 2021-08-02 16:59
 **/
@Service
public class SendVerityCodeService {
    @Resource
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 发送验证码
     * @param phone
     */
    public void send(String phone){
        // 检查非空
        AssertUtil.isNotEmpty(phone,"手机号不能为空");
        // 根据手机号查询是否已生成验证码，已生成验证码
        if (!checkCodeIsExpired(phone)) {
            return;
        }
        // 生成6位验证码
        String code = RandomUtil.randomNumbers(6);
        // 调用短信服务发送短信
        System.out.println(code);//模拟发条短信
        // 发送成功,将code保存至redis,失效时间60秒
        String key = RedisKeyConstant.verify_code.getKey() + phone;
        redisTemplate.opsForValue().set(key,code,60, TimeUnit.SECONDS);

    }

    private boolean checkCodeIsExpired(String phone) {
        String key = RedisKeyConstant.verify_code.getKey() + phone;
        String code = redisTemplate.opsForValue().get(key);
//        if (StrUtil.isBlank(code)) {
//            return true;
//        }
//        return false;
        //有结果就返回false,就证明不用发送了，故true进入return
        return StrUtil.isBlank(code) ? true :false;
    }

    public String getCodeByPhone(String phone) {
        String key = RedisKeyConstant.verify_code.getKey() + phone;
        return redisTemplate.opsForValue().get(key);
    }


}
