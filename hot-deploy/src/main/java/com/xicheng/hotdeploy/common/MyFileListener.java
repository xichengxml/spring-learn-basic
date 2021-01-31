package com.xicheng.hotdeploy.common;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;

import java.io.File;

/**
 * description
 *
 * @author xichengxml
 * @date 2021/1/31 下午 11:00
 */
public class MyFileListener extends FileAlterationListenerAdaptor {

    @Override
    public void onFileChange(File file) {
        System.out.println("文件修改：" + file.getAbsolutePath());
        new Thread(() -> {
            String root = MyClassLoader.class.getResource("/").getPath().replace("20%", " ");
            MyClassLoader classLoader = new MyClassLoader(root);

            try {
                Class<?> clazz = classLoader.loadClass("com.xicheng.hotdeploy.Application");
                clazz.getDeclaredMethod("start").invoke(clazz.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
