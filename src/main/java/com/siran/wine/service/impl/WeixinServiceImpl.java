package com.siran.wine.service.impl;

import com.siran.wine.dao.impl.WeixinUserDao;
import com.siran.wine.model.TWeixinUser;
import com.siran.wine.service.WeixinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 唐正川 on 2017/7/28.
 */
@Service
public class WeixinServiceImpl implements WeixinService {

    @Autowired
    private WeixinUserDao weixinUserDao;



    @Override
    public TWeixinUser getOpenidByUserId(Integer userId) {
        return  weixinUserDao.getOpenidByUserId(userId);
    }

}
