package com.xicheng.spring.main;

import com.xicheng.spring.basic.LubanApplicationContext;
import com.xicheng.spring.main.service.UserService03;
import com.xicheng.spring.main.service.UserService04;
import com.xicheng.spring.main.service.UserService05;
import com.xicheng.spring.main.service.UserService06;

import java.util.Scanner;

/**
 * description
 *
 * @author xichengxml
 * @date 2021/1/17 上午 10:14
 */
public class AppTest {


	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		String next = scanner.next();
		switch (next) {
			case "1":
				singletonTest();
				break;
			case "2":
				prototypeTest();
				break;
			case "3":
				autowiredTest();
				break;
			case "4":
				awareTest();
				break;
			case "5":
				initializingBeanTest();
				break;
			case "6":
				beanPostProcessorTest();
				break;
			default:
				break;
		}
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

	private static void awareTest() throws Exception {
		LubanApplicationContext applicationContext = new LubanApplicationContext(BaseConfig.class);
		UserService04 userService04 = (UserService04) applicationContext.getBean("userService04");
		System.out.println(userService04);
		userService04.printBeanName();
	}

	private static void initializingBeanTest() throws Exception {
		LubanApplicationContext applicationContext = new LubanApplicationContext(BaseConfig.class);
		UserService05 userService05 = (UserService05) applicationContext.getBean("userService05");
		userService05.printBeanName();
	}

	private static void beanPostProcessorTest() throws Exception{
		LubanApplicationContext applicationContext = new LubanApplicationContext(BaseConfig.class);
		UserService06 userService06 = (UserService06) applicationContext.getBean("userService06");
	}
}
