package com.xichneg.springboot.starter.util;

import com.xichneg.springboot.starter.common.DateProperties;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * description
 *
 * @author xichengxml
 * @date 2021/1/31 下午 05:37
 */
public class DateUtil {

    @Autowired
    private DateProperties dateProperties;

    public String getLocalTime() {
        int zone = 0;
        if (dateProperties.getLatitude() != null) {
            zone = (int) Math.round((dateProperties.getLatitude() * DateTimeConstants.HOURS_PER_DAY) / 360);
        }
        DateTimeZone dateTimeZone = DateTimeZone.forOffsetHours(zone);
        return new DateTime(dateTimeZone).toString(dateProperties.getPattern());
    }
}
