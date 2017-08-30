package com.siran.wine.service.impl;

import com.siran.wine.dao.impl.AreaDao;
import com.siran.wine.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 唐正川 on 2017/6/27.
 */
@Service
public class AreaServiceImpl implements AreaService {



    @Autowired
    private AreaDao areaDao;

    @Override
    public List getArea() {
        return areaDao.getArea();
    }
}
