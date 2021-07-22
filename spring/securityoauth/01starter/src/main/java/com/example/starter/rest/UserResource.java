package com.example.starter.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: securityoauth
 * @description:
 * @author: 谢泽毅
 * @create: 2021-07-22 16:20
 **/
@RestController
@RequestMapping("/api")
public class UserResource {
    @GetMapping("greeting")
    public String greeting(){
        return "hello world";
    }
}
