package com.siran.wine.service.impl;

import com.github.wxpay.sdk.WXPayConstants;
import com.siran.common.EnumReturnCode;
import com.siran.common.constant.ConstantDateFormat;
import com.siran.common.constant.DefineConstant;
import com.siran.util.TimeUtil;
import com.siran.wine.dao.impl.*;
import com.siran.wine.model.*;
import com.siran.wine.service.OrderService;
import com.siran.wx.WxPayController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 唐正川 on 2017/6/21.
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ItemConfigAgioDao itemConfigAgioDao;
    @Autowired
    private TRecommendUserDao tRecommendUserDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private DecutDetailDao decutDetailDao;
    @Autowired
    WxPayController wxPayController;
    @Autowired
    private TWithdrawDao tWithdrawDao;

    @Autowired
    private ItemDao itemDao;
    @Autowired
    private FundrecordDao fundrecordDao;

    @Override
    public TOrder addOrder(TOrder order) {
        Integer num = 0;
        //1、未付款，2、待付尾款 3、已付款，4.退款  5.退款完成  6、待发货，7、已发货，8.退货 9.退货完成，10。确认收货  11.系统确认收货 12、交易关闭
        order.setStatus(1);
        //生成订单号
        String orderNo = TimeUtil.getTimeStringMillisecond();
        order.setOrderNO(orderNo);

        //支付商品 type=1 或 充值type=2
        if (order.getType() == 1) {
            Integer itemConfigAgioId = order.getItemConfigAgioId();
            TItemConfigAgio tItemConfigAgio = itemConfigAgioDao.getTItemConfigAgioById(itemConfigAgioId);
            // type 1:预定自选首付折扣 2:购买满额折扣              status    1:启用 2:禁用
//                if (tItemConfigAgio.getType() == 1 && tItemConfigAgio.getStatus() == 1) {
            final TItem item = itemDao.getItemById(order.getItemId());
            final int cId = tItemConfigAgio.getcId();
            //得到折扣
            final BigDecimal discount = tItemConfigAgio.getDiscount();
            //商品总金额 单价* 数量
            BigDecimal amout = item.getPrice().multiply(new BigDecimal(order.getNum()));
            /**
             *1 常规酒类（不打折）1 不打折
             *2 预付打折酒类（打折）1 预付打折酒类（打折）
             *3 一次性付打折酒类（打折）1 一次性付打折酒类（打折）
             */
            final BigDecimal zero = new BigDecimal("0");

            BigDecimal firstPay = new BigDecimal("0.00");
            // 打折
            if (discount.compareTo(new BigDecimal("100")) < 0) {

                if (cId == 2) {
                    //首付款
                    firstPay = tItemConfigAgio.getFirstPay();


                    //算出尾款
                    //算出打折后商品的价格
                    BigDecimal discountAfter = amout.multiply(discount).divide(new BigDecimal("100"));

                    //打折后的商品价格减去首付款，获得尾款
                    BigDecimal endPay = discountAfter.subtract(firstPay).setScale(2, RoundingMode.UP);

                    //设置订单首付款
                    order.setFirstPay(firstPay);
                    //设置订单尾款;
                    order.setEndPay(endPay);
                    order.setAmount(firstPay.add(endPay));
                } else if (cId == 3) {

                    //设置订单首付款

                    //算出打折后商品的价格
                    BigDecimal discountAfter = amout.multiply(discount).divide(new BigDecimal("100"));

                    //设置订单尾款;
                    order.setFirstPay(zero);
                    order.setEndPay(zero);
                    order.setAmount(discountAfter);
                }
            }else {
                order.setAmount(amout);

            }


            //订单收货信息
//            order.setReceiverName(order.getReceiverName());
//            order.setReceiverMobile(order.getReceiverMobile());
//            order.setReceiverState(order.getReceiverState());
//            order.setReceiverCity(order.getReceiverCity());
//            order.setReceiverRegion(order.getReceiverRegion());
//            order.setReceiverAddress(order.getReceiverAddress());
//            order.setReceiverZip(order.getReceiverZip());
            //添加成功后返回主键


        } else if (order.getType() == 2) {

        }
        Integer id = orderDao.addOrder(order);
        order.setId(id);
        return order;
    }

    @Override
    public boolean deleteOrderById(Integer id) {
        boolean flag = false;
        try {
            Integer num = orderDao.deleteOrderById(id);
            if (num > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    //根据用户Id获取订单
    @Override
    public List<TOrder> getListOrderByUid(Integer uid) {
        return orderDao.getListOrderByUid(uid);
    }

    //根据订单id获取订单信息
    @Override
    public TOrder getOrderInformationById(Integer id) {
        return orderDao.getOrderInformationById(id);
    }

    //交易结束
    @Override
    public Integer orderEnd(TOrder order) {
        Integer num = 0;
        try {
            int userId = order.getUserId();
            //根据用户id得到用户对象
            TUser user = userDao.getTUserById(userId);
            //判断用户是否有推荐人
            if (StringUtils.isNotBlank(user.getRefferee())) {
                //根据userId得到推荐人编号
                TRecommendUser tRecommendUser = tRecommendUserDao.getRecommendUserIdByUserId(userId);
                //获得推荐人id
                int recommendUserId = tRecommendUser.getRecommendUserId();
                //订单总金额
                BigDecimal amount = order.getAmount();
                //算出提成金额
                //测试代码，折扣设为5%
                BigDecimal b2 = new BigDecimal(Double.valueOf(0.05));
                BigDecimal decutAmount = amount.multiply(b2).setScale(BigDecimal.ROUND_HALF_UP, 2);
                //创建一个提成表对象，并且赋值
                TDecutDetail decutDetail = new TDecutDetail();
                decutDetail.setCommissionUserId(recommendUserId);
                decutDetail.setUserId(userId);
                int orderId = order.getId();
                decutDetail.setOrderId(orderId);
                decutDetail.setCreatetime(ConstantDateFormat.SF_FULL.format(new Date()));
                decutDetail.setAmount(amount);
                decutDetail.setDecutAmount(decutAmount);
                //0-未发放，1-已发放
                decutDetail.setStatus(1);
                //把信息添加到提成表中
                if (decutDetailDao.addDecutDetail(decutDetail) == 1) {
                    //根据推荐人id获得推荐人对象
                    TUser user2 = userDao.getTUserById(recommendUserId);
                    //得到用户当前可用余额
                    BigDecimal currentUsableSum = user2.getUsableSum();
                    BigDecimal usableSum = currentUsableSum.add(decutAmount);
                    //修改用户的可用余额
                    if (userDao.updateUsableSum(recommendUserId, usableSum) == 1) {
                        //修改订单状态
                        if (orderDao.updateStatusAndIfCommission(orderId) == 1) {
                            num = 1;
                        }
                    }
                }
            } else {
                //1、未付款，2、待付尾款 3、已付款，4.退款  5.退款完成  6、待发货，7、已发货，8.退货 9.退货完成，10。确认收货  11.系统确认收货 12、交易关闭
                order.setStatus(10);
                order.setEndTime(ConstantDateFormat.SF_FULL.format(new Date()));
                Integer nu = orderDao.orderEnd(order);
                if (nu > 0) {
                    num = 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }

    //更改订单状态通过订单号
    @Override
    @Transactional
    public Integer updateSatusByOrderNo(TOrder order) {
        StringBuffer sql ;
        Object[] var  = new Object[]{order.getStatus(),order.getRealAmount(),order.getOrderNO()};
        if (order.getStatus() ==2){
            sql = new StringBuffer("update t_order set firstPaymentTime =CURRENT_TIMESTAMP(), status = ?,realAmount =realAmount+ ? where orderNO = ?");
        }else {
            sql = new StringBuffer("update t_order set paymenTime =CURRENT_TIMESTAMP(), status = ?,realAmount =realAmount+ ? where orderNO = ?");

        }



        final int userId = order.getUserId();
        final TUser user = userDao.getUserById(userId);
        TFundrecord fundrecord = new TFundrecord();
        EnumReturnCode enumReturnCode = null;
        final BigDecimal amount = order.getAmount();
        if (order.getType() ==1){
            enumReturnCode = EnumReturnCode.fundMode_1;
            fundrecord.setIncome(new BigDecimal("0"));
            fundrecord.setUsableSum(user.getUsableSum());

            //充值
        }else if (order.getType() == 2) {
            enumReturnCode = EnumReturnCode.fundMode_2;
            fundrecord.setIncome(amount);
            fundrecord.setUsableSum(user.getUsableSum().add(amount));


        }
        fundrecord.setUserId(userId);
        fundrecord.setOperateType(Integer.valueOf(enumReturnCode.getCode()));
        fundrecord.setFundMode(enumReturnCode.getDesc());
        fundrecord.setHandleSum(amount);

        fundrecord.setFreezeSum(user.getFreezeSum());
        fundrecord.setRemarks(enumReturnCode.getDesc());
        fundrecord.setCost(new BigDecimal("0"));
        fundrecord.setSpending(new BigDecimal("0"));


        final Integer i = fundrecordDao.save(fundrecord);
        final Integer j = orderDao.updateOrderCallBack(sql.toString(), var);
        return i+j;
    }

    //订单号是否存在
    @Override
    public boolean orderNoIfExists(String orderNo) {
        boolean flag = false;
        try {
            Integer num = orderDao.orderNoIfExists(orderNo);
            if (num > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public TOrder getOrderInformationByOrderNo(String orderNo) {
        return orderDao.getOrderInformationByOrderNo(orderNo);
    }


    public Integer updatePrepayId(TOrder order) {
        return orderDao.updatePrepayId(order);
    }

    /**
     * 根据订单id 退款
     *
     * @param order
     * @return
     */
    @Transactional
    public Map payRefund(TOrder order) {
        Map map = new HashMap();
        //TODO 根据订单id ,
        final TOrder orderInformationByOrderNo = getOrderInformationByOrderNo(order.getOrderNO());
        //判断状态：9退货完成执行
        if (orderInformationByOrderNo.getStatus() == 9) {
            order.setAmount(orderInformationByOrderNo.getAmount());
            order.setOrderNO(orderInformationByOrderNo.getOrderNO());
            order.setId(orderInformationByOrderNo.getId());
            order.setOutRefundNo(TimeUtil.getTimeStringMillisecond());

            //調用微信退款之前更行t_order表退款订单号,更新操作status = 4
            order.setRefundTime(ConstantDateFormat.SF_FULL.format(new Date()));
            Integer fundNo = orderDao.updateOutRefundNo(order);
            if (fundNo > 0) {
                //TODO 调用微信退款
                final Map payRefund = wxPayController.payRefund(order);
                String returnCode = (String) payRefund.get("return_code");
                if (!returnCode.equals(WXPayConstants.SUCCESS)) {
                    //t_order表Status状态改成6：退款失败
                    order.setStatus(6);
                    order.setOutRefundTime(ConstantDateFormat.SF_FULL.format(new Date()));
                    orderDao.updateSatusByOrderNo(order);
                    map.put(DefineConstant.CODE, payRefund.get("return_msg"));
                    map.put(DefineConstant.DESC, payRefund.get("return_msg"));
                    return map;

                }

                String resultCode = (String) payRefund.get("result_code");
                if (!resultCode.equals(WXPayConstants.SUCCESS)) {
                    //t_order表Status状态改成6：退款失败
                    order.setStatus(6);
                    order.setOutRefundTime(ConstantDateFormat.SF_FULL.format(new Date()));
                    orderDao.updateSatusByOrderNo(order);
                    map.put(DefineConstant.CODE, payRefund.get("err_code"));
                    map.put(DefineConstant.DESC, payRefund.get("err_code_des"));
                    return map;

                }

                //调用微信退款接口成功后执行t_order表更新操作status = 5
                order.setOutRefundTime(ConstantDateFormat.SF_FULL.format(new Date()));
                Integer nums = orderDao.upadateRefundTime(order);

                // fundrecord beigin
                final int userId = order.getUserId();
                final TUser user = userDao.getUserById(userId);
                TFundrecord fundrecord = new TFundrecord();
                EnumReturnCode enumReturnCode = EnumReturnCode.fundMode_6;
                final BigDecimal amount = order.getAmount();

                fundrecord.setIncome(new BigDecimal("0"));
                fundrecord.setUsableSum(user.getUsableSum());


                fundrecord.setUserId(userId);
                fundrecord.setOperateType(Integer.valueOf(enumReturnCode.getCode()));
                fundrecord.setFundMode(enumReturnCode.getDesc());
                fundrecord.setHandleSum(amount);

                fundrecord.setFreezeSum(user.getFreezeSum());
                fundrecord.setRemarks(enumReturnCode.getDesc());
                fundrecord.setCost(new BigDecimal("0"));
                fundrecord.setSpending(new BigDecimal("0"));

                final Integer i = fundrecordDao.save(fundrecord);
                // fundrecord end

                if (nums > 0) {
                    map.put(DefineConstant.CODE, EnumReturnCode.success_000.getCode());
                    map.put(DefineConstant.DESC, EnumReturnCode.success_000.getDesc());
                } else {
                    map.put(DefineConstant.CODE, EnumReturnCode.error_109.getCode());
                    map.put(DefineConstant.DESC, EnumReturnCode.error_109.getDesc());
                }

            } else {
                map.put(DefineConstant.CODE, EnumReturnCode.error_109.getCode());
                map.put(DefineConstant.DESC, EnumReturnCode.error_109.getDesc());

            }
        } else {
            map.put(DefineConstant.CODE, EnumReturnCode.error_110.getCode());
            map.put(DefineConstant.DESC, EnumReturnCode.error_110.getDesc());
        }
        return map;
    }

    /**
     * 企业付款
     *
     * @param order
     * @return
     */
    @Transactional
    public Map transfers(TOrder order) {
        Map map = new HashMap();
        final TUser user = userDao.getTUserById(order.getUserId());
        final BigDecimal amount = order.getAmount();
        final BigDecimal usableSum = user.getUsableSum();
        order.setOrderNO(TimeUtil.getTimeStringMillisecond());
        LOGGER.info("申请企业付款 金额：" + amount + " user:" + user.toString());

        //TODO 比较取现的金额和账号可用余额
        if (amount.compareTo(usableSum) == 1) {
            LOGGER.error("申请企业付款 金额：" + amount + " 大于可用余额:" + usableSum);
            map.put(DefineConstant.DESC, "申请企业付款 金额：" + amount + " 大于可用余额:" + usableSum);
            return map;
        }

        //TODO 微信单笔限额介于1和2万之间
        if (amount.compareTo(new BigDecimal("1")) < 0 || amount.compareTo(new BigDecimal("20000")) > 0) {
            LOGGER.error("申请企业付款单笔最小金额为1元,最大为2万元，请核对金额数量。本次请求金额为：" + amount);
            map.put(DefineConstant.DESC, "申请企业付款单笔最小金额为1元,最大为2万元，请核对金额数量。本次请求金额为：" + amount);
            return map;
        }

        //TODO insert 表 t_withdraw，update  User 可用=可用-amount，冻结=冻结+amount
        TWithdraw tWithdraw = new TWithdraw();
        tWithdraw.setName(user.getUsername());
        tWithdraw.setCellPhone(user.getUsername());
        tWithdraw.setAcount(user.getUsername());
        tWithdraw.setSum(amount);
        tWithdraw.setApplyTime(ConstantDateFormat.SF_FULL.format(new Date()));
        tWithdraw.setUserId(user.getId());
        tWithdraw.setPartnerTradeNo(order.getOrderNO());
        //调用微信企业支付前插入一条数据t_withdraw
        int twt_id = tWithdrawDao.InsertTwithdrawsList(tWithdraw);
        //更新user表：可用=可用-amount，冻结=冻结+amount
        boolean upd = userDao.updateUserSum(amount, user.getId());
        LOGGER.info("未调用微信接口前更新t_withdraw状态为：" + twt_id);
        LOGGER.info("未调用微信接口前更新t_user状态为：" + upd);
        if (twt_id > 0 && upd) {

            // fundrecord beigin
            final int userId = order.getUserId();
            TFundrecord fundrecord = new TFundrecord();
            EnumReturnCode enumReturnCode = EnumReturnCode.fundMode_3;

            fundrecord.setIncome(new BigDecimal("0"));
            fundrecord.setUsableSum(user.getUsableSum().subtract(amount));


            fundrecord.setUserId(userId);
            fundrecord.setOperateType(Integer.valueOf(enumReturnCode.getCode()));
            fundrecord.setFundMode(enumReturnCode.getDesc());
            fundrecord.setHandleSum(amount);

            fundrecord.setFreezeSum(user.getFreezeSum().add(amount));
            fundrecord.setRemarks(enumReturnCode.getDesc());
            fundrecord.setCost(new BigDecimal("0"));
            fundrecord.setSpending(new BigDecimal("0"));
            fundrecordDao.save(fundrecord);
            map.put(DefineConstant.CODE, EnumReturnCode.success_000.getCode());
            map.put(DefineConstant.DESC, EnumReturnCode.success_000.getDesc());
        } else {
            //update t_withdraw,User表数据失败
            map.put(DefineConstant.CODE, EnumReturnCode.error_109.getCode());
            map.put(DefineConstant.DESC, EnumReturnCode.error_109.getDesc());
        }
        return map;
    }

    /**
     * 提现方法抽出，用于后台调用微信
     * @param withdraw
     * @return
     */
    @Transactional
    public Map sureTwithdraw(TWithdraw withdraw){
        Map map = new HashMap();
        final TUser user = userDao.getTUserById(withdraw.getUserId());
        final BigDecimal amount = withdraw.getSum();
        TOrder order = new TOrder();
        order.setOrderNO(withdraw.getPartnerTradeNo());
        order.setAmount(amount);
        LOGGER.info("申请企业付款 金额：" + amount + " user:" + user.toString());
        final Map transfers = wxPayController.transfers(order, user);
        LOGGER.info("返回参数map===" + transfers);
        if (!transfers.get("return_code").equals(WXPayConstants.SUCCESS)) {
            //调用微信接口失败，更新t_withdraw  status状态
            tWithdrawDao.updateTwiths(withdraw.getId());
            map.put(DefineConstant.CODE, transfers.get("return_msg"));
            map.put(DefineConstant.DESC, transfers.get("return_msg"));
            return map;
        }

        if (!transfers.get("result_code").equals(WXPayConstants.SUCCESS)) {
            //调用微信接口失败，更新t_withdraw  status状态
            tWithdrawDao.updateTwiths(withdraw.getId());
            map.put(DefineConstant.CODE, transfers.get("err_code"));
            map.put(DefineConstant.DESC, transfers.get("err_code_des"));
            return map;
        }

        //update  表 t_withdraw.status,提现成功时间，set User 冻结=冻结-amount
        String times = ConstantDateFormat.SF_FULL.format(new Date());
        boolean upateTw = tWithdrawDao.updateTwithdrawsStatus(times,withdraw.getId());
        boolean updateUs = userDao.updateUserFreeSum(amount, user.getId());
        LOGGER.info("调用微信成功接口后更新t_withdraw状态为：" + upateTw);
        LOGGER.info("调用微信成功接口后更新t_user状态为：" + updateUs);

        if (upateTw && updateUs) {
            map.put(DefineConstant.CODE, EnumReturnCode.success_000.getCode());
            map.put(DefineConstant.DESC, EnumReturnCode.success_000.getDesc());
        } else {
            //微信支付调用成功：update t_withdraw,User表数据失败
            map.put(DefineConstant.CODE, EnumReturnCode.error_109.getCode());
            map.put(DefineConstant.DESC, EnumReturnCode.error_109.getDesc());

        }

        final int userId = withdraw.getUserId();
        TFundrecord fundrecord = new TFundrecord();
        // fundrecord beigin
        fundrecord = new TFundrecord();
        EnumReturnCode enumReturnCode = EnumReturnCode.fundMode_4;

        fundrecord.setIncome(new BigDecimal("0"));
        fundrecord.setUsableSum(user.getUsableSum().subtract(amount));


        fundrecord.setUserId(userId);
        fundrecord.setOperateType(Integer.valueOf(enumReturnCode.getCode()));
        fundrecord.setFundMode(enumReturnCode.getDesc());
        fundrecord.setHandleSum(amount);

        fundrecord.setFreezeSum(user.getFreezeSum());
        fundrecord.setRemarks(enumReturnCode.getDesc());
        fundrecord.setCost(new BigDecimal("0"));
        fundrecord.setSpending(amount);

        final Integer i = fundrecordDao.save(fundrecord);
        // fundrecord end
        return map;
    }


    /**
     * 根据订单状态 更改状态（收货退货）
     *
     * @param order
     * @return
     */
    public Map updateorderByresultsta(TOrder order) {
        Map map = new HashMap();
        if (order.getStatus() != null) {
            //确认收货
            if (order.getStatus() == 7 && order.getResultStatus() == 10) {
                Integer num = orderDao.updateSatusSh(order);
                if (num > 0) {
                    map.put(DefineConstant.CODE, EnumReturnCode.success_995.getCode());
                    map.put(DefineConstant.DESC, EnumReturnCode.success_995.getDesc());
                }
            }
            //确认退货
            else if (order.getStatus() == 8 && order.getResultStatus() == 9) {
                Integer num1 = orderDao.updateSatusTh(order);
                if (num1 > 0) {
                    map.put(DefineConstant.CODE, EnumReturnCode.success_994.getCode());
                    map.put(DefineConstant.DESC, EnumReturnCode.success_994.getDesc());
                }
            } else {
                map.put(DefineConstant.CODE, EnumReturnCode.error_109.getCode());
                map.put(DefineConstant.DESC, EnumReturnCode.error_109.getDesc());
            }
        } else {
            map.put(DefineConstant.CODE, EnumReturnCode.error_109.getCode());
            map.put(DefineConstant.DESC, EnumReturnCode.error_109.getDesc());
        }
        return map;
    }

    public static void main(String[] args) {
        BigDecimal bda = new BigDecimal("1");
        BigDecimal bdb = new BigDecimal("19999");
        BigDecimal bdc = new BigDecimal("20000");
        String str = "0";
        if (bdb.compareTo(bda) < 0 || bdb.compareTo(bdc) > 0) {
            str = "1";
        }
        System.out.print(str);
    }
}
