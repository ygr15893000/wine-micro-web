package com.siran.common.constant;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by guangrongyang on 2017/8/14.
 */
public class ConstantDateFormat {
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final SimpleDateFormat  SF_FULL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * yyyy-MM-dd
     */
    public static final SimpleDateFormat  SF_Y_M_D = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * yyyyMMdd
     */
    public static final SimpleDateFormat  SF_YMD = new SimpleDateFormat("yyyyMMdd");


    public static void main(String[] args) {
        String times = ConstantDateFormat.SF_FULL.format(new Date());
        System.out.print(times);
    }

}
