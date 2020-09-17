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
    private Class type;

    private String name;

    private boolean ifList;

    //是否为javabean类型
    private boolean ifBean;

    private boolean ifFilter = false;

    //泛型类
    private Class genericsClass;

    //泛型是否为javabean类型
    private boolean genericsIfBean;
}
