package com.wfkj.android.smartoffice.model;

/**
 * 设备
 * Created by wangdongyang on 16/11/22.
 */
public class Equipment {

//    "title": "直线开窗器",
//            "tag": "modbus_304_straightline_window_switch",
//            "open": "modbus_o304_straightline_open_window",
//            "close": "modbus_c304_straightline_close_window",
//            "room": "304",
//            "brand": "王府",
//            "type": "开窗器",
//            "model": "WF25",
//            "production_date": "2016-01-01",
//            "energy_consumption": "0.5w ",
//            "feature": "window"
    private String title;//设备名称
    private String tag;//设备唯一标识
    private String open;
    private String close;
    private String room;//所属房间
    private String brand;
    private String type; // 1:可操作设备  2:用于监测的设备
    private String number;//获取机智云传递过来的设备数据
    private String model;
    private String production_date;
    private String energy_consumption;
    private String feature;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProduction_date() {
        return production_date;
    }

    public void setProduction_date(String production_date) {
        this.production_date = production_date;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getEnergy_consumption() {
        return energy_consumption;
    }

    public void setEnergy_consumption(String energy_consumption) {
        this.energy_consumption = energy_consumption;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "title='" + title + '\'' +
                ", tag='" + tag + '\'' +
                ", open='" + open + '\'' +
                ", close='" + close + '\'' +
                ", room='" + room + '\'' +
                ", brand='" + brand + '\'' +
                ", type='" + type + '\'' +
                ", number='" + number + '\'' +
                ", model='" + model + '\'' +
                ", production_date='" + production_date + '\'' +
                ", energy_consumption='" + energy_consumption + '\'' +
                ", feature='" + feature + '\'' +
                '}';
    }
}
