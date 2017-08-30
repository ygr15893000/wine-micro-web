package com.siran.wine.service.impl;

import com.siran.wine.dao.impl.ItemConfigDao;
import com.siran.wine.model.TItemConfig;
import com.siran.wine.service.ItemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 唐正川 on 2017/6/21.
 */
@Service
public class ItemConfigServiceImpl implements ItemConfigService{

    @Autowired
    private ItemConfigDao itemConfigDao;


    @Override
    public TItemConfig getTItemConfigById(Integer id) {
        return itemConfigDao.getTItemConfigById(id);
    }



}
