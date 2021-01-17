package com.xicheng.spring.main.service;

import com.xicheng.spring.basic.Autowired;
import com.xicheng.spring.basic.Component;
import com.xicheng.spring.basic.Scope;
import com.xicheng.spring.basic.ScopeEnum;

/**
 * description 原型模式的、懒加载的bean
 *
 * @author xichengxml
 * @date 2021/1/17 上午 10:27
 */
@Component("userService03")
public class UserService03 {

	@Autowired
	private User user;

	public void printUser() {
		System.out.println(user);
	}
}
