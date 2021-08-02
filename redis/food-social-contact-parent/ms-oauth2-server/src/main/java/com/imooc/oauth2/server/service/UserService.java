package com.imooc.oauth2.server.service;

import com.imooc.comons.model.domain.pojo.Diners;
import com.imooc.comons.utils.AssertUtil;
import com.imooc.oauth2.server.mapper.DinersMapper;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: redis
 * @description: 登录校验
 * @author: 谢泽毅
 * @create: 2021-07-30 15:41
 **/
@Service
public class UserService implements UserDetailsService {

    @Resource
    private DinersMapper dinersMapper;

    /**
     * 根据用户名获取，这里只要求用户登录时肯定会有用户名和密码的信息，我拿着
     * 这里的用户名去数据库查出用户信息，security去做密码相关校验，校验通过生成令牌服务，然后存进redis
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AssertUtil.isNotEmpty(username,"请输入用户名");
        Diners diners = dinersMapper.selectByAccountInfo(username);
        if (diners == null) {
            throw new UsernameNotFoundException("用户名或密码错误，请重新输入");
        }
        return new User(username,diners.getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(diners.getRoles()));
    }
}
