package com.siran.wine.service.impl;

import com.siran.wine.dao.impl.ItemDao;
import com.siran.wine.model.TItem;
import com.siran.wine.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 唐正川 on 2017/6/20.
 */
@Service
public class ItemServiceImpl implements ItemService{

    @Autowired
    private ItemDao itemDao;


    @Override
    public TItem getItemById(Integer id) {
        return itemDao.getItemById(id);
    }

    //查询所有商品
    @Override
    public List<TItem> getAllItem() {
        List<TItem> list = itemDao.getAllItem();

      return list;
    }
}
