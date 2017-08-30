package com.siran.wine.service;

import com.siran.wine.model.TCoupon;

import java.util.List;

/**
 * Created by 唐正川 on 2017/8/10.
 */
public interface CouponService {



    TCoupon getCouponByUserId(Integer userId);

    List<TCoupon> getCouponListByUserId(Object[] var);
}
