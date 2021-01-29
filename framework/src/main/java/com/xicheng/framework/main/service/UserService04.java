package com.xicheng.framework.main.service;

import com.xicheng.framework.basic.Autowired;
import com.xicheng.framework.basic.Component;
import com.xicheng.framework.basic.BeanNameAware;

/**
 * description aware回调
 *
 * @author xichengxml
 * @date 2021/1/17 上午 10:27
 */
@Component("userService04")
public class UserService04 implements BeanNameAware {

	@Autowired
	private User user;

	private String beanName;

	public void printBeanName() {
		System.out.println(beanName);
	}

	@Override
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
}
