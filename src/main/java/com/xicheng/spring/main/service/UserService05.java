package com.xicheng.spring.main.service;

import com.xicheng.spring.basic.Autowired;
import com.xicheng.spring.basic.BeanNameAware;
import com.xicheng.spring.basic.Component;
import com.xicheng.spring.basic.InitializingBean;

/**
 * description InitializingBean
 *
 * @author xichengxml
 * @date 2021/1/17 上午 10:27
 */
@Component("userService05")
public class UserService05 implements InitializingBean {

	@Autowired
	private User user;

	private String beanName;

	public void printBeanName() {
		System.out.println(beanName);
	}

	@Override
	public void afterPropertiesSet() {
		this.beanName = "初始化";
	}
}
