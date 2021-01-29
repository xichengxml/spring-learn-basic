package com.xicheng.springboot;

import org.springframework.stereotype.Component;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * description
 *
 * @author xichengxml
 * @date 2021/1/29 下午 11:35
 */
@Component
public class MyInitializer  extends MyServlet implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // 启动Tomcat容器，将DispatcherServlet放到tomcat容器中
        ServletRegistration.Dynamic registration = servletContext.addServlet("app", getDispatcherServlet());
        registration.setLoadOnStartup(1);
        registration.addMapping("/*");
    }
}
