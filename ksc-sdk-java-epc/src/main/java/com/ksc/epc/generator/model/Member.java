package com.ksc.epc.generator.model;

import lombok.Data;

/**
 * @author create by WANGXIAOHAN
 * @Date 2020-09-04 16:23
 * <p>
 * 成员变量
 */
@Data
public class Member {
    private String type;

    //首字母大写
    private String name;

    //首字母转大写
    public void setName(String name) {
        if (Character.isUpperCase(name.charAt(0))) {
            this.name = name;
        } else {
            this.name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        }
    }
}
