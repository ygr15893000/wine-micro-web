package com.siran.wine.service;

import com.siran.wine.model.TItemConfig;

/**
 * Created by 唐正川 on 2017/6/21.
 */
public interface ItemConfigService {

    /**
     * 通过id获取TItemConfig对象
     * @param id
     * @return
     */
    TItemConfig getTItemConfigById(Integer id);
}
