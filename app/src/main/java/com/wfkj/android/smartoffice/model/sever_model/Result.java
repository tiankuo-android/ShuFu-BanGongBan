package com.wfkj.android.smartoffice.model.sever_model;

import java.util.List;

/**
 * Created by wangdongyang on 17/2/8.
 */
public class Result {

    private Account account;
    private Profile profile;
    private Info info;
    private List<House> houses;


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<House> getHouses() {
        return houses;
    }

    public void setHouses(List<House> houses) {
        this.houses = houses;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
