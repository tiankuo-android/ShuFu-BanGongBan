package com.wfkj.android.smartoffice.model.sever_model;

/**
 *
 * 网关
 * Created by wangdongyang on 17/1/4.
 */
public class Gateway {

    private int id;
    private String uuid;
    private String name;
    private String title;
    private String extid;//prodectkey
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
    private long created_at;
    private long updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getManufactory() {
        return manufactory;
    }

    public void setManufactory(String manufactory) {
        this.manufactory = manufactory;
    }

    public String getExtid() {
        return extid;
    }

    public void setExtid(String extid) {
        this.extid = extid;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getIpv6() {
        return ipv6;
    }

    public void setIpv6(String ipv6) {
        this.ipv6 = ipv6;
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


    @Override
    public String toString() {
        return "Gateway{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", extid='" + extid + '\'' +
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
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
