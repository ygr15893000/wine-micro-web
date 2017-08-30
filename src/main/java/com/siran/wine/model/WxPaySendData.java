package com.siran.wine.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by 唐正川 on 2017/7/5.
 */
public class WxPaySendData {
    //公众号ID
    private String appid;
    //附加参数
    private String attach;
    //商品名称
    private String body;
    //商户ID
    private String mch_id;
    //随机支付串
    private String nonce_str;
    //通知地址,不能携带参数,直接就能访问
    private String notify_url;
    //用户订单号
    private String out_trade_no;
    //总金额 以分为单位
    private int total_fee;
    //交易类型
    private String trade_type;
    //终端IP
    private String spbill_create_ip;
    //openID
    private String openid;
    //签名
    private String sign;
    //设备号
    private String device_info;
    //url
    private String url;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getAppid() {
        return appid;
    }
    public void setAppid(String appid) {
        this.appid = appid;
    }
    public String getAttach() {
        return attach;
    }
    public void setAttach(String attach) {
        this.attach = attach;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getMch_id() {
        return mch_id;
    }
    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }
    public String getNonce_str() {
        return nonce_str;
    }
    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }
    public String getNotify_url() {
        return notify_url;
    }
    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }
    public String getOut_trade_no() {
        return out_trade_no;
    }
    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }
    public int getTotal_fee() {
        return total_fee;
    }
    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }
    public String getTrade_type() {
        return trade_type;
    }
    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }
    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }
    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }
    public String getOpenid() {
        return openid;
    }
    public void setOpenid(String openid) {
        this.openid = openid;
    }
    public String getSign() {
        return sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }


    @Override
    public String toString() {
        return "WxPaySendData{" +
                "appid='" + appid + '\'' +
                ", attach='" + attach + '\'' +
                ", body='" + body + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", nonce_str='" + nonce_str + '\'' +
                ", notify_url='" + notify_url + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                ", total_fee=" + total_fee +
                ", trade_type='" + trade_type + '\'' +
                ", spbill_create_ip='" + spbill_create_ip + '\'' +
                ", openid='" + openid + '\'' +
                ", sign='" + sign + '\'' +
                ", device_info='" + device_info + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
