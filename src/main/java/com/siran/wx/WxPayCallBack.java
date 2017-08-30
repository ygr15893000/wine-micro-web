package com.siran.wx;

import com.github.wxpay.logger.WXLoggerService;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.siran.util.PayCommonUtil;
import com.siran.wine.model.TOrder;
import com.siran.wine.service.OrderService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by 唐正川 on 2017/7/7.
 */
@Controller
public class WxPayCallBack {

    protected static final Logger logger = LoggerFactory.getLogger(WxPayCallBack.class);


    @Autowired
    private OrderService orderService;

    @Autowired
    WXLoggerService wxLoggerService;

    /**
     * 微信支付回调页面
     *
     * @param @param request
     * @param @param trade_status
     * @param @param out_trade_no
     * @param @param trade_no
     * @return void
     * @throws
     * @Title: wechatPayNotify
     * @Description: TODO
     */
    @RequestMapping(value = "/wx/wechat_notify", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String payNotifyUrl (HttpServletRequest request, HttpServletResponse response) throws Exception {
        BufferedReader reader = null;

        reader = request.getReader();
        String line = "";
        String xmlString = null;
        StringBuffer inputString = new StringBuffer();

        while ((line = reader.readLine()) != null) {
            inputString.append(line);
        }
        xmlString = inputString.toString();
        request.getReader().close();
        logger.info("----接收到的数据如下：---" + xmlString);
        Map<String, String> map = new HashMap<String, String>();
        String result_code = "";
        String return_code = "";
        String out_trade_no = "";
        map = WXPayUtil.xmlToMap(xmlString);
        logger.info("接受到的数据map" + map);
        out_trade_no = map.get("out_trade_no");
        result_code = map.get("result_code").toString();
        return_code = map.get("return_code").toString();
        final String urlSuffix = "wx/chooseWXPay";
        wxLoggerService.saveWXResponse(urlSuffix,map,xmlString);
        //判断订单号是否在队列中，没在则put进缓存中
     /*   if (WXCache.instance.queue.contains(out_trade_no)) {
            return returnXML(result_code);
        }else {
            WXCache.instance.queue.put(out_trade_no);

        }

        if (WXCache.instance.queue.size() > 990) {
            WXCache.instance.queue.take();
        }*/

        //判断消息是不是微信发的
        //TODO 测试注释
//        if (checkSign(xmlString)) {
            //判断是否支付成功
            if (result_code.equalsIgnoreCase("SUCCESS") && return_code.equalsIgnoreCase("SUCCESS")) {

                boolean flag = orderService.orderNoIfExists(out_trade_no);
                if (flag) {

                    //商品订单号存在
                    //通过订单号获取订单信息
                    TOrder tOrder = orderService.getOrderInformationByOrderNo(out_trade_no);
                    //1	常规酒类
//                    2 打折酒类

                    final BigDecimal total_fee = new BigDecimal(map.get("total_fee")).divide(new BigDecimal("100")).setScale(2);
                    tOrder.setRealAmount(total_fee);


//                    Integer itemConfigAgioId = tOrder.getItemConfigAgioId();
                    final Integer status = tOrder.getStatus();
                    final BigDecimal amount = tOrder.getAmount();
                    final BigDecimal firstPay = tOrder.getFirstPay();
                    final BigDecimal endPay = tOrder.getEndPay();

                    if (status==1){
                        // 预付类型
                        if (firstPay.compareTo(new BigDecimal("0")) == 1) {
                            if (tOrder.getFirstPay().compareTo(total_fee) == 0) {
                                tOrder.setStatus(2);
                            } else {
                                logger.error(String.format("firstPay 不等于 total_fee:%s <> %s", firstPay, total_fee));

//                            return returnXML("FAIL");//非法金额
                            }
                            //充值或一次性付款
                        }else {
                            if (amount.compareTo(total_fee) == 0) {
                                tOrder.setStatus(3);
                            } else {
                                logger.error(String.format("amount 不等于 total_fee:%s <> %s", amount, total_fee));

//                            return returnXML("FAIL");
                            }

                        }

                        //预付类型支付尾款
                    }else if (status == 2) {
                        if (endPay.compareTo(total_fee) == 0){
                            tOrder.setStatus(3);
                        }else {
                            logger.error(String.format("endPay 不等于 total_fee:%s <> %s",endPay,total_fee));
//                            return returnXML("FAIL");
                        }
                    }else {
                        return returnXML(result_code);
                    }
                    result_code = "FAIL";
                    Integer nu = orderService.updateSatusByOrderNo(tOrder);
                    if (nu > 0) {
                        result_code = "SUCCESS";
                    }
                }else {
                    logger.error(String.format("微信回调订单不存在 out_trade_no:%s", out_trade_no));

                }
                return returnXML(result_code);
            } else {
                return returnXML("FAIL");
            }
//        }
//        return "fuck";
    }
        //通过重新签名的方式验证流中包含的信息的正确性。就是判断这个信息到底是不是微信发的

    private boolean checkSign(String xmlString) throws Exception {

        Map<String, String> map = null;

        try {

            map = WXPayUtil.xmlToMap(xmlString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        String signFromAPIResponse = map.get("sign").toString();

        if (signFromAPIResponse == "" || signFromAPIResponse == null) {

            logger.info("API返回的数据签名数据不存在，有可能被第三方篡改!!!");

            return false;

        }
        logger.info("服务器回包里面的签名是:",signFromAPIResponse);

        //清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名

        map.put("sign", "");

        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较

//        String signForAPIResponse = getSign(map);
        String signForAPIResponse= WXPayUtil.generateSignature(map, WXPayConstants.KEY, WXPayConstants.SignType.HMACSHA256);

        if (!signForAPIResponse.equals(signFromAPIResponse)) {

            //签名验不过，表示这个API返回的数据有可能已经被篡改了

            System.out.println("API返回的数据签名验证不通过，有可能被第三方篡改!!! signForAPIResponse生成的签名为" + signForAPIResponse);

            return false;

        }

        System.out.println("恭喜，API返回的数据签名验证通过!!!");

        return true;

    }


    private String returnXML(String return_code) {

        return "<xml><return_code><![CDATA["

                + return_code

                + "]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }

    //sign算法
    public String getSign(Map<String, String> map) throws Exception {
        SortedMap<String, String> signParams = new TreeMap<String, String>();
        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            signParams.put(stringStringEntry.getKey(), stringStringEntry.getValue());
        }
        signParams.remove("sign");
        String sign = PayCommonUtil.createSign("UTF-8", signParams);
        return sign;
    }

        public static void main (String[]args) throws Exception {
            String total_fee =String.valueOf(Integer.valueOf(2850*100));
            String out_trade_no ="20170825160838183845";
            StringBuffer sb = new StringBuffer("<xml><appid><![CDATA[wx1f99c4b7887e73e5]]></appid><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1296910101]]></mch_id><nonce_str><![CDATA[3d1b31cf2dd946e2bfaa69ef550061fd]]></nonce_str><openid><![CDATA[oJ2FzuJp0ASvN4I7ccmC6oLmT4q0]]></openid><out_trade_no><![CDATA["+out_trade_no+"]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[A6150D7266A773BD371948128DBAD03D]]></sign><time_end><![CDATA[20170815145703]]></time_end><total_fee>"+total_fee+"</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[4003522001201708156356995941]]></transaction_id></xml>");
//            StringBuffer sb = new StringBuffer("<xml><appid><![CDATA[wx1f99c4b7887e73e5]]></appid><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1296910101]]></mch_id><nonce_str><![CDATA[3d1b31cf2dd946e2bfaa69ef550061fd]]></nonce_str><openid><![CDATA[oJ2FzuJp0ASvN4I7ccmC6oLmT4q0]]></openid><out_trade_no><![CDATA[20170815145444010216]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[A6150D7266A773BD371948128DBAD03D]]></sign><time_end><![CDATA[20170815145703]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[4003522001201708156356995941]]></transaction_id></xml>");
            String str = sb.toString();
            InputStream instream = new ByteArrayInputStream(str.getBytes("utf-8"));
            System.out.println("发送的字串流：");
            System.out.println(str);
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://localhost:8088//wx/wechat_notify");
            InputStreamEntity inputEntry = new InputStreamEntity(instream);
            httpPost.setEntity(inputEntry);
            CloseableHttpResponse response2 = httpclient.execute(httpPost);
            response2.close();
            httpclient.close();
    }



}