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

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setProgramId(Integer programId) {
        this.programId = programId;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setType(Short type) {
        this.type = type;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TDecutDetail that = (TDecutDetail) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (orderId != that.orderId) return false;
        if (programId != that.programId) return false;
        if (status != that.status) return false;
        if (type != that.type) return false;
        if (createtime != null ? !createtime.equals(that.createtime) : that.createtime != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (decutAmount != null ? !decutAmount.equals(that.decutAmount) : that.decutAmount != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + orderId;
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (decutAmount != null ? decutAmount.hashCode() : 0);
        result = 31 * result + programId;
        result = 31 * result + status;
        result = 31 * result + (int) type;
        return result;
    }
}
