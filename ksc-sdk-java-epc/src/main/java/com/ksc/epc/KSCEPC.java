package com.ksc.epc;

import com.ksc.epc.model.OpsEpcRequest;
import com.ksc.epc.model.BaseResult;

public interface KSCEPC {

    /**
     * 重启物理机
     *
     * @param opsEpcRequest OpsEpcRequest
     * @return BaseResult
     */
    BaseResult rebootEpc(OpsEpcRequest opsEpcRequest);
}