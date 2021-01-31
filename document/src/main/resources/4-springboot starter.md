1. 为什么在sringboot项目中可以省略版本号
因为父项目spring-boot-starter-parent的父项目spring-boot-dependencies中DependencyManagement定义了一些基本的jar包版本
但是不能直接依赖spring-boot-dependencies，因为spring-boot-starter-parent还定义了配置文件，可以自定义配置
```
<resource>
  <directory>${basedir}/src/main/resources</directory>
  <filtering>true</filtering>
  <includes>
    <include>**/application*.yml</include>
    <include>**/application*.yaml</include>
    <include>**/application*.properties</include>
  </includes>
</resource>
```
#### 复合注解
@SpringBootApplication
```
// java元注解，用来修饰自定义注解的注解
@Target(ElementType.TYPE) // 定义注解的使用范围
@Retention(RetentionPolicy.RUNTIME) // 定义注解的生命周期    编译期，运行期
@Documented  // javadoc 
@Inherited // 被修饰的自定义注解可被子类继承


@SpringBootConfiguration // 实际上就是Configuration注解，所以在启动类里可以使用@Bean生成bean
@EnableAutoConfiguration
@ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication
```

@SpringBootConfiguration
```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
public @interface SpringBootConfiguration {
    
    // 默认使用cglib代理该类
	@AliasFor(annotation = Configuration.class)
	boolean proxyBeanMethods() default true;

}
```

@Configuration与@Component：
* proxyBeanMethods()为false时两个注解一样
* 前者可以保证单例，后者不可以

@EnableAutoConfiguration
```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited

@AutoConfigurationPackage
@Import(AutoConfigurationImportSelector.class) // 导入参数到IOC容器
public @interface EnableAutoConfiguration {

	String ENABLED_OVERRIDE_PROPERTY = "spring.boot.enableautoconfiguration";

	Class<?>[] exclude() default {};
	String[] excludeName() default {};

}
```

@AutoConfigurationPackage
```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(AutoConfigurationPackages.Registrar.class) // 保存扫描路径，提供给spring-data-jpa查询
public @interface AutoConfigurationPackage {

	String[] basePackages() default {};

	Class<?>[] basePackageClasses() default {};

}
```

@Import 自动装配的核心注解
```
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Import {
    Class<?>[] value();
}
```

springboot自动装配核心注解@Import：
* 如果修饰的是普通类，直接将实例交给spring ioc容器管理
* 如果修饰的是org.springframework.context.annotation.ImportBeanDefinitionRegistrar的实现类，支持手工注册bean
* 如果是ImportSelector实现类，注册返回的数组（类的全路径）到IOC容器，实现批量注册

#### 自动装配原理

org.springframework.boot.autoconfigure.AutoConfigurationPackages.Registrar.registerBeanDefinitions
-> org.springframework.boot.autoconfigure.AutoConfigurationPackages.PackageImports.PackageImports

核心：
org.springframework.boot.autoconfigure.AutoConfigurationImportSelector.selectImports
-> org.springframework.boot.autoconfigure.AutoConfigurationImportSelector.getAutoConfigurationEntry
-> org.springframework.boot.autoconfigure.AutoConfigurationImportSelector.getCandidateConfigurations

#### 自定义Starter组件
1. 新建两个模块：
* ×××-autoconfig：完成自动配置的核心功能
* ×××-starter：管理pom依赖
2. 使用@configurationProperties接收配置参数
3. 使用@configuration+@Bean注册需要的bean，使用@EnableConfigurationProperties开启参数接收
4. 加载配置类: 
* 利用META-INF/spring.factories加载
* 在启动类上使用Import注解加载
* 自定义注解加载

