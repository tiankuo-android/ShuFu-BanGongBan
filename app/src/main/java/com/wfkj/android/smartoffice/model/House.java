package com.wfkj.android.smartoffice.model;

import java.util.List;

/**
 * 房屋
 * Created by wangdongyang on 16/11/22.
 */
public class House{

    private String name;
    private List<Room> rooms;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "House{" +
                "name='" + name + '\'' +
                ", rooms=" + rooms +
                '}';
    }
}
