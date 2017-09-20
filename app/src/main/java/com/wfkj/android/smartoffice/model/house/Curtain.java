package com.wfkj.android.smartoffice.model.house;

/**
 *
 * 窗帘
 * Created by wangdongyang on 16/12/13.
 */
public class Curtain {

    private String type;
    private String curatinName;
    private String curatinID;
    private String dataPointOpen;
    private String dataPointClose;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCuratinName() {
        return curatinName;
    }

    public void setCuratinName(String curatinName) {
        this.curatinName = curatinName;
    }

    public String getCuratinID() {
        return curatinID;
    }

    public void setCuratinID(String curatinID) {
        this.curatinID = curatinID;
    }

    public String getDataPointOpen() {
        return dataPointOpen;
    }

    public void setDataPointOpen(String dataPointOpen) {
        this.dataPointOpen = dataPointOpen;
    }

    public String getDataPointClose() {
        return dataPointClose;
    }

    public void setDataPointClose(String dataPointClose) {
        this.dataPointClose = dataPointClose;
    }

    @Override
    public String toString() {
        return "Curtain{" +
                "type='" + type + '\'' +
                ", curatinName='" + curatinName + '\'' +
                ", curatinID='" + curatinID + '\'' +
                ", dataPointOpen='" + dataPointOpen + '\'' +
                ", dataPointClose='" + dataPointClose + '\'' +
                '}';
    }
}
