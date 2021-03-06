package com.imooc.comons.constant;

import lombok.Getter;

/**
 * @program: redis
 * @description: 公共枚举类
 * 作用  在后续的项目开发中，会有很多数据存到redis中，类似token令牌，我们要在redis设置一个存储的层级 方便进行查询
 * 如本枚举类中   verify_code:即代表层级前缀，后面则是该层级的描述，有利于查看redis
 * @author: 谢泽毅
 * @create: 2021-08-02 16:51
 **/
@Getter
public enum RedisKeyConstant {

    verify_code("verify_code:", "验证码"),
    seckill_vouchers("seckill_vouchers:", "秒杀券的key"),
    lock_key("lockby:", "分布式锁的Key"),
    following("following:", "关注集合Key"),
    followers("followers:", "粉丝集合key"),
    following_feeds("following_feeds:", "我关注的好友的FeedsKey");

    private String key;
    private String desc;

    RedisKeyConstant(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

}