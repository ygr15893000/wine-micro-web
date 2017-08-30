package com.siran.wine.service.impl;

import com.siran.wine.dao.impl.TRecommendUserDao;
import com.siran.wine.model.TDecutDetail;
import com.siran.wine.model.TRecommendUser;
import com.siran.wine.service.TRecommendUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by 唐正川 on 2017/6/26.
 */
@Service
public class TRecommendUserServiceImpl implements TRecommendUserService {



    @Autowired
    private TRecommendUserDao tRecommendUserDao;


    @Override
    public boolean insertTRecommendUser(TRecommendUser tRecommendUser) {
        boolean flag  = false ;
        try{
            Integer num = tRecommendUserDao.insertTRecommendUser(tRecommendUser);
            if ( num > 0  ){
                flag  = true ;
            }
        }catch ( Exception e ){
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public Integer getRecommendUserIdByUserId(Integer userId) {
        TRecommendUser tRecommendUser = tRecommendUserDao.getRecommendUserIdByUserId(userId);
        return tRecommendUser.getRecommendUserId();
    }


}
