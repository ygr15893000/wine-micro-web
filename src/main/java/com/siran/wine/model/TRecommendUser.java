package com.siran.wine.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by 唐正川 on 2017/6/26.
 */
public class TRecommendUser implements Serializable{

    private int id;
    private int userId;
    private int recommendUserId;
    private String beginDate;
    private String endDate;
    private String createTime;
    //来源：0-后台添加，1-用户注册输入推荐人添加，2-微信好友邀请，3-微博邀请，4-qq空间邀请，5-微信朋友圈邀请，6-qq好友，7-短信，8-二维码
    private short source;
    private String userName;//用户名

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRecommendUserId() {
        return recommendUserId;
    }

    public void setRecommendUserId(int recommendUserId) {
        this.recommendUserId = recommendUserId;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public short getSource() {
        return source;
    }

    public void setSource(short source) {
        this.source = source;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    }
