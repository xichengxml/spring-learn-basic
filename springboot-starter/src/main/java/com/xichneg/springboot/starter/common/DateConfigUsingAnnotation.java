package com.xichneg.springboot.starter.common;

import com.xichneg.springboot.starter.util.DateUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description 使用@Import注解注册bean
 *
 * @author xichengxml
 * @date 2021/1/31 下午 08:41
 */
@Configuration
@EnableConfigurationProperties(DateProperties.class)
public class DateConfigUsingAnnotation {

    @Bean(name = "dateUtilFromAnnotation")
    public DateUtil getDateUtil() {
        return new DateUtil();
    }
}
