package com.wfkj.android.smartoffice.model.ginseng;

/**
 * Created by wangdongyang on 17/1/3.
 */
public class RegisteredGinseng {

    private String mobile;
    private String password;
    private String verify_code;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerify_code() {
        return verify_code;
    }

    public void setVerify_code(String verify_code) {
        this.verify_code = verify_code;
    }

    @Override
    public String toString() {
        return "RegisteredGinseng{" +
                "mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", verify_code='" + verify_code + '\'' +
                '}';
    }
}
