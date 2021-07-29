package com.imooc;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @program: redes6.x
 * @description: 连接池工具类
 * @author: 谢泽毅
 * @create: 2021-07-29 15:09
 **/
public class JedisPoolConnectRedis {

    private static JedisPool jedisPool;

    static {
        // 创建连接池配置对象
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 设置最大连接数，默认 8
        jedisPoolConfig.setMaxTotal(5);
        // 设置最大空闲数量，默认 8
        jedisPoolConfig.setMaxIdle(5);
        // 设置最少空闲数量，默认 0
        jedisPoolConfig.setMinIdle(0);
        // 设置等待时间 ms
        jedisPoolConfig.setMaxWaitMillis(100);
        // 初始化 JedisPool对象
        jedisPool = new JedisPool(jedisPoolConfig,
                "121.43.113.221", 6379,
                100, "xiezeyi");

    }

    /**
     * 获取jedis对象
     *
     * @return
     */
    public static Jedis getJedis() {
        return jedisPool.getResource();
    }
}
