package com.wfkj.android.smartoffice.model.sever_model;

import java.util.ArrayList;

/**
 *
 * 设备类型
 * Created by wangdongyang on 17/1/4.
 */
public class Categorie {

    private int id;
    private String name;
    private ArrayList<Device> devices;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Device> getDevices() {
        return devices;
    }

    public void setDevices(ArrayList<Device> devices) {
        this.devices = devices;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", devices=" + devices +
                '}';
    }
}
