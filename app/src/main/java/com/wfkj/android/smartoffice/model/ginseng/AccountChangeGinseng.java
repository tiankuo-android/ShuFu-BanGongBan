package com.wfkj.android.smartoffice.model.ginseng;

/**
 * Created by wangdongyang on 17/2/8.
 * 账户变更比提交的数据结构
 */
public class AccountChangeGinseng {

    private String name;
    private String avatar_path;
    private String avatar_base_url;
    private String mobile;
    private String email;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar_path() {
        return avatar_path;
    }

    public void setAvatar_path(String avatar_path) {
        this.avatar_path = avatar_path;
    }

    public String getAvatar_base_url() {
        return avatar_base_url;
    }

    public void setAvatar_base_url(String avatar_base_url) {
        this.avatar_base_url = avatar_base_url;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
