package com.siran.wine.model;

import java.math.BigDecimal;

/**
 * Created by 唐正川 on 2017/8/10.
 */
public class TCoupon {

    private Integer id =0;
    private Integer userId;
    private BigDecimal price;
    private byte status;
    private String created;
    private String beginDate;
    private String endDate;
    private String partnerTradeNo;
    private String updated;
    private Integer ordId ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
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

    public String getPartnerTradeNo() {
        return partnerTradeNo;
    }

    public void setPartnerTradeNo(String partnerTradeNo) {
        this.partnerTradeNo = partnerTradeNo;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public Integer getOrdId() {
        return ordId;
    }

    public void setOrdId(Integer ordId) {
        this.ordId = ordId;
    }

    @Override
    public String toString() {
        return "TCoupon{" +
                "id=" + id +
                ", userId=" + userId +
                ", price=" + price +
                ", status=" + status +
                ", created='" + created + '\'' +
                ", beginDate='" + beginDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", partnerTradeNo='" + partnerTradeNo + '\'' +
                ", updated='" + updated + '\'' +
                ", ordId=" + ordId +
                '}';
    }
}
