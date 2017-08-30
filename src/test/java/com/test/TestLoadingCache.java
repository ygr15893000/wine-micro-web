package com.test;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by guangrongyang on 2017/8/2.
 */
public class TestLoadingCache {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestLoadingCache.class);

    public static void main(String[]  args) throws Exception {
        LoadingCache<String, String> cahceBuilder = CacheBuilder
                .newBuilder()
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        String strProValue = "hello " + key + "!";
                        return strProValue;
                    }

                });

       LOGGER.debug("jerry value:" + cahceBuilder.apply("jerry"));
       LOGGER.debug("jerry value:" + cahceBuilder.get("jerry"));
       LOGGER.debug("peida value:" + cahceBuilder.get("peida"));
       LOGGER.debug("peida value:" + cahceBuilder.apply("peida"));
       LOGGER.debug("lisa value:" + cahceBuilder.apply("lisa"));
        cahceBuilder.put("harry", "ssdded");
       LOGGER.debug("harry value:" + cahceBuilder.get("harry"));
    }

    @Test
    public void getById() throws Exception {
        LOGGER.error("seckill={}", "111");
    }
}
