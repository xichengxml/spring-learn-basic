package com.xicheng.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description
 *
 * @author xichengxml
 * @date 2021/1/29 下午 11:04
 */
@RestController
public class HelloController {

    @RequestMapping(value = "hello")
    public String hello() {
        return "hello springboot";
    }
}
