package com.xzy.javaSE8.y1语法;

import java.util.Comparator;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;

/**
 * @program: java-lambda
 * @description: 语法格式
 * 新的操作符 ->
 * 操作符左侧: 指出表达式所需要的参数
 * 操作符右侧: 表达式执行的功能
 * @author: 谢泽毅
 * @create: 2021-08-03 10:52
 **/
public class 语法格式 {
    public static void main(String[] args) {
        //语法格式一：无参 无返回值
        Runnable r1 = () -> System.out.println("Hello Lambda");
        //语法格式二: 有参
        Consumer<String> con = (x) -> System.out.println("x = " + x);
        //语法格式三: 有参(单独) 优化
        Consumer<String> con2 = x -> System.out.println("x = " + x);
        //语法格式四: 有参(多参) 有返回值
        Comparator<Integer> com = (x, y) -> {
            System.out.println("函数式接口");
            return Integer.compare(x, y);
        };
        //语法格式五: 主体只有一条语句，省略大括号
        Comparator<Integer> com2 = (x, y) -> Integer.compare(x, y);
        //语法格式六: 参数列表数据类型省略不写，由JVM通过上下文推出数据类型（依赖编译器 类型判断）
        Comparator<Integer> com3 = (Integer x,Integer y) -> {  //Integer 类型可以省略
            System.out.println("函数式接口");
            return Integer.compare(x, y);
        };

        BinaryOperator<Long> add = (Long x, Long y) -> x + y;// 4

        BinaryOperator<Long> addImplicit = (x, y) -> x + y;// 5 类型推断
    }
}
