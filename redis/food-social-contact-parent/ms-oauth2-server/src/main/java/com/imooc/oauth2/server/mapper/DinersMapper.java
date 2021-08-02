package com.imooc.oauth2.server.mapper;

import com.imooc.comons.model.domain.pojo.Diners;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @program: redis
 * @description:
 * @author: 谢泽毅
 * @create: 2021-07-30 16:40
 **/
public interface DinersMapper {

    // 根据用户名 or 手机号 or 邮箱查询用户信息
    @Select("select id, username, nickname, phone, email, " +
            "password, avatar_url, roles, is_valid from t_diners where " +
            "(username = #{account} or phone = #{account} or email = #{account})")
    Diners selectByAccountInfo(@Param("account") String account);
}
