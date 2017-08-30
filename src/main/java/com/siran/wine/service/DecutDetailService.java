package com.siran.wine.service;

import com.siran.wine.model.TDecutDetail;

/**
 * Created by 唐正川 on 2017/6/27.
 */
public interface DecutDetailService {

    /**
     * 添加一个提成关系数据
     * @param decutDetail
     * @return
     */
    boolean addDecutDetail(TDecutDetail decutDetail);


}
