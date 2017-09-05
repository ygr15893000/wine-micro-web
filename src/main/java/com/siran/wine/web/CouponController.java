package com.siran.wine.web;

import com.siran.common.constant.DefineConstant;
import com.siran.common.EnumReturnCode;
import com.siran.wine.model.TCoupon;
import com.siran.wine.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 唐正川 on 2017/8/10.
 */
@Controller
public class CouponController {

    @Autowired
    private CouponService couponService;


    @RequestMapping("/getCouponByUserId")
    @ResponseBody
    public TCoupon getCouponByUserId( Integer userId ){
        TCoupon tCoupon = couponService.getCouponByUserId(userId);
        return tCoupon;
    }

    @RequestMapping("/getCouponListByUserId")
    @ResponseBody
    public Map getCouponListByUserId(Integer userId) {
        Map map = new HashMap();
        map.put(DefineConstant.CODE, EnumReturnCode.success_000.getCode());
        map.put(DefineConstant.DESC,EnumReturnCode.success_000.getDesc());
        map.put(DefineConstant.DATA,couponService.getCouponListByUserId(new Object[]{userId}));
        return map  ;
    }


    @RequestMapping("/getCouponListByUserId1")
    @ResponseBody
    public Object getCouponListByUserId1(Integer userId) {
        final List<TCoupon> couponList = couponService.getCouponListByUserId(new Object[]{userId});
        final ResponseEntity responseEntity = new ResponseEntity(couponList, HttpStatus.OK);
        return responseEntity;
    }


}
