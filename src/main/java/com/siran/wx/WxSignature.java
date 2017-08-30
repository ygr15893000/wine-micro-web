package com.siran.wx;

import com.github.wxpay.sdk.WXPayUtil;
import com.siran.core.config.EnvironmentConfig;
import com.siran.wine.model.WxPaySendData;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 唐正川 on 2017/8/3.
 * 获取微信signature
 */
@Controller
public class WxSignature {

    @Autowired
    private EnvironmentConfig environmentConfig;

    private static final Logger LOGGER = LoggerFactory.getLogger(WXCache.class);

    @RequestMapping(value= "/wx/getSignature")
    @ResponseBody
    public Map getSignature( String url ){
           //获得随机字符串
        String nonce_str = WXPayUtil.generateNonceStr();
        //获得时间戳
        String timestamp = Long.toString(WXPayUtil.getCurrentTimestamp());


        try {

        final CacheBean cacheBean = WXCache.instance.cache.get("key");
        String jsapi_ticket = cacheBean.getTicket();
        LOGGER.info(cacheBean.getAccessToken());
        LOGGER.info(cacheBean.getTicket());
        //注意这里参数名必须全部小写，且必须有序
        String string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;

        //算出signature的值
        String signature = DigestUtils.sha1Hex(string1);
        Map map = new HashMap();
        map.put("appid",environmentConfig.getProperty("wx.appid"));
        map.put("noncestr",nonce_str);
        map.put("timestamp",timestamp);
        map.put("signature",signature);
        LOGGER.info(" ********** 生成的signature:  ************" + map);
        return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
