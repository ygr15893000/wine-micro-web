package com.github.wxpay.logger;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guangrongyang on 2017/8/18.
 */
@Service
public class WXLoggerService {

    @Autowired
    WXLoggerDao wxLoggerDao;

    /**
     * 保存到 TWxRequest 表
     *
     *
     * @param urlSuffix
     * @param var
     * @return
     */
    public int saveWXRequest(String urlSuffix, Map var, String xmlDataString) {
        TWxRequest tWxRequest = new TWxRequest();
        String outTradeNo = (String) var.get("out_trade_no");
        if(StringUtils.isBlank(outTradeNo)){
            outTradeNo = (String) var.get("partner_trade_no");
        }
        tWxRequest.setOutTradeNo(outTradeNo);
        tWxRequest.setUrlSuffix(urlSuffix);
        tWxRequest.setData(xmlDataString);
        tWxRequest.setAppid((String) var.get("appid"));
        tWxRequest.setMchId((String) var.get("mch_id"));
        tWxRequest.setOpenid((String) var.get("openid"));
        tWxRequest.setTradeType((String) var.get("trade_type"));
        tWxRequest.setSignType((String) var.get("sign_type"));
        tWxRequest.setTransactionId((String) var.get("transaction_id"));
        tWxRequest.setTimeEnd((String) var.get("time_end"));
        return wxLoggerDao.saveWXRequest(tWxRequest);

    }

    /**
     * 保存到 TWxResponse 表
     *
     *
     * @param urlSuffix
     * @param var
     * @return
     */
    public int saveWXResponse(String urlSuffix, Map var, String xmlDataString) {

        TWxResponse tWxResponse = new TWxResponse();
        String outTradeNo = (String) var.get("out_trade_no");
        if(StringUtils.isBlank(outTradeNo)){
            outTradeNo = (String) var.get("partner_trade_no");
        }
        tWxResponse.setOutTradeNo(outTradeNo);
        tWxResponse.setUrlSuffix(urlSuffix);
        tWxResponse.setData(xmlDataString);
        tWxResponse.setAppid((String) var.get("appid"));
        tWxResponse.setMchId((String) var.get("mch_id"));
        tWxResponse.setResultCode((String) var.get("result_code"));
        tWxResponse.setReturnCode((String) var.get("return_code"));
        tWxResponse.setErrCode((String) var.get("err_code"));
        tWxResponse.setErrCodeDes((String) var.get("err_code_des"));
        tWxResponse.setOpenid((String) var.get("openid"));
        tWxResponse.setTotalFee((String) var.get("total_fee"));
        tWxResponse.setTradeType((String) var.get("trade_type"));
        tWxResponse.setSignType((String) var.get("sign_type"));
        tWxResponse.setBankType((String) var.get("bank_type"));
        tWxResponse.setTransactionId((String) var.get("transaction_id"));
        tWxResponse.setTimeEnd((String) var.get("time_end"));
        return wxLoggerDao.saveWXResponse(tWxResponse);
    }

}
