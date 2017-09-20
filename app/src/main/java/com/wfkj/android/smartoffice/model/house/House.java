package com.wfkj.android.smartoffice.model.house;

import java.util.List;

/**
 * 房屋
 * Created by wangdongyang on 16/12/13.
 */
public class House {
    private String houseName;
    private String houseID;
    private String address;
    private String weatherAddarss;
    private List<Room> rooms;

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getHouseID() {
        return houseID;
    }

    public void setHouseID(String houseID) {
        this.houseID = houseID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public String getWeatherAddarss() {
        return weatherAddarss;
    }

    public void setWeatherAddarss(String weatherAddarss) {
        this.weatherAddarss = weatherAddarss;
    }

    @Override
    public String toString() {
        return "House{" +
                "houseName='" + houseName + '\'' +
                ", houseID='" + houseID + '\'' +
                ", address='" + address + '\'' +
                ", weatherAddarss='" + weatherAddarss + '\'' +
                ", rooms=" + rooms +
                '}';
    }
}
