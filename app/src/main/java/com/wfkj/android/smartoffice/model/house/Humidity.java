package com.wfkj.android.smartoffice.model.house;

/**
 * Created by wangdongyang on 16/12/13.
 */
public class Humidity {

    private String type;
    private String name;
    private String id;
    private String modleNumber;
    private String dataModleNumber;
    private String dataPointNumber;
    private String dataPointHumidification;
    private String dataPointDehumidification;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataPointNumber() {
        return dataPointNumber;
    }

    public void setDataPointNumber(String dataPointNumber) {
        this.dataPointNumber = dataPointNumber;
    }

    public String getDataPointHumidification() {
        return dataPointHumidification;
    }

    public void setDataPointHumidification(String dataPointHumidification) {
        this.dataPointHumidification = dataPointHumidification;
    }

    public String getDataPointDehumidification() {
        return dataPointDehumidification;
    }

    public void setDataPointDehumidification(String dataPointDehumidification) {
        this.dataPointDehumidification = dataPointDehumidification;
    }

    public String getModleNumber() {
        return modleNumber;
    }

    public void setModleNumber(String modleNumber) {
        this.modleNumber = modleNumber;
    }

    public String getDataModleNumber() {
        return dataModleNumber;
    }

    public void setDataModleNumber(String dataModleNumber) {
        this.dataModleNumber = dataModleNumber;
    }

    @Override
    public String toString() {
        return "Humidity{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", modleNumber='" + modleNumber + '\'' +
                ", dataModleNumber='" + dataModleNumber + '\'' +
                ", dataPointNumber='" + dataPointNumber + '\'' +
                ", dataPointHumidification='" + dataPointHumidification + '\'' +
                ", dataPointDehumidification='" + dataPointDehumidification + '\'' +
                '}';
    }
}
