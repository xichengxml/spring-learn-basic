package com.xicheng.springboot;

import org.apache.catalina.startup.Tomcat;
import org.springframework.stereotype.Component;

/**
 * description
 *
 * @author xichengxml
 * @date 2021/1/29 下午 11:17
 */
@Component
public class MyTomcat implements WebServerFactory {
    @Override
    public void createServer() throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8088);

        tomcat.addContext("/", "D://tomcattest");
        tomcat.start();
        tomcat.getServer().await();
    }
}
