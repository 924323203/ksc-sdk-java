package com.ksc.epc.model.transform;

import com.ksc.DefaultRequest;
import com.ksc.KscClientException;
import com.ksc.Request;
import com.ksc.epc.model.OpsEpcRequest;
import com.ksc.http.HttpMethodName;
import com.ksc.transform.Marshaller;
import com.ksc.util.StringUtils;

public class OpsEpcRequestMarshaller implements Marshaller<Request<OpsEpcRequest>, OpsEpcRequest> {

	@Override
	public Request<OpsEpcRequest> marshall(OpsEpcRequest opsEpcRequest) {
		if (opsEpcRequest == null) {
			throw new KscClientException("Invalid argument passed to marshall(...)");
		}
		Request<OpsEpcRequest> request = new DefaultRequest<OpsEpcRequest>(opsEpcRequest, "epc");
		request.addParameter("Action", "RebootEpc");
		String version = opsEpcRequest.getVersion();
		if (org.apache.commons.lang.StringUtils.isBlank(version)) {
			version = "2015-11-01";
		}
		request.addParameter("Version", version);
		request.setHttpMethod(HttpMethodName.GET);
		if (opsEpcRequest.getHostId() != null) {
			request.addParameter("HostId", StringUtils.fromString(opsEpcRequest.getHostId()));
		}
		if (opsEpcRequest.getEpcHostId() != null) {
			request.addParameter("EpcHostId", StringUtils.fromString(opsEpcRequest.getEpcHostId()));
		}
		return request;
	}

}
