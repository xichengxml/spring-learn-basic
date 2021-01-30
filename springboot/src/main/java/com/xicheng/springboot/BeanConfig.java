package com.xicheng.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * description 将相关的bean加载到ioc容器
 *
 * @author xichengxml
 * @date 2021/1/29 下午 11:15
 */
@Configuration
public class BeanConfig {

    @Bean
    public DispatcherServlet getDispatcherServlet() {
        return new DispatcherServlet();
    }
}
