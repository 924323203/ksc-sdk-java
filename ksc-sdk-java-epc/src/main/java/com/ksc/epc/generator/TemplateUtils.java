package com.ksc.epc.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author create by WANGXIAOHAN
 * @Date 2020-09-03 18:25
 */
public class TemplateUtils {

    public static void generateForRequest(Class clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            Class<?> fieldClazz = field.getType();
            if (fieldClazz.equals(String.class)) {

            } else if (fieldClazz.equals(Integer.class) || fieldClazz.equals(int.class)) {

            } else if (fieldClazz.equals(Long.class) || fieldClazz.equals(long.class)) {

            } else if (fieldClazz.equals(Double.class) || fieldClazz.equals(double.class)) {

            } else if (fieldClazz.equals(Float.class) || fieldClazz.equals(float.class)) {

            } else if (fieldClazz.equals(Boolean.class) || fieldClazz.equals(boolean.class)) {

            }
        }
    }

    public static void generateMethod() throws Exception {
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setDirectoryForTemplateLoading(
                new File(TemplateUtils.class.getResource("/templates").getPath()));
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("returnType","Result");
        dataMap.put("methodName","test");
        dataMap.put("paramType","123");
        dataMap.put("param","45");

        StringWriter stringWriter=new StringWriter();

        Template template = configuration.getTemplate("interface.ftl");
        template.process(dataMap, stringWriter);

        System.out.println(stringWriter.toString());

    }

    public static void main(String[] args) throws Exception {
        generateMethod();
    }
}
