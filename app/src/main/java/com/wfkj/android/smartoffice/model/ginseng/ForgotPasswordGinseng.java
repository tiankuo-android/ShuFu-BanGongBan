package com.wfkj.android.smartoffice.model.ginseng;

/**
 * 修改密码-提交
 * Created by wangdongyang on 17/1/26.
 */
public class ForgotPasswordGinseng {

    private String password;
    private String password_new;
    private String password_confirm;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_new() {
        return password_new;
    }

    public void setPassword_new(String password_new) {
        this.password_new = password_new;
    }

    public String getPassword_confirm() {
        return password_confirm;
    }

    public void setPassword_confirm(String password_confirm) {
        this.password_confirm = password_confirm;
    }

    @Override
    public String toString() {
        return "ForgotPasswordGinseng{" +
                "password='" + password + '\'' +
                ", password_new='" + password_new + '\'' +
                ", password_confirm='" + password_confirm + '\'' +
                '}';
    }
}
