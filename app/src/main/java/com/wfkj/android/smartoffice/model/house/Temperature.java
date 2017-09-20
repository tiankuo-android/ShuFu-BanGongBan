package com.wfkj.android.smartoffice.model.house;

/**
 *
 * 温湿度
 * Created by wangdongyang on 16/12/13.
 */
public class Temperature {

    private String type;
    private String name;
    private String id;
    private String modleNumber;
    private String dataModleNumber;
    private String dataPointNumber;
    private String dataPointRefrigeration;
    private String dataPointHeating;

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

    public String getDataPointRefrigeration() {
        return dataPointRefrigeration;
    }

    public void setDataPointRefrigeration(String dataPointRefrigeration) {
        this.dataPointRefrigeration = dataPointRefrigeration;
    }

    public String getDataPointNumber() {
        return dataPointNumber;
    }

    public void setDataPointNumber(String dataPointNumber) {
        this.dataPointNumber = dataPointNumber;
    }

    public String getDataPointHeating() {
        return dataPointHeating;
    }

    public void setDataPointHeating(String dataPointHeating) {
        this.dataPointHeating = dataPointHeating;
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
        return "Temperature{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", modleNumber='" + modleNumber + '\'' +
                ", dataModleNumber='" + dataModleNumber + '\'' +
                ", dataPointNumber='" + dataPointNumber + '\'' +
                ", dataPointRefrigeration='" + dataPointRefrigeration + '\'' +
                ", dataPointHeating='" + dataPointHeating + '\'' +
                '}';
    }
}
