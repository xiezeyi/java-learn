package com.xzy.javaSE8.y2自定义函数接口;

import java.util.function.Consumer;

/**
 * @program: java-lambda
 * @description:
 * @author: 谢泽毅
 * @create: 2021-08-03 11:23
 **/
public class 自定义函数接口 {
    public static void main(String[] args) {
        //添加了ConsumerInterface自定义函数及饿哦库后
        ConsumerInterface<String> consumer = str -> System.out.println(str);
        consumer.accept("我是自定义函数式接口");
    }
}
