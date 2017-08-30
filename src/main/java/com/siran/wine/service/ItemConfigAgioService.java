package com.siran.wine.service;

import com.siran.wine.model.TItemConfigAgio;

import java.util.List;

/**
 * Created by 唐正川 on 2017/6/21.
 */
public interface ItemConfigAgioService {


    /**
     * 根据id查询TItemConfigAgio
     * @param id
     * @return
     */
    TItemConfigAgio getTItemConfigAgioById(Integer id);


    List<TItemConfigAgio> findAllByCid(Integer cid);

}
