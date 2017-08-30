package com.siran.wx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.siran.common.constant.DefineConstant;
import com.siran.util.HttpRequestor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 中央缓存,缓存微信的 access_token 和 ticket
 * Created by guangrongyang on 2017/8/2.
 */
public enum WXCache {
    instance;
    private static final Logger LOGGER = LoggerFactory.getLogger(WXCache.class);

    protected LoadingCache<String, CacheBean> cache = CacheBuilder.newBuilder().maximumSize(100)
            .expireAfterWrite(7200, TimeUnit.SECONDS)
            .refreshAfterWrite(7200, TimeUnit.SECONDS)
            .build(new CacheLoader<String, CacheBean>() {
                       @Override
                       public CacheBean load(String key) throws InterruptedException {
                           return createValue(key);
                       }


                       @Override
                       public ListenableFuture<CacheBean> reload(String key, CacheBean oldValue)
                               throws Exception {
                           return Futures.immediateFuture(createValue(key));
                       }
                   }
            );

    //创建value
    private static CacheBean createValue(String key) throws InterruptedException {
        CacheBean cacheBean = new CacheBean();
        final HttpRequestor httpRequestor = new HttpRequestor();
        try {

            //获取access_token
            String access_toten_result = "";
            access_toten_result = httpRequestor.doGet(DefineConstant.ACCESS_TOKEN_URL);
            LOGGER.info("access_token_result = " + access_toten_result);
            //使用Jackjson获取json里的值
            ObjectMapper mapper = new ObjectMapper();
            String accessToken = mapper.readTree(access_toten_result).get("access_token").toString();
            accessToken = accessToken.replace("\"", "");
            cacheBean.setAccessToken(accessToken);

            String requestUrl1 = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi";
            LOGGER.info(requestUrl1);
            String oppid1 = httpRequestor.doGet(requestUrl1);
            String jsapi_ticket = mapper.readTree(oppid1).get("ticket").toString();
            jsapi_ticket = jsapi_ticket.replace("\"", "");
            LOGGER.info(jsapi_ticket);
            cacheBean.setTicket(jsapi_ticket);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cacheBean;
    }


    /**
     * 阻塞队列 存放订单号
     * 接收到订单号先put ,重复的订单号 不做处理
     */
    public static final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(1000);


    public static void main(String[] args) {
      /*  for (int i = 0; i <200 ; i++) {

            try {
                final CacheBean cacheBean = WXCache.instance.cache.get("key");
//                 WXCache.instance.cache.get("key"+i);
//                LOGGER.info(cacheBean.getAccessToken());
//                LOGGER.info(cacheBean.getTicket());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }*/

        TestR testR = new TestR();
        Thread thread = new Thread(testR);
        Thread thread2 = new Thread(testR);

        thread.start();
        thread2.start();


    }

    static class TestR implements Runnable {
        @Override
        public void run() {
            try {
                for (int i = 0; i < 100; i++) {
                    // 接收的订单号 requestId
                    final String requestId = String.valueOf(i);
                    if (!WXCache.instance.queue.contains(requestId)) {
                        WXCache.instance.queue.put(requestId);
                    }

                    if (WXCache.instance.queue.size() > 990) {
                        WXCache.instance.queue.take();
                    }
                    LOGGER.info(requestId + " - " + WXCache.instance.queue);
                }
                LOGGER.info("size" + WXCache.instance.queue.size());

                for (int i = 0; i < 20; i++) {
                    final String requestId = String.valueOf(i);
                    if (WXCache.instance.queue.contains(requestId)) {
                        LOGGER.info("contains" + requestId);

                    } else {
                        LOGGER.info(" not contains" + requestId);
                    }

                }

                LOGGER.info("size" + WXCache.instance.queue.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
