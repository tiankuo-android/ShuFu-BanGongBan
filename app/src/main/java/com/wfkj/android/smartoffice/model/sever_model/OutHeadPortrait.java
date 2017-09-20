package com.wfkj.android.smartoffice.model.sever_model;

/**
 * Created by wangdongyang on 17/2/8.
 */
public class OutHeadPortrait {

    private String status;
    private String message;
    private int code;
    private HeadPortrait result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public HeadPortrait getResult() {
        return result;
    }

    public void setResult(HeadPortrait result) {
        this.result = result;
    }
}
