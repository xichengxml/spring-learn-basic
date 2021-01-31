package com.xichneg.springboot.starter.web;

import com.xichneg.springboot.starter.util.DateUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * description
 *
 * @author xichengxml
 * @date 2021/1/31 下午 04:22
 */
@RestController
public class HellController {

    @Resource(name = "dateUtilFromSpringFactories")
    private DateUtil dateUtilFromSpringFactories;

    @Resource(name = "dateUtilFromImport")
    private DateUtil dateUtilFromImport;

    @Resource(name = "dateUtilFromAnnotation")
    private DateUtil dateUtilFromAnnotation;

    @RequestMapping("/hello")
    public Map<String, String> hello() {
        Map<String, String> dateMap = new HashMap<>();
        dateMap.put("dateUtilFromSpringFactories", dateUtilFromSpringFactories.getLocalTime());
        dateMap.put("dateUtilFromImport", dateUtilFromImport.getLocalTime());
        dateMap.put("dateUtilFromAnnotation", dateUtilFromAnnotation.getLocalTime());
        return dateMap;

    }
}
