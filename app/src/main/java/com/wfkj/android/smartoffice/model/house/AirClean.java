package com.wfkj.android.smartoffice.model.house;

/**
 * Created by wangdongyang on 16/12/13.
 */
public class AirClean {

    private String type;
    private String name;
    private String id;

    private String modbus_co2;
    private String modbus_HCHO;
    private String modbus_pm25;
    private String modbus_voc;

    private String dataModbus_co2;
    private String dataModbus_HCHO;
    private String dataModbus_pm25;
    private String dataModbus_voc;

    private String event_co2;
    private String event_HCHO;
    private String event_pm25;
    private String event_voc;

    public String getModbus_co2() {
        return modbus_co2;
    }

    public void setModbus_co2(String modbus_co2) {
        this.modbus_co2 = modbus_co2;
    }

    public String getModbus_HCHO() {
        return modbus_HCHO;
    }

    public void setModbus_HCHO(String modbus_HCHO) {
        this.modbus_HCHO = modbus_HCHO;
    }

    public String getModbus_pm25() {
        return modbus_pm25;
    }

    public void setModbus_pm25(String modbus_pm25) {
        this.modbus_pm25 = modbus_pm25;
    }

    public String getEvent_co2() {
        return event_co2;
    }

    public void setEvent_co2(String event_co2) {
        this.event_co2 = event_co2;
    }

    public String getModbus_voc() {
        return modbus_voc;
    }

    public void setModbus_voc(String modbus_voc) {
        this.modbus_voc = modbus_voc;
    }

    public String getEvent_HCHO() {
        return event_HCHO;
    }

    public void setEvent_HCHO(String event_HCHO) {
        this.event_HCHO = event_HCHO;
    }

    public String getEvent_pm25() {
        return event_pm25;
    }

    public void setEvent_pm25(String event_pm25) {
        this.event_pm25 = event_pm25;
    }

    public String getEvent_voc() {
        return event_voc;
    }

    public void setEvent_voc(String event_voc) {
        this.event_voc = event_voc;
    }

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

    public String getDataModbus_co2() {
        return dataModbus_co2;
    }

    public void setDataModbus_co2(String dataModbus_co2) {
        this.dataModbus_co2 = dataModbus_co2;
    }

    public String getDataModbus_HCHO() {
        return dataModbus_HCHO;
    }

    public void setDataModbus_HCHO(String dataModbus_HCHO) {
        this.dataModbus_HCHO = dataModbus_HCHO;
    }

    public String getDataModbus_pm25() {
        return dataModbus_pm25;
    }

    public void setDataModbus_pm25(String dataModbus_pm25) {
        this.dataModbus_pm25 = dataModbus_pm25;
    }

    public String getDataModbus_voc() {
        return dataModbus_voc;
    }

    public void setDataModbus_voc(String dataModbus_voc) {
        this.dataModbus_voc = dataModbus_voc;
    }

    @Override
    public String toString() {
        return "AirClean{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", modbus_co2='" + modbus_co2 + '\'' +
                ", modbus_HCHO='" + modbus_HCHO + '\'' +
                ", modbus_pm25='" + modbus_pm25 + '\'' +
                ", modbus_voc='" + modbus_voc + '\'' +
                ", dataModbus_co2='" + dataModbus_co2 + '\'' +
                ", dataModbus_HCHO='" + dataModbus_HCHO + '\'' +
                ", dataModbus_pm25='" + dataModbus_pm25 + '\'' +
                ", dataModbus_voc='" + dataModbus_voc + '\'' +
                ", event_co2='" + event_co2 + '\'' +
                ", event_HCHO='" + event_HCHO + '\'' +
                ", event_pm25='" + event_pm25 + '\'' +
                ", event_voc='" + event_voc + '\'' +
                '}';
    }
}
