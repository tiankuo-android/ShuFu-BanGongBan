package com.wfkj.android.smartoffice.model.sever_model;

/**
 * Created by wangdongyang on 17/1/4.
 */
public class OutModel_PersonalInForMation<T> {

    private String status;
    private String message;
    private int code;
    private PersonalInformation result;
    private T request;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PersonalInformation getResult() {
        return result;
    }

    public void setResult(PersonalInformation result) {
        this.result = result;
    }

    public T getRequest() {
        return request;
    }

    public void setRequest(T request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "OutModel{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", code=" + code +
                ", result=" + result +
                ", request='" + request + '\'' +
                '}';
    }
}
