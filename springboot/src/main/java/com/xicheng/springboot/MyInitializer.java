package com.xicheng.springboot;

import org.springframework.stereotype.Component;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.Set;

/**
 * description
 * spring的initializer{@link org.springframework.web.SpringServletContainerInitializer}
 *
 * @author xichengxml
 * @date 2021/1/29 下午 11:35
 */
@Component
public class MyInitializer  extends MyServlet implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext servletContext) throws ServletException {
        // 启动Tomcat容器，将DispatcherServlet放到tomcat容器中
        ServletRegistration.Dynamic registration = servletContext.addServlet("app", getDispatcherServlet());
        registration.setLoadOnStartup(1);
        registration.addMapping("/*");
    }
}
