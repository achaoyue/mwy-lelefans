package com.lelefans.mwy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 这是一个测试类
 */

@RequestMapping("/api/test")
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public Object sayHello(HttpServletRequest request){
        return request.getParameterMap();
    }

}
