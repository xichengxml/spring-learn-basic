package com.xicheng.spring.basic;

/**
 * description
 *
 * @author xichengxml
 * @date 2021/1/18 下午 10:32
 */
public interface BeanPostProcessor {
    void postProcessBeforeInitialization(String beanName, Object bean);

    void postProcessAfterInitialization(String beanName, Object bean);
}
