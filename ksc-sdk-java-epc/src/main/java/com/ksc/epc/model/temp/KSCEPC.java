package com.ksc.epc.model.temp;

import com.ksc.epc.model.BaseResult;
import com.ksc.epc.model.GetEpcRequest;
import com.ksc.epc.model.GetEpcResult;
import com.ksc.epc.model.ListEpcsRequest;
import com.ksc.epc.model.ListEpcsResult;
import com.ksc.epc.model.OpsEpcRequest;

public interface KSCEPC  {
	/**
	 * 获取云物理主机列表信息
	 * @param listEpcsRequest
	 * @return ListEpcsResult
	 */
	ListEpcsResult listEpcs(ListEpcsRequest listEpcsRequest);
	/**
	 * 获取云物理机详细信息
	 * @param getEpcRequest
	 * @return getEpcResult
	 */
	GetEpcResult getEpc(GetEpcRequest getEpcRequest);

	/**
	 * 重启物理机
	 * @param opsEpcRequest OpsEpcRequest
	 * @return BaseResult
	 */
	BaseResult rebootEpc(OpsEpcRequest opsEpcRequest);
}
