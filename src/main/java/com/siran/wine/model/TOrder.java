package com.siran.wine.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by 唐正川 on 2017/6/14.
 *
 * 订单表
 */
public class TOrder implements Serializable {

    private int id  =0;
    private Integer type;//类型，1：商品购买支付 2：充值
    private String firstOrderNO;//首付订单号
    private String orderNO;//订单号
    private int itemId =0;//商品id
    private short num;//数量
    private BigDecimal amount;//总金额
    private BigDecimal realAmount;//需实付总金额
    private BigDecimal firstPay = new BigDecimal("0");//首付款
    private BigDecimal endPay = new BigDecimal("0");//需付尾款
    private Integer status;//状态：1、未付款，2、待付尾款 3、已付款，4、待发货，5、已发货，6、交易成功，7、交易关闭
    private String createTime;//订单创建时间
    private String updateTime;//订单更新时间
    private String firstPaymentTime;//首付付款时间
    private String paymenTime;//付款时间
    private String consignTime;//发货时间
    private String endTime;//交易完成时间
    private String closeTime;//交易关闭时间
    private String shippingName;//物流名称
    private String shippingCode;//物流单号
    private String shippingNo;//物流单号
    private int userId;//用户id
    private int itemConfigAgioId =0;//_item_config_agio.id
    private int ifCommission;//提成是否发放  0 ‘没有’ 1'已发放'
    private String receiverName;//收货人姓名
    private String receiverPhone;//固定电话
    private String receiverMobile;//移动电话
    private String receiverState;//省份
    private String receiverCity;//城市
    private String receiverRegion;//区
    private String receiverAddress;//收货地址，如：xx路xx号
    private String receiverZip;//邮政编码,如：310001
    private String title;//商品标题
    private String microImage;//商品图片(订单列表缩微图)
    private String prepayId; //微信支付预支付id
    private BigDecimal price;//商品价格
    private String specification;//规格
    private String outRefundNo;//退款订单号
    private String outRefundTime;//退款完成时间
    private String refundTime;//退款申请时间

    private String applyReturnsTime;
    private String returnsTime;
    private String sellerReceivedTime;
    private Integer resultStatus;
    private Integer couponId =0; // 代金券id


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getReceiverRegion() {
        return receiverRegion;
    }

    public void setReceiverRegion(String receiverRegion) {
        this.receiverRegion = receiverRegion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMicroImage() {
        return microImage;
    }

    public void setMicroImage(String microImage) {
        this.microImage = microImage;
    }

    public int getIfCommission() {
        return ifCommission;
    }

    public void setIfCommission(int ifCommission) {
        this.ifCommission = ifCommission;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverState() {
        return receiverState;
    }

    public void setReceiverState(String receiverState) {
        this.receiverState = receiverState;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverZip() {
        return receiverZip;
    }

    public void setReceiverZip(String receiverZip) {
        this.receiverZip = receiverZip;
    }

    public int getItemConfigAgioId() {
        return itemConfigAgioId;
    }

    public void setItemConfigAgioId(int itemConfigAgioId) {
        this.itemConfigAgioId = itemConfigAgioId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNO() {
        return orderNO;
    }

    public void setOrderNO(String orderNO) {
        this.orderNO = orderNO;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public short getNum() {
        return num;
    }

    public void setNum(short num) {
        this.num = num;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount = realAmount;
    }

    public BigDecimal getFirstPay() {
        return firstPay;
    }

    public void setFirstPay(BigDecimal firstPay) {
        this.firstPay = firstPay;
    }

    public BigDecimal getEndPay() {
        return endPay;
    }

    public void setEndPay(BigDecimal endPay) {
        this.endPay = endPay;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getFirstPaymentTime() {
        return firstPaymentTime;
    }

    public void setFirstPaymentTime(String firstPaymentTime) {
        this.firstPaymentTime = firstPaymentTime;
    }

    public String getPaymenTime() {
        return paymenTime;
    }

    public void setPaymenTime(String paymenTime) {
        this.paymenTime = paymenTime;
    }

    public String getConsignTime() {
        return consignTime;
    }

    public void setConsignTime(String consignTime) {
        this.consignTime = consignTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getShippingNo() {
        return shippingNo;
    }

    public void setShippingNo(String shippingNo) {
        this.shippingNo = shippingNo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }


    public String getOutRefundTime() {
        return outRefundTime;
    }

    public void setOutRefundTime(String outRefundTime) {
        this.outRefundTime = outRefundTime;
    }

    public String getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(String refundTime) {
        this.refundTime = refundTime;
    }

    public Integer getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(Integer resultStatus) {
        this.resultStatus = resultStatus;
    }


    public String getFirstOrderNO() {
        return firstOrderNO;
    }

    public void setFirstOrderNO(String firstOrderNO) {
        this.firstOrderNO = firstOrderNO;
    }

    public String getApplyReturnsTime() {
        return applyReturnsTime;
    }

    public void setApplyReturnsTime(String applyReturnsTime) {
        this.applyReturnsTime = applyReturnsTime;
    }

    public String getReturnsTime() {
        return returnsTime;
    }

    public void setReturnsTime(String returnsTime) {
        this.returnsTime = returnsTime;
    }

    public String getSellerReceivedTime() {
        return sellerReceivedTime;
    }

    public void setSellerReceivedTime(String sellerReceivedTime) {
        this.sellerReceivedTime = sellerReceivedTime;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    @Override
    public String toString() {
        return "TOrder{" +
                "id=" + id +
                ", type=" + type +
                ", orderNO='" + orderNO + '\'' +
                ", itemId=" + itemId +
                ", num=" + num +
                ", amount=" + amount +
                ", realAmount=" + realAmount +
                ", firstPay=" + firstPay +
                ", endPay=" + endPay +
                ", status=" + status +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", firstPaymentTime='" + firstPaymentTime + '\'' +
                ", paymenTime='" + paymenTime + '\'' +
                ", consignTime='" + consignTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", closeTime='" + closeTime + '\'' +
                ", shippingName='" + shippingName + '\'' +
                ", shippingCode='" + shippingCode + '\'' +
                ", shippingNo='" + shippingNo + '\'' +
                ", userId=" + userId +
                ", itemConfigAgioId=" + itemConfigAgioId +
                ", ifCommission=" + ifCommission +
                ", receiverName='" + receiverName + '\'' +
                ", receiverPhone='" + receiverPhone + '\'' +
                ", receiverMobile='" + receiverMobile + '\'' +
                ", receiverState='" + receiverState + '\'' +
                ", receiverCity='" + receiverCity + '\'' +
                ", receiverRegion='" + receiverRegion + '\'' +
                ", receiverAddress='" + receiverAddress + '\'' +
                ", receiverZip='" + receiverZip + '\'' +
                ", title='" + title + '\'' +
                ", microImage='" + microImage + '\'' +
                ", prepayId='" + prepayId + '\'' +
                ", price=" + price +
                ", specification='" + specification + '\'' +
                ", outRefundNo='" + outRefundNo + '\'' +
                ", outRefundTime='" + outRefundTime + '\'' +
                ", refundTime='" + refundTime + '\'' +
                ", resultStatus=" + resultStatus +
                '}';
    }
}
