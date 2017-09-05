package com.siran.wine.service.impl;

import com.siran.common.constant.ConstantDateFormat;
import com.siran.wine.dao.impl.CouponDao;
import com.siran.wine.model.TCoupon;
import com.siran.wine.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by 唐正川 on 2017/8/10.
 */
@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponDao couponDao;

    @Override
    public TCoupon getCouponByUserId(Integer userId) {
        return couponDao.getCouponByUserId(userId);
    }

    @Override
    public List<TCoupon> getCouponListByUserId(Object[] var) {
        final List<TCoupon> list = couponDao.findAll(var);
        for (TCoupon tCoupon: list
             ) {
            final String updated = tCoupon.getUpdated();

            tCoupon.setUpdated(updated.substring(0,19));
            final String now = ConstantDateFormat.SF_Y_M_D.format(new Date());
            if (now.compareTo(tCoupon.getEndDate()) ==1){
                tCoupon.setStatus((byte) 4);
            }

        }
        return list;
    }
}
