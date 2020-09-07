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

    private String name;

    private boolean ifList;

    private boolean ifFilter = false;

    //泛型类
    private String genericsClassName;
}
