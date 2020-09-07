package com.ksc.epc.model;

import com.ksc.KscWebServiceRequest;
import com.ksc.Request;
import com.ksc.epc.model.transform.OpsEpcRequestMarshaller;
import com.ksc.internal.SdkInternalList;
import com.ksc.model.DryRunSupportedRequest;
import com.ksc.model.Filter;

public class OpsEpcRequest extends KscWebServiceRequest implements DryRunSupportedRequest<OpsEpcRequest> {

    private String hostId;

    private String epcHostId;

    private SdkInternalList<String> epcIds;

    private SdkInternalList<Filter> filters;

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

    public SdkInternalList<String> getEpcIds() {
        return epcIds;
    }

    public void setEpcIds(SdkInternalList<String> epcIds) {
        this.epcIds = epcIds;
    }

    public SdkInternalList<Filter> getFilters() {
        return filters;
    }

    public void setFilters(SdkInternalList<Filter> filters) {
        this.filters = filters;
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
