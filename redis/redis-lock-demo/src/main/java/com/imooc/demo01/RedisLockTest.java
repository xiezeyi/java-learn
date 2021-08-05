package com.imooc.demo01;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.UUID;

public class RedisLockTest {

    private int count = 0;
    private String lockKey = "lock";

    private void call(Jedis jedis) {
        // 加锁
            // 传入 jedis对象 lockkey 失效时间    传值是为了向redis中设置一个有过期时间的锁
            // UUID没什么作用，只是要赋予key一个值，让客户端设置进去后其他人就不能再设置了，除非删了这个锁
        boolean locked = RedisLock.tryLock(jedis, lockKey,
                UUID.randomUUID().toString(), 60);
        try {
            if (locked) {
                for (int i = 0; i < 500; i++) {
                    count++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisLock.unlock(jedis, lockKey);
        }
    }

    /**
     * new一个对象，初始化JedisPool
     * 创建两个线程
     * 执行
     * join指执行完后去执行sout的操作
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        RedisLockTest redisLockTest = new RedisLockTest();
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMinIdle(1);
        jedisPoolConfig.setMaxTotal(5);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,
                "121.43.113.221", 6379, 1000, "xiezeyi");

        Thread t1 = new Thread(() -> redisLockTest.call(jedisPool.getResource()));
        Thread t2 = new Thread(() -> redisLockTest.call(jedisPool.getResource()));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(redisLockTest.count);
    }

}