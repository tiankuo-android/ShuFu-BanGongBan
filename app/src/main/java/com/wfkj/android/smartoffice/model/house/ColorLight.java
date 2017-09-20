package com.wfkj.android.smartoffice.model.house;

/**
 * 彩灯
 * Created by wangdongyang on 16/12/13.
 */
public class ColorLight {

    private String lightName;
    private String type;
    private String lightID;
    private boolean switchState;
    private String dataPoint;

    public String getLightName() {
        return lightName;
    }

    public void setLightName(String lightName) {
        this.lightName = lightName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDataPoint() {
        return dataPoint;
    }

    public void setDataPoint(String dataPoint) {
        this.dataPoint = dataPoint;
    }

    public String getLightID() {
        return lightID;
    }

    public void setLightID(String lightID) {
        this.lightID = lightID;
    }

    public boolean isSwitchState() {
        return switchState;
    }

    public void setSwitchState(boolean switchState) {
        this.switchState = switchState;
    }

    @Override
    public String toString() {
        return "ColorLight{" +
                "lightName='" + lightName + '\'' +
                ", type='" + type + '\'' +
                ", lightID='" + lightID + '\'' +
                ", switchState=" + switchState +
                ", dataPoint='" + dataPoint + '\'' +
                '}';
    }
}
