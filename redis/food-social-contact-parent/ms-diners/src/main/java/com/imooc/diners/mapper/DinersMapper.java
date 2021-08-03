package com.imooc.diners.mapper;

import com.imooc.comons.model.domain.dto.DinersDTO;
import com.imooc.comons.model.domain.pojo.Diners;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @program: redis
 * @description: 食客 Mapper
 * @author: 谢泽毅
 * @create: 2021-08-02 17:27
 **/
public interface DinersMapper {

    //根据手机号查询食客信息
    @Select("select id,username,phone,email,is_valid " +
            " from t_diners where phone = #{phone}")
    Diners selectByPhone(@Param("phone") String phone);

    // 根据用户名查询食客信息
    @Select("select id, username, phone, email, is_valid " +
            " from t_diners where username= #{username}")
    Diners selectByUseranme(@Param("username") String username);

    // 新增食客信息
    @Insert("insert into t_diners " +
            " (username,password,phone,roles,is_valid, create_date, update_date) " +
            " values (#{username},#{password},#{phone},\"ROLE_USER\",1,now(),now())")
    int save(DinersDTO dinersDTO);
}

