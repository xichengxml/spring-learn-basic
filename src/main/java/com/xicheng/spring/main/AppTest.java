package com.xicheng.spring.main;

import com.xicheng.spring.basic.LubanApplicationContext;
import com.xicheng.spring.main.service.UserService03;

/**
 * description
 *
 * @author xichengxml
 * @date 2021/1/17 上午 10:14
 */
public class AppTest {


	public static void main(String[] args) throws Exception {
		singletonTest();
		prototypeTest();
		autowiredTest();
	}

	private static void singletonTest() throws Exception {
		LubanApplicationContext applicationContext = new LubanApplicationContext(BaseConfig.class);
		// 单例测试
		System.out.println(applicationContext.getBean("userService01"));
		System.out.println(applicationContext.getBean("userService01"));
		System.out.println(applicationContext.getBean("userService01"));
	}

	private static void prototypeTest() throws Exception {
		LubanApplicationContext applicationContext = new LubanApplicationContext(BaseConfig.class);
		// 单例测试
		System.out.println(applicationContext.getBean("userService02"));
		System.out.println(applicationContext.getBean("userService02"));
		System.out.println(applicationContext.getBean("userService02"));
	}

	private static void autowiredTest() throws Exception {
		LubanApplicationContext applicationContext = new LubanApplicationContext(BaseConfig.class);
		UserService03 userService03 = (UserService03) applicationContext.getBean("userService03");
		userService03.printUser();
	}
}
