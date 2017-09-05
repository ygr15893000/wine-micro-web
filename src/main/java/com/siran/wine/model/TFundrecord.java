package com.siran.wine.model;

import java.math.BigDecimal;

/**
 * Created by guangrongyang on 2017/8/30.
 */
public class TFundrecord {
    private long id;
    private Integer userId;
    private Integer operateType;
    private String fundMode;
    private BigDecimal handleSum;
    private BigDecimal usableSum;
    private BigDecimal freezeSum;
    private Long trader;
    private String recordTime;
    private String remarks;
    private BigDecimal cost;
    private BigDecimal income;
    private BigDecimal spending;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public String getFundMode() {
        return fundMode;
    }

    public void setFundMode(String fundMode) {
        this.fundMode = fundMode;
    }

    public BigDecimal getHandleSum() {
        return handleSum;
    }

    public void setHandleSum(BigDecimal handleSum) {
        this.handleSum = handleSum;
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

    public Long getTrader() {
        return trader;
    }

    public void setTrader(Long trader) {
        this.trader = trader;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getSpending() {
        return spending;
    }

    public void setSpending(BigDecimal spending) {
        this.spending = spending;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TFundrecord that = (TFundrecord) o;

        if (id != that.id) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (operateType != null ? !operateType.equals(that.operateType) : that.operateType != null) return false;
        if (fundMode != null ? !fundMode.equals(that.fundMode) : that.fundMode != null) return false;
        if (handleSum != null ? !handleSum.equals(that.handleSum) : that.handleSum != null) return false;
        if (usableSum != null ? !usableSum.equals(that.usableSum) : that.usableSum != null) return false;
        if (freezeSum != null ? !freezeSum.equals(that.freezeSum) : that.freezeSum != null) return false;
        if (trader != null ? !trader.equals(that.trader) : that.trader != null) return false;
        if (recordTime != null ? !recordTime.equals(that.recordTime) : that.recordTime != null) return false;
        if (remarks != null ? !remarks.equals(that.remarks) : that.remarks != null) return false;
        if (cost != null ? !cost.equals(that.cost) : that.cost != null) return false;
        if (income != null ? !income.equals(that.income) : that.income != null) return false;
        if (spending != null ? !spending.equals(that.spending) : that.spending != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (operateType != null ? operateType.hashCode() : 0);
        result = 31 * result + (fundMode != null ? fundMode.hashCode() : 0);
        result = 31 * result + (handleSum != null ? handleSum.hashCode() : 0);
        result = 31 * result + (usableSum != null ? usableSum.hashCode() : 0);
        result = 31 * result + (freezeSum != null ? freezeSum.hashCode() : 0);
        result = 31 * result + (trader != null ? trader.hashCode() : 0);
        result = 31 * result + (recordTime != null ? recordTime.hashCode() : 0);
        result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (income != null ? income.hashCode() : 0);
        result = 31 * result + (spending != null ? spending.hashCode() : 0);
        return result;
    }
}
