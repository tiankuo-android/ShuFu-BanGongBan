package com.wfkj.android.smartoffice.model.sever_model;

import java.io.Serializable;

/**
 *
 * 设备
 * Created by wangdongyang on 17/1/4.
 */
public class Device implements Serializable{
    private int id;
    private String name;
    private String uuid;
    private String title;
    private String extid;
    private int category;
    private String type;
    private String data_type;
    private String manufactory;
    private String model;
    private String brand;
    private String production_date;
    private String energy_consumption;
    private String feature;
    private int status;
    private String description;
    private String comment;
    private String holiday;
    private String gateway_ip;
    private String port;
    private String ip;
    private String ipv6;
    private String modbus_cmd;
    private String modbus_register;
    private String register_num;
    private String device_addr;
    private long created_at;
    private long updated_at;
    private int sub_category;

    private int openFlg = 0;
    private int closeFlg = 0;

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExtid() {
        return extid;
    }

    public void setExtid(String extid) {
        this.extid = extid;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public String getManufactory() {
        return manufactory;
    }

    public void setManufactory(String manufactory) {
        this.manufactory = manufactory;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProduction_date() {
        return production_date;
    }

    public void setProduction_date(String production_date) {
        this.production_date = production_date;
    }

    public String getEnergy_consumption() {
        return energy_consumption;
    }

    public void setEnergy_consumption(String energy_consumption) {
        this.energy_consumption = energy_consumption;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    public String getGateway_ip() {
        return gateway_ip;
    }

    public void setGateway_ip(String gateway_ip) {
        this.gateway_ip = gateway_ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIpv6() {
        return ipv6;
    }

    public void setIpv6(String ipv6) {
        this.ipv6 = ipv6;
    }

    public String getModbus_cmd() {
        return modbus_cmd;
    }

    public void setModbus_cmd(String modbus_cmd) {
        this.modbus_cmd = modbus_cmd;
    }

    public String getModbus_register() {
        return modbus_register;
    }

    public void setModbus_register(String modbus_register) {
        this.modbus_register = modbus_register;
    }

    public String getRegister_num() {
        return register_num;
    }

    public void setRegister_num(String register_num) {
        this.register_num = register_num;
    }

    public String getDevice_addr() {
        return device_addr;
    }

    public void setDevice_addr(String device_addr) {
        this.device_addr = device_addr;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public long getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(long updated_at) {
        this.updated_at = updated_at;
    }

    public int getSub_category() {
        return sub_category;
    }

    public void setSub_category(int sub_category) {
        this.sub_category = sub_category;
    }

    public int getOpenFlg() {
        return openFlg;
    }

    public void setOpenFlg(int openFlg) {
        this.openFlg = openFlg;
    }

    public int getCloseFlg() {
        return closeFlg;
    }

    public void setCloseFlg(int closeFlg) {
        this.closeFlg = closeFlg;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", uuid='" + uuid + '\'' +
                ", title='" + title + '\'' +
                ", extid='" + extid + '\'' +
                ", category=" + category +
                ", type='" + type + '\'' +
                ", data_type='" + data_type + '\'' +
                ", manufactory='" + manufactory + '\'' +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", production_date='" + production_date + '\'' +
                ", energy_consumption='" + energy_consumption + '\'' +
                ", feature='" + feature + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", comment='" + comment + '\'' +
                ", holiday='" + holiday + '\'' +
                ", gateway_ip='" + gateway_ip + '\'' +
                ", port='" + port + '\'' +
                ", ip='" + ip + '\'' +
                ", ipv6='" + ipv6 + '\'' +
                ", modbus_cmd='" + modbus_cmd + '\'' +
                ", modbus_register='" + modbus_register + '\'' +
                ", register_num='" + register_num + '\'' +
                ", device_addr='" + device_addr + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", sub_category=" + sub_category +
                ", openFlg=" + openFlg +
                ", closeFlg=" + closeFlg +
                '}';
    }
}
