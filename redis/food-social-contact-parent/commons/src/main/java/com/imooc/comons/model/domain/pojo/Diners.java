package com.imooc.comons.model.domain.pojo;

import com.imooc.comons.model.domain.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @program: redis
 * @description: 食客实体类
 * @author: 谢泽毅
 * @create: 2021-08-02 08:59
 **/
@Getter
@Setter
public class Diners extends BaseModel {
    // 主键
    private Integer id;
    // 用户名
    private String username;
    // 昵称
    private String nickname;
    // 密码
    private String password;
    // 手机号
    private String phone;
    // 邮箱
    private String email;
    // 头像
    private String avatarUrl;
    // 角色
    private String roles;
}
