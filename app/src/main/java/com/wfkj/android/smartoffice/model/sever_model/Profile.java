package com.wfkj.android.smartoffice.model.sever_model;

/**
 * 配置文件
 * Created by wangdongyang on 17/1/4.
 */
public class Profile {

    private String firstName;//名字
    private String avatarPath;//头像
    private String avatarBaseUrl;
    private String locale;
    private String gender;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAvatarBaseUrl() {
        return avatarBaseUrl;
    }

    public void setAvatarBaseUrl(String avatarBaseUrl) {
        this.avatarBaseUrl = avatarBaseUrl;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "firstName='" + firstName + '\'' +
                ", avatarPath='" + avatarPath + '\'' +
                ", avatarBaseUrl='" + avatarBaseUrl + '\'' +
                ", locale='" + locale + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
