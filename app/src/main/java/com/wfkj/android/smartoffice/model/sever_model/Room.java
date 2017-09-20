package com.wfkj.android.smartoffice.model.sever_model;

import java.util.ArrayList;

/**
 * 房间
 * Created by wangdongyang on 17/1/4.
 */
public class Room {

    private int id;
    private String name;
    private int status;
    private ArrayList<Categorie> categories;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<Categorie> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Categorie> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", categories=" + categories +
                '}';
    }
}
