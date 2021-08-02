package com.imooc.diners.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @program: redis
 * @description: 视图对象
 * @author: 谢泽毅
 * @create: 2021-08-02 11:06
 **/
@Setter
@Getter
public class LoginDinerInfo implements Serializable {

    private String nickname;
    private String token;
    private String avatarUrl;

}
