package com.siran.wine.service;

import com.siran.wine.model.TItem;
import com.siran.wine.model.TItemConfigAgio;

import java.util.List;

/**
 * Created by 唐正川 on 2017/6/20.
 */
public interface ItemService {
    /**
     * 通过商品id获得商品对象
     * @param id
     * @return
     */
    TItem getItemById(Integer id);


    /**
     * 获取全部商品
     * @return
     */
    List<TItem> getAllItem();
}
