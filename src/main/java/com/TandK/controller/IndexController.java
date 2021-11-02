package com.TandK.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TandK
 * @since 2021/8/23 21:13
 */
@RestController
@RequestMapping("index")
public class IndexController {

    @GetMapping("/hello")
    public String sayHello(){
        return "hello, spring boot!";
    }
}
