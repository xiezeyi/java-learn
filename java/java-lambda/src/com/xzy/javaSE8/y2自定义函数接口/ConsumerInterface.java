package com.xzy.javaSE8.y2自定义函数接口;

@FunctionalInterface
public interface ConsumerInterface<T> {
    void accept(T t);
}
