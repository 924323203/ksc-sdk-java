package com.ksc.epc.model;

import com.ksc.internal.SdkInternalList;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BaseResult {

    private String requestId;

    private Boolean result;

    private SdkInternalList<String> epcIds;

    private Cpu cpu;

    private SdkInternalList<EpcHost> epcHosts;

    private BigDecimal price;

}
