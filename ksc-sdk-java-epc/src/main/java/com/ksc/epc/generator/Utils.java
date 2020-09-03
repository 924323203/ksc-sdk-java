package com.ksc.epc.generator;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author create by WANGXIAOHAN
 * @Date 2020-09-03 10:28
 */
public class Utils {
    public static List<Class> findAllClass(String packageName) throws Exception {
        List<Class> results = new ArrayList<>();
        String basePath = packageName.replace('.', '/');

        URL url = Utils.class.getClassLoader().getResource(basePath);
        if (url == null) {
            return results;
        }

        File root = new File(URLDecoder.decode(url.getFile(), "UTF-8"));
        File[] files = root.listFiles();
        if (files == null) {
            return results;
        }
        for (File file : files) {
            //暂不处理目录
            if (!file.isFile() && !file.getName().endsWith(".class")) {
                continue;
            }
            String className = file.getName().substring(0, file.getName().length() - 6);
            results.add(Class.forName(packageName + "." + className));
        }
        return results;
    }


    public static void main(String[] args) throws Exception {
        for (Class clazz : findAllClass("com.ksc.epc.model")) {
            System.out.println(clazz.getSimpleName());
            for (Field field : clazz.getDeclaredFields()) {
                System.out.println(field.getType().getName() + "  " + field.getName());
            }
            System.out.println("----------------");
        }
    }


}
