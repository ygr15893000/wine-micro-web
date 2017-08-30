package com.siran.wine.service.impl;

import com.siran.wine.dao.impl.OrderDao;
import com.siran.wine.dao.impl.ReturnItemDao;
import com.siran.wine.model.TOrder;
import com.siran.wine.model.TReturnItem;
import com.siran.wine.service.ReturnItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by 唐正川 on 2017/6/28.
 */
@Service
public class ReturnItemServiceImpl implements ReturnItemService {

    @Autowired
    private ReturnItemDao returnItemDao;
    @Autowired
    private OrderDao orderDao;

    @Override
    public TReturnItem getReturnItemById(Integer id) {
        return returnItemDao.getReturnItemById(id);
    }

    @Override
    public boolean getReturnItemInformation(TReturnItem tReturnItem) {
        boolean flag = false;
        try{
            int orderId = tReturnItem.getOrderId();
            TOrder order = orderDao.getOrderInformationById(orderId);
            //1、未付款，2、待付尾款 3、已付款，4.退款  5.退款完成  6、待发货，7、已发货，8.退货 9.退货完成，10。确认收货  11.系统确认收货 12、交易关闭
            if( order.getStatus().equals("2") ){
                tReturnItem.setAmount(order.getFirstPay());
            }
            if ( order.getStatus().equals("3") ){
                BigDecimal b1 = order.getFirstPay();
                BigDecimal b2 = order.getEndPay();
                tReturnItem.setAmount(b1.add(b2));
            }
            tReturnItem.setApplyTime(new Date());
            Integer num = returnItemDao.getReturnItemInformation(tReturnItem);
            if( num > 0 ) {
                order.setStatus(8);
                if (orderDao.updateSatus(order) == 1){
                    flag = true;
                }
            }
        }catch ( Exception e ){
            e.printStackTrace();
        }
        return flag;
    }
}
