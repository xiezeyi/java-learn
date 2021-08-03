package com.xzy.javaSE8.y2自定义函数接口;

/**
 * @program: java-lambda
 * @description:
 * @author: 谢泽毅
 * @create: 2021-08-03 11:29
 **/
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        TestStream<String> stream = new TestStream<String>();
        List list = Arrays.asList("11", "22", "33");
        stream.setList(list);
        stream.myForEach(str -> System.out.println(str));// 使用自定义函数接口书写Lambda表达式
    }
}

//@FunctionalInterface
//interface ConsumerInterface<T>{
//    void accept(T t);
//}

class TestStream<T>{
    private List<T> list;
    public void myForEach(ConsumerInterface<T> consumer){// 1
        for(T t : list){
            consumer.accept(t);
        }
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}