package com.ksc.epc;

import com.ksc.epc.model.BaseResult;
import com.ksc.epc.model.OpsEpcRequest;

public interface KSCEPC  {

/**
* 重启物理机
* @param opsEpcRequest OpsEpcRequest
* @return BaseResult
*/
BaseResult rebootEpc(OpsEpcRequest opsEpcRequest);
}