package com.siran.wine.service.impl;

import com.siran.wine.dao.impl.ItemConfigAgioDao;
import com.siran.wine.model.TItemConfigAgio;
import com.siran.wine.service.ItemConfigAgioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 唐正川 on 2017/6/21.
 */
@Service
public class ItemConfigAgioServiceImpl implements ItemConfigAgioService {


    @Autowired
    private ItemConfigAgioDao itemConfigAgioDao;

    @Override
    public TItemConfigAgio getTItemConfigAgioById(Integer id) {
        return itemConfigAgioDao.getTItemConfigAgioById(id);
    }

    @Override
    public List<TItemConfigAgio> findAllByCid(Integer cid) {
        return itemConfigAgioDao.findAllByCid(cid);
    }
}
