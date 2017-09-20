package com.wfkj.android.smartoffice.model.house;

import java.util.List;

/**
 * 房间
 * Created by wangdongyang on 16/12/13.
 */
public class Room {
    private String roomID;
    private String roomName;
    private List<Light> lights;
    private List<ColorLight> colorLights;
    private Temperature temperature;
    private Humidity humidity;
    private List<Curtain> curtains;
    private AirClean airClean;

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public List<Light> getLights() {
        return lights;
    }

    public void setLights(List<Light> lights) {
        this.lights = lights;
    }

    public List<ColorLight> getColorLights() {
        return colorLights;
    }

    public void setColorLights(List<ColorLight> colorLights) {
        this.colorLights = colorLights;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Humidity getHumidity() {
        return humidity;
    }

    public void setHumidity(Humidity humidity) {
        this.humidity = humidity;
    }

    public List<Curtain> getCurtains() {
        return curtains;
    }

    public void setCurtains(List<Curtain> curtains) {
        this.curtains = curtains;
    }

    public AirClean getAirClean() {
        return airClean;
    }

    public void setAirClean(AirClean airClean) {
        this.airClean = airClean;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomID='" + roomID + '\'' +
                ", roomName='" + roomName + '\'' +
                ", lights=" + lights +
                ", colorLights=" + colorLights +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", curtains=" + curtains +
                ", airClean=" + airClean +
                '}';
    }
}
