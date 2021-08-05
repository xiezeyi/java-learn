package com.imooc.demo01;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

public class RedisLock {

    private static final String LOCK_SUCCESS = "OK";
    private static final long UNLOCK_SUCCESS = 1L;

    /**
     * 尝试获取分布式锁
     *
     * @param jedis      Redis客户端
     * @param lockKey    锁
     * @param value      锁的值
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public static boolean tryLock(Jedis jedis, String lockKey,
                                  String value, int expireTime) {
        // 自旋锁
            // 理解：一个线程进来后，在redis里设置了一个锁,返回ok，该锁设置成功，return true，跳出该代码块
                //在第一个线程未将该锁del或该锁未过期时，第二个线程进来后只会返回设置失败,无法return true,故
                //进行第二次循环，直到第一个线程将锁释放后，其它线程再竞争该锁
        while (true) {
            // set key value ex seconds nx(只有键不存在的时候才会设置key)
            String result = jedis.set(lockKey, value,
                    SetParams.setParams().ex(expireTime).nx());
            if (LOCK_SUCCESS.equals(result)) {
                return true;
            }
        }
    }

    /**
     * 释放分布式锁
     *
     * @param jedis   Redis客户端
     * @param lockKey 锁
     * @return 是否释放成功
     */
    public static boolean unlock(Jedis jedis, String lockKey) {
        Long result = jedis.del(lockKey);
        if (UNLOCK_SUCCESS == result) {
            return true;
        }
        return false;
    }

}