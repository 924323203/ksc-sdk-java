package com.ksc.epc.model;

public class BaseResult {

    private String requestId;

    private Boolean result;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "requestId='" + requestId + '\'' +
                ", result=" + result +
                '}';
    }
}
