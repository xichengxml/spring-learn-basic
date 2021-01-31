package com.xicheng.hotdeploy.common;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * description
 *
 * @author xichengxml
 * @date 2021/1/31 下午 10:43
 */
public class MyClassLoader extends ClassLoader {

    /**
     * 定义加载路径，可以加载不在classpath的类，或者classpath下希望手动加载的类
     */
    private String path;

    private Set<String> loadedClassName = new HashSet<>();

    public MyClassLoader(String path) {
        this.path = path;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return loadClass(name, false);
    }

    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> c = findLoadedClass(name);
        if (c == null) {
            try {
                c = getData(new File(name));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 如果要打破双亲委派，去掉这部分即可；但是会造成类型不唯一，有安全问题
        if (c == null) {
            // 使用AppClassLoader
            c = getSystemClassLoader().loadClass(name);
        }
        return c;
    }


    private Class<?> getData(File file) throws Exception {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (ArrayUtils.isEmpty(files)) {
                return null;
            }
            for (File listFile : files) {
                getData(listFile);
            }
        } else {
            if (file.getName().endsWith(".class") && file.getName().contains("com.xicheng")) {
                file = new File(path + file.getName().replace("\\.", "/") + ".class");
                if (!file.getName().endsWith(".class")) {
                    return null;
                }
                FileInputStream fileInputStream = new FileInputStream(file);
                int available = fileInputStream.available();
                byte[] bytes = new byte[available];
                int read = fileInputStream.read(bytes);
                fileInputStream.close();

                return defineClass(getAbsolutePath(file), bytes, 0, bytes.length);
            }
        }
        return null;
    }

    private String getAbsolutePath(File file) {
        String name = file.getPath().replace(new File(path).getPath(), "").replace("\\\\", ".");
        return name.substring(1, name.lastIndexOf("."));
    }
}
