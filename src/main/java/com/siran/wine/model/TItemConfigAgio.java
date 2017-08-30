package com.siran.wine.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by 唐正川 on 2017/6/14.
 *
 * 商品表梯度折扣配置表
 */
public class TItemConfigAgio implements Serializable {

    private int id ;
    private int cId;//t_item_config.id
    private BigDecimal firstPay;//首付 对应 type=1
    private BigDecimal totalAmount;//购买满额 对应 type=2
    private BigDecimal discount;//折扣 80% 按百分比
    private byte status;//1:启用 2:禁用
    private byte orderBy;//排序
    private byte type;//1:预定自选首付折扣 2:购买满额折扣
    private String name;//商品类别
    private String remark;//


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public BigDecimal getFirstPay() {
        return firstPay;
    }

    public void setFirstPay(BigDecimal firstPay) {
        this.firstPay = firstPay;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public byte getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(byte orderBy) {
        this.orderBy = orderBy;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
