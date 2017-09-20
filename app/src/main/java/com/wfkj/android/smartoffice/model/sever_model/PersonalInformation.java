package com.wfkj.android.smartoffice.model.sever_model;

import java.util.ArrayList;

/**
 *
 * 个人信息
 * Created by wangdongyang on 17/1/4.
 */
public class PersonalInformation {

    private Profile profile;
    private Info info;
    private ArrayList<House> houses;
    private Oauth oauth;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public ArrayList<House> getHouses() {
        return houses;
    }

    public void setHouses(ArrayList<House> houses) {
        this.houses = houses;
    }

    public Oauth getOauth() {
        return oauth;
    }

    public void setOauth(Oauth oauth) {
        this.oauth = oauth;
    }

    @Override
    public String toString() {
        return "PersonalInformation{" +
                "profile=" + profile +
                ", info=" + info +
                ", houses=" + houses +
                ", oauth=" + oauth +
                '}';
    }
}
