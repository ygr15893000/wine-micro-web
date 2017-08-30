package com.siran.wine.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by 唐正川 on 2017/6/14.
 * 商品表
 */
public class TItem implements Serializable {

    private int id;//商品id，同时也是商品编号
    private String title;//商品标题
    private String sellPoint;//商品卖点
    private BigDecimal price;//商品价格，单位为：分
    private int num;//库存数量
    private String image;//商品图片
    private String microImage;//商品图片(订单列表缩微图)
    private byte status;//商品状态，-1 待审核  1-正常，2-下架，3-删除
    private Date created;//创建时间
    private Date updated;//更新时间
    private int level;//商品等级 1：一级图 2:二级图
    private int type;//t_item_config.id
    private String specification;//规格
    private int remark;//折扣
    private boolean prepayType;//0：常规类型商品 ，1预付类型商品


    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public int getRemark() {
        return remark;
    }

    public void setRemark(int remark) {
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMicroImage() {
        return microImage;
    }

    public void setMicroImage(String microImage) {
        this.microImage = microImage;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isPrepayType() {
        return prepayType;
    }

    public void setPrepayType(boolean prepayType) {
        this.prepayType = prepayType;
    }
}
