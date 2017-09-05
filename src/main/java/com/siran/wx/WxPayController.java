package com.siran.wx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wxpay.logger.WXLoggerService;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.siran.common.constant.ConstantDateFormat;
import com.siran.core.config.EnvironmentConfig;
import com.siran.util.HttpRequestor;
import com.siran.util.TimeUtil;
import com.siran.wine.dao.impl.CouponDao;
import com.siran.wine.dao.impl.ItemConfigAgioDao;
import com.siran.wine.dao.impl.OrderDao;
import com.siran.wine.dao.impl.WeixinUserDao;
import com.siran.wine.model.*;
import com.siran.wine.service.OrderService;
import com.siran.wine.service.WeixinService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;


/**
 * Created by 唐正川 on 2017/7/6.
 */
@Controller
public class WxPayController {


    @Autowired
    private EnvironmentConfig environmentConfig;

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private WeixinService weixinService;
    @Autowired
    private WXLoggerService wxLoggerService;

    @Autowired
    private WXPayConfigImpl config;

    @Autowired
    private WXPay wxpay;


    @Autowired
    private WeixinUserDao weixinUserDao;
    @Autowired
    private CouponDao couponDao;

    @Autowired
    private ItemConfigAgioDao itemConfigAgioDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(WxPayController.class);


    public WxPayController() throws Exception {
       /* config = WXPayConfigImpl.getInstance();
        wxpay = new WXPay(config, wxLoggerService);*/

    }

    /**
     * 支付  下单
     */
    @RequestMapping(value = "/wx/wxPay")
    @Transactional
    @ResponseBody
    public Map<String, String> unifiedOrder(WxPaySendData wxPaySendData, TOrder order) throws Exception {
        LOGGER.info("  WxPaySendData对象：  " + wxPaySendData.toString());
        LOGGER.info("  Torder对象：  " + order.toString());
        final Integer couponId = order.getCouponId();
        Map<String, String> map = new HashMap<>();
        String out_trade_no;
        int id = order.getId();
        //根据订单id获取订单号
        TOrder tOrder;
        //待支付订单
        if (id > 0) {
            tOrder = orderService.getOrderInformationById(id);
            final Integer type = tOrder.getType();
            final Integer status = tOrder.getStatus();
            if (type == 2){
                LOGGER.warn("充值不作再次支付处理");
                return null;
            }
            map.put("body", tOrder.getTitle());
            // 以分为单位的整数
            //TODO 测试暂时注释
//            map.put("total_fee", String.valueOf(tOrder.getAmount().multiply(new BigDecimal("100")).setScale(BigDecimal.ROUND_UP,0)));
            if (status == 1){
                if (tOrder.getFirstPay().compareTo(new BigDecimal("0")) == 1) {
                    map.put("total_fee", String.valueOf(tOrder.getFirstPay().multiply(new BigDecimal("100")).setScale(0)));
                }else {
                    map.put("total_fee", String.valueOf(tOrder.getAmount().multiply(new BigDecimal("100")).setScale(0)));
                }

            }else if (status == 2) {
                map.put("total_fee", String.valueOf(tOrder.getEndPay().multiply(new BigDecimal("100")).setScale(0)));
            }
            out_trade_no = TimeUtil.getTimeStringMillisecond();


        } else {

            //使用代金券
            TCoupon coupon =null;
            if (couponId > 0) {
                coupon = couponDao.getCouponById(couponId);
                final byte status = coupon.getStatus();
                if (status != 1) {
                    LOGGER.error("代金券状态非法：" + coupon);
                    return null;
                }
                final String endDate = coupon.getEndDate();
                final String nowDate = ConstantDateFormat.SF_Y_M_D.format(new Date());
                if (endDate.compareTo(nowDate) < 0) {
                    LOGGER.error("代金券过期：" + coupon);
                    return null;
                }
                Integer itemConfigAgioId = order.getItemConfigAgioId();
                TItemConfigAgio tItemConfigAgio = itemConfigAgioDao.getTItemConfigAgioById(itemConfigAgioId);
                if (tItemConfigAgio.getDiscount().compareTo(new BigDecimal("100")) < 0) {
                    LOGGER.error("代金券 和 折扣不能同时使用：" + coupon);
                    return null;
                }
            }
            //支付商品 type=1 或 充值type=2
            //添加订单返回主键
            id = orderService.addOrder(order).getId();

            //使用代金券
            if (couponId > 0) {
                //代金券金额
                final BigDecimal price = coupon.getPrice();
                // update t_order set amount =amount -price
                orderDao.updateTorderAmount(price,id);

                // t_coupon  set status=2  ordId=?
                coupon.setOrdId(id);
                coupon.setId(couponId);
                orderDao.updateTcouponStatus(coupon);

                //  insert t_coupon_his
                TCouponHis tCouponHis = new TCouponHis();
                tCouponHis.setUserId(Long.valueOf(order.getUserId()));
                tCouponHis.setCouponId(Long.valueOf(couponId));
                tCouponHis.setAmount(price);
                tCouponHis.setType(2);
                orderDao.insertTcouponHisList(tCouponHis);

            }

            tOrder = orderService.getOrderInformationById(id);
            map.put("body", wxPaySendData.getBody());
//            map.put("total_fee", String.valueOf(wxPaySendData.getTotal_fee()));
            final BigDecimal amount = tOrder.getAmount();
            map.put("total_fee", String.valueOf(amount.multiply(new BigDecimal("100")).setScale(0)));
           if (tOrder.getStatus() == 2) {
                if (tOrder.getFirstPay().compareTo(new BigDecimal("0")) == 1) {
                    map.put("total_fee", String.valueOf(tOrder.getEndPay().multiply(new BigDecimal("100")).setScale(0)));
                }
            }

            out_trade_no = tOrder.getOrderNO();
        }
        tOrder.setOrderNO(out_trade_no);


        LOGGER.info("Torder对象 = " + tOrder.toString());
        Integer userId = tOrder.getUserId();
        //根据userId获取openid
        TWeixinUser tWeixinUser = weixinService.getOpenidByUserId(userId);
        String openid = tWeixinUser.getOpenid();
        LOGGER.info("openid = " + openid);


        map.put("out_trade_no", out_trade_no);
        map.put("fee_type", WXPayConstants.FEE_TYPE);

        //TODO 上线时需要修改W
        map.put("spbill_create_ip", WXPayConstants.SPBILL_CREATE_IP);
        map.put("nonce_str", WXPayUtil.generateNonceStr());
        map.put("notify_url", WXPayConstants.NOTIFY_URL);
        map.put("trade_type", WXPayConstants.TRADE_TYPE);
        map.put("openid", openid);
        Map<String, String> data = null;
        String prepayId = null;
        try {
            data = wxpay.unifiedOrder(map);
            LOGGER.info("统一下单返回的数据" + data);
            prepayId = data.get("prepay_id");
            tOrder.setPrepayId(prepayId);
            Integer nu = null;
            if (tOrder.getStatus() == 1) {
                if (tOrder.getFirstPay().compareTo(new BigDecimal("0")) == 1){
                    nu = orderDao.updatePrepayIdFirst(tOrder);
                }else {
                    nu = orderDao.updatePrepayId(tOrder);

                }
            }else if (tOrder.getStatus() == 2) {
                nu = orderDao.updatePrepayId(tOrder);

            }

            if (nu > 0) {
                LOGGER.info("添加微信预支付id成功");
            } else {
                LOGGER.info("添加微信预支付id失败");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return WxH5Pay(prepayId, wxPaySendData.getUrl());
    }


    public SortedMap<String, String> WxH5Pay(String prepayId, String url) throws Exception {
        LOGGER.info(" ********** 支付页面的url ********** = " + url);
        //获取需要的参数
        String appId = environmentConfig.getProperty("wx.appid");
        String nonceStr = WXPayUtil.generateNonceStr();
        String timeStamp = Long.toString(WXPayUtil.getCurrentTimestamp());


        SortedMap<String, String> sortedMap = new TreeMap<String, String>();
        sortedMap.put("appId", appId);
        sortedMap.put("nonceStr", nonceStr);
        sortedMap.put("package", "prepay_id=" + prepayId);
        sortedMap.put("signType", WXPayConstants.HMACSHA256);
//        sortedMap.put("signType", WXPayConstants.MD5);
        sortedMap.put("timeStamp", timeStamp);

        //生成paysign
        String paySign = WXPayUtil.generateSignature(sortedMap, WXPayConstants.KEY, WXPayConstants.SignType.HMACSHA256);
//        String paySign = PayCommonUtil.createSign("UTF-8", sortedMap);
        sortedMap.put("paySign", paySign);

        //从缓存中获取jsapi_ticket
        final CacheBean cacheBean = WXCache.instance.cache.get("key");
        String jsapi_ticket = cacheBean.getTicket();
        LOGGER.info("jsapi_ticket : " + jsapi_ticket);
        //注意这里参数名必须全部小写，且必须有序
        String string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonceStr +
                "&timestamp=" + timeStamp +
                "&url=" + url;

        //获得signature

        LOGGER.info("signature : " + string1);

        String signature = DigestUtils.sha1Hex(string1);

        sortedMap.put("signature", signature);
        LOGGER.info("H5调用支付所需的参数:" + sortedMap);
        return sortedMap;
    }


    @RequestMapping(value = "/wx/getOpenId")
    @ResponseBody
    public TWeixinUser getOpenId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            /*String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf9d0b2eb3175a4a7&redirect_uri=http://192.168.1.109:8080/wx/getOpenId&response_type=code&scope=snsapi_userinfo#wechat_redirect";*/

            //从请求中获取code
            String code = request.getParameter("code");

            //获取appid,appsecret
            String appid = environmentConfig.getProperty("wx.appid");
            String secret = environmentConfig.getProperty("wx.appsecret");

            //获得access_token和openid的请求接口地址
            String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid
                    + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";

            String oppid = new HttpRequestor().doGet(requestUrl);
            LOGGER.error("sns/oauth2/access_token:"+ oppid);
            //使用jackjson解析
            ObjectMapper mapper = new ObjectMapper();
            // 获取openid
            String openid = mapper.readTree(oppid).get("openid").toString();
            openid = openid.replace("\"", "");
            LOGGER.info(requestUrl + " ************* 生成openid:  *************** " + openid);

            TWeixinUser weixinUser = new TWeixinUser();
            //查询openId是否存在
            Integer num = weixinUserDao.openidIfExists(openid);

            //如果openid存在，返回openid，userId,username参数
            if (num != 0) {
                weixinUser = weixinUserDao.getUserIdByOpenId(openid);
            } else {
                weixinUser.setOpenid(openid);
            }
            response.setContentType("text/html;charset=utf-8");
            return weixinUser;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 正常情况下，微信会返回下述JSON数据包给公众号：
     * {
     * "subscribe": 1,
     * "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M",
     * "nickname": "Band",
     * "sex": 1,
     * "language": "zh_CN",
     * "city": "广州",
     * "province": "广东",
     * "country": "中国",
     * "headimgurl":  "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4
     * eMsv84eavHiaiceqxibJxCfHe/0",
     * "subscribe_time": 1382694957,
     * "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
     * "remark": "",
     * "groupid": 0,
     * "tagid_list":[128,2]
     * }
     * 参数说明
     * 参数	说明
     * subscribe	用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
     * openid	用户的标识，对当前公众号唯一
     * nickname	用户的昵称
     * sex	用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     * city	用户所在城市
     * country	用户所在国家
     * province	用户所在省份
     * language	用户的语言，简体中文为zh_CN
     * headimgurl	用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
     * subscribe_time	用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
     * unionid	只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
     * remark	公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
     * groupid	用户所在的分组ID（兼容旧的用户分组接口）
     * tagid_list	用户被打上的标签ID列表
     */
    @RequestMapping(value = "/wx/user/info")
    @ResponseBody
    public void userInfo(String openid,HttpServletResponse response) throws Exception {
        final String accessToken = WXCache.instance.cache.get("key").getAccessToken();

        LOGGER.info("accessToken:"+ accessToken);
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken + "&openid=" + openid + "&lang=zh_CN";
        String result = null;
        try {

            result = new HttpRequestor().doGet(requestUrl);
            response.setCharacterEncoding("UTF-8"); //设置编码格式
            response.setContentType("text/html");   //设置数据格式
            PrintWriter out = response.getWriter(); //获取写入对象
            out.print(result); //将json数据写入流中
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }

    }

    /**
     * 退款
     *
     * @param order
     * @return
     */
    public Map<String, String> payRefund(TOrder order) {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", order.getOrderNO());
        data.put("out_refund_no",order.getOutRefundNo());
        data.put("total_fee", String.valueOf(order.getAmount().multiply(new BigDecimal("100")).setScale(0, BigDecimal.ROUND_HALF_UP)));
        data.put("refund_fee", String.valueOf(order.getAmount().multiply(new BigDecimal("100")).setScale(0, BigDecimal.ROUND_HALF_UP)));
        data.put("refund_fee_type", "CNY");
        data.put("op_user_id", config.getMchID());
        data.put("refund_account", "REFUND_SOURCE_RECHARGE_FUNDS");
        Map<String, String> result =new HashMap<>();
        try {
            result = wxpay.refund(data);
            LOGGER.info(result.toString());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 企业付款（用户取现）
     *
     * @param order
     * @param user
     * @return
     */
    public Map<String, String> transfers(final TOrder order, final TUser user) {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("partner_trade_no", order.getOrderNO());
        data.put("openid", user.getOpenid());
        data.put("check_name", "NO_CHECK");// 固定 NO_CHECK：不校验真实姓名 FORCE_CHECK：强校验真实姓名
       // data.put("re_user_name", "");// 如果check_name设置为FORCE_CHECK，则必填用户真实姓名
        data.put("amount", String.valueOf(order.getAmount().multiply(new BigDecimal("100")).setScale(0, BigDecimal.ROUND_HALF_UP)));
        data.put("desc", "企业付款到微信钱包");
        data.put("spbill_create_ip", WXPayConstants.SPBILL_CREATE_IP);
//        data.put("op_user_id", config.getMchID());
        Map<String, String> result = new HashMap<>();
        try {
            result = wxpay.transfers(data);
            LOGGER.info(result.toString());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    public static void main(String[] args) {
        String num = "0.01";
        String nums = String.valueOf(new BigDecimal(num).multiply(new BigDecimal("100")).setScale(0, BigDecimal.ROUND_HALF_UP));
        System.out.print(nums);
    }
}
