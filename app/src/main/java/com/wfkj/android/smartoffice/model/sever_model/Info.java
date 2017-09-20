package com.wfkj.android.smartoffice.model.sever_model;

/**
 *
 * 信息
 * Created by wangdongyang on 17/1/4.
 */
public class Info {

    private String name;
    private String birthday;
    private String mobile;
    private String telephone;
    private String address;
    private String company;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Info{" +
                "name='" + name + '\'' +
                ", birthday='" + birthday + '\'' +
                ", mobile='" + mobile + '\'' +
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
