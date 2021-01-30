package com.xicheng.springboot;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * description
 *
 * @author xichengxml
 * @date 2021/1/29 下午 11:32
 */
@Component
public class MyServlet implements ApplicationContextAware {

    private static ApplicationContext context;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static DispatcherServlet getDispatcherServlet() {
        return context.getBean(DispatcherServlet.class);
    }
}
