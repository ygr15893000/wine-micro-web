package com.siran.wine.web.order;

import com.siran.common.constant.DefineConstant;
import com.siran.wine.model.TAddress;
import com.siran.wine.model.TOrder;
import com.siran.wine.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 唐正川 on 2017/6/21.
 */
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;


//添加订单
    @RequestMapping(value = "/order/addOrder" )
    @ResponseBody
    public Map addOrder( TOrder order, TAddress address ){
        Map map = new HashMap();
        TOrder num = orderService.addOrder(order);
        map.put(DefineConstant.CODE,null);
        map.put(DefineConstant.DESC,null);
        map.put(DefineConstant.DATA,num);
        return  map;
    }

    //删除订单
    @RequestMapping(value = "/order/deleteOrder" )
    @ResponseBody
    public Map deleteOrder(Integer id ){
        Map map = new HashMap();
        boolean flag = orderService.deleteOrderById(id);
        map.put(DefineConstant.CODE,null);
        map.put(DefineConstant.DESC,null);
        map.put(DefineConstant.DATA,flag);

        return  map;
    }
//通过用户id得到用户的订单信息
    @RequestMapping(value = "/order/getListOrderByUid" )
    @ResponseBody
    public Map getListOrderByUid(Integer  userId ){
        Map map = new HashMap();
        List<TOrder> list = orderService.getListOrderByUid(userId);
        map.put(DefineConstant.CODE,null);
        map.put(DefineConstant.DESC,null);
        map.put(DefineConstant.DATA,list);
        return  map;
    }

 //订单id得到订单信息
    @RequestMapping(value = "/order/getOrderInformationById" )
    @ResponseBody
    public Map getOrderInformationById(Integer  id ){
        Map map = new HashMap();
       TOrder order = orderService.getOrderInformationById(id);
        map.put(DefineConstant.CODE,null);
        map.put(DefineConstant.DESC,null);
        map.put(DefineConstant.DATA,order);
        return  map;
    }


    @RequestMapping(value = "/order/orderEnd" )
    @ResponseBody
    public Map orderEnd( TOrder  order ){
        Map map = new HashMap();
        Integer num = orderService.orderEnd(order);
        map.put(DefineConstant.CODE,null);
        map.put(DefineConstant.DESC,null);
        map.put(DefineConstant.DATA,num);
        return  map;
    }

    @RequestMapping(value = "wx/pay/refund")
    @ResponseBody
    public Map payRefund(TOrder order) {
        return orderService.payRefund(order);
    }


    @RequestMapping(value = "wx/promotion/transfers")
    @ResponseBody
    public Map transfers(TOrder order) {
        return orderService.transfers(order);
    }

    //退货确认收货
    @RequestMapping(value = "/order/tuishou")
    @ResponseBody
    public Map tsH(TOrder order) {
        return orderService.updateorderByresultsta(order);
    }
}
