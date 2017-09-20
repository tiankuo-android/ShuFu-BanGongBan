package com.wfkj.android.smartoffice.model.weather.sub;

/**
 * Created by liukun on 2016/3/4.
 */
public class Update {
    private String loc;

    private String utc;

    public void setLoc(String loc){
        this.loc = loc;
    }
    public String getLoc(){
        return this.loc;
    }
    public void setUtc(String utc){
        this.utc = utc;
    }
    public String getUtc(){
        return this.utc;
    }

}