package com.siran.wine.service;

import com.siran.wine.model.TWeixinUser;

/**
 * Created by 唐正川 on 2017/7/28.
 */
public interface WeixinService {


    /**
     * 根据用户id查询openid
     * @param userId
     * @return
     */
    TWeixinUser getOpenidByUserId(Integer userId);


}
