package com.wfkj.android.smartoffice.model.sever_model;

/**
 * Created by wangdongyang on 17/2/8.
 */
public class SignOut {

    private String status;
    private String message;
    private int code;

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
}
