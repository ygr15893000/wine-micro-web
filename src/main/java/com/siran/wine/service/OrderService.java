package com.siran.wine.service;

import com.siran.wine.model.TOrder;

import java.util.List;
import java.util.Map;

/**
 * Created by 唐正川 on 2017/6/21.
 */
public interface OrderService {


    /**
     * 添加订单
     *
     * @param order
     * @return
     */
    TOrder addOrder(TOrder order);


    /**
     * 删除订单
     *
     * @param id
     * @return
     */
    boolean deleteOrderById(Integer id);

    /**
     * 通过userId获得订单信息
     *
     * @param uid
     * @return
     */
    List<TOrder> getListOrderByUid(Integer uid);

    /**
     * 查看订单信息
     *
     * @param id
     * @return
     */
    TOrder getOrderInformationById(Integer id);

    /**
     * 订单结束
     *
     * @param order
     * @return
     */
    Integer orderEnd(TOrder order);

    /**
     * 根据订单号修改订单状态
     *
     * @param order
     * @return
     */
    Integer updateSatusByOrderNo(TOrder order);

    /**
     * 判断订单号是否存在
     *
     * @param orderNo
     * @return
     */
    boolean orderNoIfExists(String orderNo);


    /**
     * 根据订单号查看订单信息
     *
     * @param orderNo
     * @return
     */
    TOrder getOrderInformationByOrderNo(String orderNo);

    /**
     * 添加微信预支付id
     *
     * @param order
     * @return
     */
    Integer updatePrepayId(TOrder order);

    /**
     * 退款
     * @param order
     * @return
     */
    Map payRefund(TOrder order);


     /**
     * 企业付款
     * @param order
     * @return
     */
    Map transfers(TOrder order);


    /**
     * 根据订单状态 更改状态（收货退货）
     * @param order
     * @return
     */
    Map updateorderByresultsta(TOrder order);
}

