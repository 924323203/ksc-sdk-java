package com.ksc.epc.generator;

import com.ksc.epc.generator.model.Member;
import com.ksc.internal.SdkInternalList;
import com.ksc.model.Filter;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author create by WANGXIAOHAN
 * @Date 2020-09-03 10:28
 */
public class ClassUtils {
    /**
     * 获取指定包下的全部类
     *
     * @param packageName 包名
     * @return 类
     * @throws Exception e
     */
    public static List<Class> findAllClass(String packageName) throws Exception {
        List<Class> results = new ArrayList<>();
        String basePath = packageName.replace('.', '/');

        URL url = ClassUtils.class.getClassLoader().getResource(basePath);
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


    /**
     * 获取成员
     *
     * @param clazz 类
     * @return 成员
     */
    public static List<Member> findMembers(Class clazz) {
        List<Member> members = new ArrayList<>();

        for (Field field : clazz.getDeclaredFields()) {
            if ("serialVersionUID".equals(field.getName())) {
                continue;
            }
            Member member = new Member();
            member.setName(field.getName());
            member.setType(field.getType().getSimpleName());
            //是否为List
            if (field.getType().equals(SdkInternalList.class)) {
                Type type = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];

                //是否为Filter
                if (type.equals(Filter.class)) {
                    member.setIfFilter(true);
                }
                member.setIfList(true);
                member.setGenericsClassName(((Class) type).getSimpleName());
            } else {
                member.setIfList(false);
            }
            members.add(member);
        }
        return members;
    }


    public static void main(String[] args) throws Exception {
        for (Class clazz : findAllClass("com.ksc.epc.model")) {
            System.out.println(clazz.getSimpleName());
            for (Member member : findMembers(clazz)) {
                System.out.println(member.toString());
            }
            System.out.println("----------------");
        }
    }


}
