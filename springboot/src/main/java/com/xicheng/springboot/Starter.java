package com.xicheng.springboot;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * description
 *
 * @author xichengxml
 * @date 2021/1/29 下午 11:11
 */
@ComponentScan("com.xicheng.springboot")
public class Starter {

    public static void main(String[] args) throws Exception {
        // Load Spring web application configuration
        // 启动IOC容器
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(Starter.class);
        context.refresh();

        WebServerFactory webServerFactory = context.getBean(WebServerFactory.class);
        webServerFactory.createServer();
    }
}
