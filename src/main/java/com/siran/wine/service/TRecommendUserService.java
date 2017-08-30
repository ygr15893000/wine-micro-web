package com.siran.wine.service;

import com.siran.wine.model.TDecutDetail;
import com.siran.wine.model.TRecommendUser;

import java.util.List;
import java.util.Map;

/**
 * Created by 唐正川 on 2017/6/26.
 *
 * 推荐关系表
 */
public interface TRecommendUserService {

    /**
     *添加一个推荐关系
     * @param tRecommendUser
     * @return
     */
    boolean insertTRecommendUser(TRecommendUser tRecommendUser);


    /**
     * 根据userId得到推荐人编号
     * @param userId
     * @return
     */
    Integer getRecommendUserIdByUserId(Integer userId);


}
