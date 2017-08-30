package com.siran.wine.service.impl;

import com.siran.wine.dao.impl.DecutDetailDao;
import com.siran.wine.model.TDecutDetail;
import com.siran.wine.service.DecutDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 唐正川 on 2017/6/27.
 */
@Service
public class DecutDetailServiceImpl implements DecutDetailService {

    @Autowired
    private DecutDetailDao decutDetailDao;


    @Override
    public boolean addDecutDetail(TDecutDetail decutDetail) {
        boolean flag = false;
        try{
            Integer num = decutDetailDao.addDecutDetail(decutDetail);
            if ( num > 0 ){
                flag = true;
            }
        }catch ( Exception e ){
            e.printStackTrace();
        }
        return flag;
    }

}
