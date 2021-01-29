package com.xicheng.framework.main.service;

import com.xicheng.framework.basic.BeanPostProcessor;
import com.xicheng.framework.basic.Component;

/**
 * description
 *
 * @author xichengxml
 * @date 2021/1/18 下午 10:38
 */
@Component
public class LubanBeanPostProcessor implements BeanPostProcessor {
    @Override
    public void postProcessBeforeInitialization(String beanName, Object bean) {
        System.out.println("do nothing");
    }

    @Override
    public void postProcessAfterInitialization(String beanName, Object bean) {
        System.out.println("初始化之后");
    }
}
