##### 官网实现
https://docs.spring.io/spring-framework/docs/5.2.12.RELEASE/spring-framework-reference/web.html#mvc-servlet
```
public class MyWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {

        // Load Spring web application configuration
        // 启动IOC容器
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class);

        // Create and register the DispatcherServlet
        // 将DispactcherServlet放到IOC容器中
        DispatcherServlet servlet = new DispatcherServlet(context);
        // 启动Tomcat容器，将DispatcherServlet放到tomcat容器中
        ServletRegistration.Dynamic registration = servletContext.addServlet("app", servlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/app/*");
    }
}
```
