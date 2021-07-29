package com.imooc.diners.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: redis
 * @description:
 * @author: 谢泽毅
 * @create: 2021-07-29 19:39
 **/
@RestController
@RequestMapping("hello")
public class HelloController {

    @GetMapping
    public String hello(String name){
        return "hello" + name;
    }
}
