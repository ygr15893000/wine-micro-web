package com.siran.wine.service;

import com.siran.wine.model.TReturnItem;

/**
 * Created by 唐正川 on 2017/6/28.
 */

public interface ReturnItemService {
    /**
     * 根据id查询退货信息
     * @param id
     * @return
     */
    TReturnItem getReturnItemById( Integer id );


    /**
     * 添加退货信息
     * @param tReturnItem
     * @return
     */
    boolean getReturnItemInformation( TReturnItem tReturnItem );
}
