package com.xichneg.springboot.starter.common;

import com.xichneg.springboot.starter.util.DateUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description 使用spring.factories文件注册bean
 *
 * @author xichengxml
 * @date 2021/1/31 下午 05:42
 */
@Configuration
@EnableConfigurationProperties(DateProperties.class)
public class DateConfigUsingSpringFactories {

    @Bean(name = "dateUtilFromSpringFactories")
    public DateUtil getDateUtil() {
        return new DateUtil();
    }
}
