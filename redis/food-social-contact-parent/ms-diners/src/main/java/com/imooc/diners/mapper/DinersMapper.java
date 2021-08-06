package com.imooc.diners.mapper;

import com.imooc.comons.model.domain.dto.DinersDTO;
import com.imooc.comons.model.domain.pojo.Diners;
import com.imooc.comons.model.domain.vo.ShortDinerInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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
    // 这样子插入，有两种好处，
    // 1，微服务方式，每一次调用都是建立在tcp上的http请求，三次握手四次挥手请求头响应体等，
    //      使用for循环对远程调用和数据库的压力都很大
    // 发过去一个多个集合
    // 根据 ID 集合查询多个食客信息
    @Select("<script> " +
            " select id, nickname, avatar_url from t_diners " +
            " where is_valid = 1 and id in " +
            " <foreach item=\"id\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\"> " +
            "   #{id} " +
            " </foreach> " +
            " </script>")
    List<ShortDinerInfo> findByIds(@Param("ids") String[] ids);
}

