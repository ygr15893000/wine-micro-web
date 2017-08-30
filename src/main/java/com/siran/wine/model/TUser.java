package com.siran.wine.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by 唐正川 on 2017/6/14.
 *
 * 用户表
 */
public class TUser implements Serializable {

    private int id;
    private String username;      //用户移动电话
    private String password;     //用户密码
    private String refferee;  //推荐人
    private String lastIP; //最后登录IP
    private Timestamp lastDate; //最后登录时间
    private Date createTime; //帐号创建时间
    private BigDecimal usableSum;//可用金额
    private BigDecimal freezeSum;//冻结金额
    private String headImg;//头像
    private long loginCount;//登录次数
    private Date lockTime;//锁定时间
    private int loginErrorCount;//错误登录次数，默认0
    private int isLoginLimit;//1.不限制登录 2.限制登录
    private short status;//状态，0-有效，-1无效
    private String realName;//真实姓名
    private int  sex;//性别 1，男      2，女
    private Date birthday;//出生日期
    private String address;//居住地址
    private String idNo;//身份证号码
    private String acount;//微信账号
    private int level;//等级
    private int con;//
    private int recommendUserCount;
    private int couponCount;
    private int orderCount;
    private String openid;

    public int getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(int couponCount) {
        this.couponCount = couponCount;
    }



    public int getCon() {
        return con;
    }

    public void setCon(int con) {
        this.con = con;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getAcount() {
        return acount;
    }

    public void setAcount(String acount) {
        this.acount = acount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRefferee() {
        return refferee;
    }

    public void setRefferee(String refferee) {
        this.refferee = refferee;
    }

    public String getLastIP() {
        return lastIP;
    }

    public void setLastIP(String lastIP) {
        this.lastIP = lastIP;
    }

    public Timestamp getLastDate() {
        return lastDate;
    }

    public void setLastDate(Timestamp lastDate) {
        this.lastDate = lastDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getUsableSum() {
        return usableSum;
    }

    public void setUsableSum(BigDecimal usableSum) {
        this.usableSum = usableSum;
    }

    public BigDecimal getFreezeSum() {
        return freezeSum;
    }

    public void setFreezeSum(BigDecimal freezeSum) {
        this.freezeSum = freezeSum;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public long getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(long loginCount) {
        this.loginCount = loginCount;
    }

    public Date getLockTime() {
        return lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    public int getLoginErrorCount() {
        return loginErrorCount;
    }

    public void setLoginErrorCount(int loginErrorCount) {
        this.loginErrorCount = loginErrorCount;
    }

    public int getIsLoginLimit() {
        return isLoginLimit;
    }

    public void setIsLoginLimit(int isLoginLimit) {
        this.isLoginLimit = isLoginLimit;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public int getRecommendUserCount() {
        return recommendUserCount;
    }

    public void setRecommendUserCount(int recommendUserCount) {
        this.recommendUserCount = recommendUserCount;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
