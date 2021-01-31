package com.xicheng.hotdeploy;

import com.xicheng.hotdeploy.common.MyClassLoader;
import com.xicheng.hotdeploy.web.HelloController;

/**
 * description 自定义类加载器实现类的加兹和卸载
 *
 * @author xichengxml
 * @date 2021/1/31 下午 11:26
 */
public class Starter {

    private static final String CLASS_NAME = "com.xicheng.hotdeploy.web.HelloController";

    public static void main(String[] args) throws Exception {
        String path = MyClassLoader.class.getResource("/").getPath();
        MyClassLoader classLoader = new MyClassLoader(path);
        Class<?> clazz01 = classLoader.loadClass(CLASS_NAME, false);
        System.out.println(clazz01.hashCode());

        Class<?> clazz02 = classLoader.loadClass(CLASS_NAME);
        System.out.println(clazz02.hashCode());

        classLoader = null;
        clazz02 = null;

        classLoader = new MyClassLoader(path);
        clazz02 = classLoader.loadClass(CLASS_NAME);
        System.out.println(clazz02.hashCode());
    }
}
