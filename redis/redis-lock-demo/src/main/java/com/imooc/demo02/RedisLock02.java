package com.imooc.demo02;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

public class RedisLock02 {

    private static final String LOCK_SUCCESS = "OK";
    private static final long UNLOCK_SUCCESS = 1L;

    /**
     * 尝试获取分布式锁
     * 通过加锁和删锁都有一个唯一的requestId进行标识。判断要不要删锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 锁的值
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public static boolean tryLock(Jedis jedis, String lockKey,
                                  String requestId, int expireTime) {

        while(true) {
            // set key value ex seconds nx(只有键不存在的时候才会设置key)
            String result = jedis.set(lockKey, requestId,
                    SetParams.setParams().ex(expireTime).nx());
            if (LOCK_SUCCESS.equals(result)) {
                return true;
            }
        }

    }

    /**
     * 释放分布式锁
     * 释放锁时，先获取到锁的值，与该线程下的值进行比对判断，相同就解锁，不同则可能因为超时等原因导致锁已经被释放掉了
     * 此时只需要直接返回即可
     * 该情况是进程级别的，这里是一个JVM ，要在多个JVM下构建多个程序才可演示该效果
     * 为什么教学说线程级别不能演示？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？
     * 相关学习
     * https://blog.csdn.net/weixin_39833509/article/details/88603957?utm_medium=distribute.pc_aggpage_search_result.none-task-blog-2~aggregatepage~first_rank_v2~rank_aggregation-1-88603957.pc_agg_rank_aggregation&utm_term=%E4%B8%80%E4%B8%AA%E8%AF%B7%E6%B1%82%E6%98%AF%E4%B8%80%E4%B8%AA%E7%BA%BF%E7%A8%8B%E8%BF%98%E6%98%AF%E4%B8%80%E4%B8%AA%E8%BF%9B%E7%A8%8B&spm=1000.2123.3001.4430
     *
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @return 是否释放成功
     */
    public static boolean unlock(Jedis jedis,  String lockKey, String requestId) {
        if (!requestId.equals(jedis.get(lockKey))) {
            return false;
        }
        Long result = jedis.del(lockKey);
        return UNLOCK_SUCCESS == result ? true: false;
    }

}