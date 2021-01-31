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

#### SPI规范
(services provider interface)

用于服务发现、打破双亲委派

1. 建文件夹 WEB-INF/services
2. 建文件，文件名：接口全路径，文件内容：实现类全路径
3. 自定义上下文类加载器（所有类加载器的共享区域），加载第2步的接口和实现类

#### servlet规范
如果是ServletContainerInitializer接口，需要调用onStartUp方法

org.springframework.web.SpringServletContainerInitializer

#### 双亲委派
* BootstrapClassLoader: 加载rt.jar
* ExtClassLoader：加载lib
* AppClassLoader：加载classpath

应用在类加载的时候，会首先委托给上层类加载器加载，如果不在上层类加载的加载路径，才会由下层类加载器加载。所有的类必须传递到最上层类加载器

保证类的安全

按双亲委派机制，SPI的实现父类加载器无法加载到具体的实现类，这时候就需要子类加载器去加载class文件。这样SPI就打破了双亲委派机制。

以JDBC举例，DriverManager类和ServiceLoader类都是属于 rt.jar 的，它们的类加载器是Bootstrap ClassLoader顶层类加载器。
而具体的数据库驱动却属于业务代码，这是启动类加载器是无法加载的。所以java.util.ServiceLoader类进行动态装载时，
使用了线程的上下文类加载器进行加载。

#### 源代码

Spring加载tomcat初始化类使用的就是SPI技术，实现类：org.springframework.web.SpringServletContainerInitializer
类上面的注解@HandlesTypes使用的是字节码技术

IOC容器启动和tomcat启动源码流程参见springboot源码里的注释

DispatcherServlet加载到IOC容器和tomcat上下文的代码入口：spring-boot-autoconfigure包下面的META-INF的spring.factories文件
org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration


org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration.DispatcherServletConfiguration.dispatcherServlet
将DispatcherServlet添加到IOC容器

org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration.DispatcherServletRegistrationConfiguration.dispatcherServletRegistration
依赖DispatcherServlet
它的父类是ServletRegistrationBean，父类的类图关系见uml图 -> ServletContextInitializer

与ServletContainerInitializer关系：与WebApplicationInitializer一样

Tomcat子容器：StandardContext 
-> 添加：org.apache.catalina.core.StandardContext.addServletContainerInitializer
-> tomcat启动时使用SPI调用：org.apache.catalina.core.StandardContext.startInternal
```
for (Map.Entry<ServletContainerInitializer, Set<Class<?>>> entry : initializers.entrySet()) {
    try {
        // SPI实现
        entry.getKey().onStartup(entry.getValue(),getServletContext());
    } catch (ServletException e) {
        log.error(sm.getString("standardContext.sciFail"), e);
        ok = false;
        break;
    }
}
```

ServletContextInitializer的onStartUp被调用的地方：org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.createWebServer
```
this.webServer = factory.getWebServer(getSelfInitializer());
```
->org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.selfInitialize
```
// springboot jar包部署方式
if (webServer == null && servletContext == null) {
	StartupStep createWebServer = this.getApplicationStartup().start("spring.boot.webserver.create");
	ServletWebServerFactory factory = getWebServerFactory();
	createWebServer.tag("factory", factory.getClass().toString());
    // *
	this.webServer = factory.getWebServer(getSelfInitializer());
	createWebServer.end();
	getBeanFactory().registerSingleton("webServerGracefulShutdown",
			new WebServerGracefulShutdownLifecycle(this.webServer));
	getBeanFactory().registerSingleton("webServerStartStop",
			new WebServerStartStopLifecycle(this, this.webServer));
}
// springboot打war包执行，tomcat先执行，上下文非空
else if (servletContext != null) {
	try {
        // 触发实际方法执行
		getSelfInitializer().onStartup(servletContext);
	}
	catch (ServletException ex) {
		throw new ApplicationContextException("Cannot initialize servlet context", ex);
	}
}
```
-> org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory.prepareContext
-> org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory.configureContext
-> 完成ServletContextInitializer取代ServletContainerInitializer：org.springframework.boot.web.embedded.tomcat.TomcatStarter.onStartup

* springboot没有自己实现SPI，利用了tomcat的SPI
* spring自己实现了SPI,使用WebApplicationInitializer取代ServletContainerInitializer


