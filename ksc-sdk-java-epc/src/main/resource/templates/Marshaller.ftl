package com.ksc.epc.model.transform;

import com.ksc.DefaultRequest;
import com.ksc.KscClientException;
import com.ksc.Request;
import com.ksc.epc.model.${requestType};
import com.ksc.http.HttpMethodName;
import com.ksc.transform.Marshaller;
import com.ksc.util.StringUtils;

public class ${requestType}Marshaller implements Marshaller<Request<${requestType}>, ${requestType}> {

	@Override
	public Request<${requestType}> marshall(${requestType} ${requestParam}) {
		if (opsEpcRequest == null) {
			throw new KscClientException("Invalid argument passed to marshall(...)");
		}
		Request<${requestType}> request = new DefaultRequest<${requestType}>(${requestParam}, "epc");
		request.addParameter("Action", ${action});
		String version = ${requestParam}.getVersion();
		if (org.apache.commons.lang.StringUtils.isBlank(version)) {
			version = "2015-11-01";
		}
		request.addParameter("Version", version);
		request.setHttpMethod(HttpMethodName.GET);
		<#list members as meneber>
		if (${requestParam}.get${meneber.name}() != null) {
			request.addParameter("${meneber.name}", StringUtils.fromString(opsEpcRequest.get${meneber.name}()));
		}
		</#list>
		if (opsEpcRequest.getEpcHostId() != null) {
			request.addParameter("EpcHostId", StringUtils.fromString(opsEpcRequest.getEpcHostId()));
		}
		return request;
	}

}
