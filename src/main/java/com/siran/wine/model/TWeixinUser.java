package com.siran.wine.model;


import java.util.Date;

/**
 * Created by 唐正川 on 2017/7/28.
 */
public class TWeixinUser {

    private int id;
    private String openid;
    private int userId;
    private Date authtime;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getAuthtime() {
        return authtime;
    }

    public void setAuthtime(Date authtime) {
        this.authtime = authtime;
    }
}
