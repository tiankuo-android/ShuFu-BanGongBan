package com.wfkj.android.smartoffice.model.weather.sub;

/**
 * Created by wangdongyang on 16/11/27.
 */
public class Air {
    private String brf;
    private String txt;

    public String getBrf() {
        return brf;
    }

    public void setBrf(String brf) {
        this.brf = brf;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    @Override
    public String toString() {
        return "Air{" +
                "brf='" + brf + '\'' +
                ", txt='" + txt + '\'' +
                '}';
    }
}
