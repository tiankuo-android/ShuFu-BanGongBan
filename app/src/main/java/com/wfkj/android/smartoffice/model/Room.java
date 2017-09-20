package com.wfkj.android.smartoffice.model;

import java.util.List;

/**
 * 房间
 * Created by wangdongyang on 16/11/22.
 */
public class Room {

    private String name;
    private String tag;
    private List<Equipment> equipments;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                ", tag='" + tag + '\'' +
                ", equipments=" + equipments +
                '}';
    }
}
