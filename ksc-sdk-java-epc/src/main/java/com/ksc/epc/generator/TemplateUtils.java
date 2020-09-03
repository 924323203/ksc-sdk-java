package com.ksc.epc.generator;

import java.io.File;
import java.lang.reflect.Field;

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
}
