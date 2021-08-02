package com.imooc.diners.mapper;

import com.imooc.comons.model.domain.pojo.Diners;
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
}
