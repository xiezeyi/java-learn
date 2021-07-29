package com.imooc.jedis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * @program: redes6.x
 * @description:
 * @author: 谢泽毅
 * @create: 2021-07-29 14:42
 **/
public class JedisTest {

    Jedis jedis = null;

    @Before
    public void init() {
        // 初始化Jedis客户端，声明主机和端口
        jedis = new Jedis("121.43.113.221", 6379);
        // 身份认证
        jedis.auth("xiezeyi");
        // ping pong 心跳机制检测是否连接成功
        String pong = jedis.ping();
        System.out.println("pong = " + pong);
    }

    @Test
    public void testString() {
        // 选择数据库
        String selectRelsult = jedis.select(2);
        System.out.println("selectRelsult = " + selectRelsult);
        // 插入一条数据
        String result = jedis.set("username", "zhangsan1");
        System.out.println("result = " + result);
        // 获取一条数据
        String username = jedis.get("username");
        System.out.println("username = " + username);
        // 插入一条数据
        jedis.set("imooc:user:1", "zhangsan");
        // 获取一条数据
        String users1 = jedis.get("imooc:user:1");
        System.out.println("users1 = " + users1);
    }

    @Test
    public void testKeys() {
        jedis.select(2);
        Set<String> keys = jedis.keys("*");
        System.out.println("keys = " + keys);
    }


    @After
    public void close() {
        if (null != jedis) {
            jedis.close();
        }
        // 释放资源
    }

}
