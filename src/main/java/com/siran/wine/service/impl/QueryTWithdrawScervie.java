package com.siran.wine.service.impl;

import com.siran.wine.dao.impl.TWithdrawDao;
import com.siran.wine.model.TWithdraw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/17.
 */

@Service
public class QueryTWithdrawScervie {

    @Autowired
    private TWithdrawDao tWithdrawDao;

    public List<TWithdraw> queryTwithList(Integer userId){

        return tWithdrawDao.getTWithdrawByUserId(userId);

    }

}
