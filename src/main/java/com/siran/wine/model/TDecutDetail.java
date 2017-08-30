package com.siran.wine.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by 唐正川 on 2017/6/27.
 *
 * 提成计算表
 */
public class TDecutDetail implements Serializable{

    private int id;
    private int commissionUserId;//提成用户id
    private int userId;//用户id
    private int orderId;//对应t_order id
    private String createtime;//计算提成时间
    private BigDecimal amount;//交易总金额
    private BigDecimal decutAmount;//提成金额
    private int programId;//项目ID
    private int status;//该提成是否已发放，0-未发放，1-已发放
    private short type;//1:投资提成，2：认购提成() 3：提成奖励（按月）4提成奖励（按年）
    private String userName;//用户名

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommissionUserId() {
        return commissionUserId;
    }

    public void setCommissionUserId(int commissionUserId) {
        this.commissionUserId = commissionUserId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getDecutAmount() {
        return decutAmount;
    }

    public void setDecutAmount(BigDecimal decutAmount) {
        this.decutAmount = decutAmount;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
