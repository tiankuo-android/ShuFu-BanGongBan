package com.wfkj.android.smartoffice.model.sever_model;

/**
 * Created by wangdongyang on 17/2/8.
 */
public class Account {

    private String userName;
    private String  email;
    private int status;
    private long createdAt;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
