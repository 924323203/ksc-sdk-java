package com.ksc.epc.generator.model;

import lombok.Data;

import java.util.List;

/**
 * @author create by WANGXIAOHAN
 * @Date 2020-09-04 17:35
 */
@Data
public class Marshaller {
    private String requestType;

    private String requestParam;

    private String action;

    private List<Member> members;
}
