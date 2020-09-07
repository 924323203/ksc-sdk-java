package com.ksc.epc.generator.model;

import lombok.Data;

/**
 * @author create by WANGXIAOHAN
 * @Date 2020-09-04 11:22
 */
@Data
public class InterfaceInfo {
    private String returnType;

    private String paramType;

    private String action;

    private String javadoc;
}
