package com.siran.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by guangrongyang on 16/9/30.
 */
public class HttpUtils {
    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);
    public static int doHttp(String url) {
        String result = "";
        int ret =-1;
        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpPost httpPost = null;
        try {
            httpPost = new HttpPost(url);
            try {
                CloseableHttpResponse responses = httpclient.execute(httpPost);
                ret=  responses.getStatusLine().getStatusCode();
                try {
                    HttpEntity entity = responses.getEntity();
                    result = EntityUtils.toString(entity, "UTF-8");
                    log.info("+++++++++++++++result:" + result + "-----------" + responses.getStatusLine().getReasonPhrase() + responses.getStatusLine().getStatusCode());

                    EntityUtils.consume(entity);
                } finally {
                    responses.close();
                }


            } catch (ClientProtocolException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }

        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static void   main(String[] args) {
        String urlPerfix ="http://localhost:8080/huifu/Cash.action?";
        String urlSuffix ="MerPriv={\"rId\":20160517103745517,\"userId\":1448}&OpenAcctId=6226090298767890&OpenBankId=CMB&OrdId=20160517103745518&RealTransAmt=19.00&TransAmt=19.00&UsrCustId=6000060001673238&FeeCustId=6000060001673238";
        try {
            urlSuffix = URLEncoder.encode(urlSuffix,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int s = HttpUtils.doHttp(urlPerfix+urlSuffix);
        System.out.println(s);
    }
}
