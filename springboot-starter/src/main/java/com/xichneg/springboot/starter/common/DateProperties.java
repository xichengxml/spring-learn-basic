package com.xichneg.springboot.starter.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * description 根据经度或市区计算当地时间
 *
 * @author xichengxml
 * @date 2021/1/31 下午 05:32
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "util.date")
public class DateProperties {

    private Double latitude = 120d;

    private int zone;

    private String pattern = "yyyy-MM-dd HH:mm:ss";

}
