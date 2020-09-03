package com.ksc.epc.model;

import com.ksc.KscWebServiceRequest;
import com.ksc.Request;
import com.ksc.epc.model.transform.OpsEpcRequestMarshaller;
import com.ksc.model.DryRunSupportedRequest;

public class OpsEpcRequest extends KscWebServiceRequest implements DryRunSupportedRequest<OpsEpcRequest> {

    private String hostId;

    private String epcHostId;

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getEpcHostId() {
        return epcHostId;
    }

    public void setEpcHostId(String epcHostId) {
        this.epcHostId = epcHostId;
    }

    @Override
    public String toString() {
        return "OpsEpcRequest{" +
                "hostId='" + hostId + '\'' +
                ", epcHostId='" + epcHostId + '\'' +
                '}';
    }

    @Override
    public Request<OpsEpcRequest> getDryRunRequest() {
        Request<OpsEpcRequest> request = new OpsEpcRequestMarshaller().marshall(this);
        request.addParameter("DryRun", Boolean.toString(true));
        return request;
    }
}
