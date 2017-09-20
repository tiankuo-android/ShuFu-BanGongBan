package com.wfkj.android.smartoffice.model.ginseng;

/**
 * Created by wangdongyang on 17/1/5.
 */
public class LoginGinseng {

    private String mobile;
    private String password;
    private String house_type;

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

    public String getHouse_type() {
        return house_type;
    }

    public void setHouse_type(String house_type) {
        this.house_type = house_type;
    }

    @Override
    public String toString() {
        return "LoginGinseng{" +
                "mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
