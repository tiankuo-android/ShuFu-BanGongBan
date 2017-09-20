package com.wfkj.android.smartoffice.model.sever_model;

/**
 * 用户名下的房屋
 * Created by wangdongyang on 17/1/4.
 */
public class UserHouse {

    private int user_id;
    private String username;
    private int house_id;
    private String name;
    private int status;
    private String role;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getHouse_id() {
        return house_id;
    }

    public void setHouse_id(int house_id) {
        this.house_id = house_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserHouse{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", house_id=" + house_id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", role='" + role + '\'' +
                '}';
    }
}
