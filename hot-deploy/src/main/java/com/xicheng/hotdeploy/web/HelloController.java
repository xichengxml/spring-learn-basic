package com.xicheng.hotdeploy.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description
 *
 * @author xichengxml
 * @date 2021/1/31 下午 09:23
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello, hot deploy update";
    }

}
