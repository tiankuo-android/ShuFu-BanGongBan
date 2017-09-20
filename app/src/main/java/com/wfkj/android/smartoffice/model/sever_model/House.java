package com.wfkj.android.smartoffice.model.sever_model;

import java.util.ArrayList;

/**
 *
 * 房屋
 * Created by wangdongyang on 17/1/4.
 */
public class House {

    private int id;
    private String name;
    private String country;
    private String state;
    private String city;
    private String address;
    private int status;
    private String role;//owner=管理员
    private ArrayList<Room> rooms;
    private ArrayList<Gateway> gateways;
    private ArrayList<UserHouse> userHouses;

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public ArrayList<Gateway> getGateways() {
        return gateways;
    }

    public void setGateways(ArrayList<Gateway> gateways) {
        this.gateways = gateways;
    }

    public ArrayList<UserHouse> getUserHouses() {
        return userHouses;
    }

    public void setUserHouses(ArrayList<UserHouse> userHouses) {
        this.userHouses = userHouses;
    }

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", role='" + role + '\'' +
                ", rooms=" + rooms +
                ", gateways=" + gateways +
                ", userHouses=" + userHouses +
                '}';
    }
}
