package com.wfkj.android.smartoffice.model.ginseng;

/**
 * 忘记密码提交
 * Created by wangdongyang on 17/2/9.
 */
public class ResetPasswordGinseng {
    private String mobile;
    private String verify_code;
    private String password_new;
    private String password_confirm;


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerify_code() {
        return verify_code;
    }

    public void setVerify_code(String verify_code) {
        this.verify_code = verify_code;
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
}
