package com.siran.wine.service;

import com.siran.wine.model.TAddress;

import java.util.List;

/**
 * Created by 唐正川 on 2017/6/21.
 */
public interface AddressService {

    /**
     * 添加收货地址
     * @param address 地址对象
     * @return
     */
    boolean insertAddress(TAddress address);


    /**
     * 根据地址ID删除
     * @param id
     * @return
     */
    boolean deleteAddress(Integer id);


    /**
     * 根据地址ID查询地址信息
     * @param id
     * @return
     */
    TAddress selectAddressById(Integer id);


    /**
     * 修改订单
     * @param address
     * @return
     */
    boolean updateAddress(TAddress address);



    /**
     * 根据uid查询地址
     * @param uid
     * @return
     */
    List<TAddress> getTAddressByUid(Integer uid);


}
