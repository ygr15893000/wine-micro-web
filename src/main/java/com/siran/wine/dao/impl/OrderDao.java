package com.siran.wine.dao.impl;

import com.siran.wine.dao.BaseDao;
import com.siran.wine.model.TCoupon;
import com.siran.wine.model.TCouponHis;
import com.siran.wine.model.TOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by 唐正川 on 2017/6/21.
 */
@Repository
public class OrderDao extends BaseDao {


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    //添加订单
    public Integer addOrder(TOrder order) {
        final String sql = "insert into t_order (orderNo,itemId,num,amount,firstPay,endPay," +
                "status,createTime,firstPaymentTime,userId,itemConfigAgioId,receiverName," +
                "receiverMobile,receiverState,receiverCity,receiverRegion,receiverAddress,receiverZip,type) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int autoIncId;
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, order.getOrderNO());
                ps.setInt(2, order.getItemId());
                ps.setInt(3, order.getNum());
                ps.setBigDecimal(4, order.getAmount());
                ps.setBigDecimal(5, order.getFirstPay());
                ps.setBigDecimal(6, order.getEndPay());
                ps.setInt(7, order.getStatus());
                //订单创建时间 以及 首付款时间
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = format.format(new Date());
                ps.setString(8, date);
                ps.setString(9, date);
                ps.setInt(10, order.getUserId());
                ps.setInt(11, order.getItemConfigAgioId());
                ps.setString(12, order.getReceiverName());
                ps.setString(13, order.getReceiverMobile());
                ps.setString(14, order.getReceiverState());
                ps.setString(15, order.getReceiverCity());
                ps.setString(16, order.getReceiverRegion());
                ps.setString(17, order.getReceiverAddress());
                ps.setString(18, order.getReceiverZip());
                //类型，1：商品购买支付 2：充值
                ps.setInt(19, order.getType());
                return ps;
            }
        }, keyHolder);
        autoIncId = keyHolder.getKey().intValue();
        return autoIncId;

    }

    //删除订单
    public Integer deleteOrderById(Integer id) {
        return this.jdbcTemplate.update("delete from t_order where id = ?", new Object[]{id});
    }

    //根据用户id查询所有订单信息
    public List<TOrder> getListOrderByUid(Integer uid) {
        final String sql = " SELECT o.*, t.title, t.microImage,t.price,t.specification FROM t_order o LEFT JOIN t_item t ON o.itemId = t.id WHERE o.userId = ? ";
        return this.jdbcTemplate.query(sql,
                new Object[]{uid}, new BeanPropertyRowMapper<>(TOrder.class));
    }


    public TOrder getOrderInformationById(Integer id) {
        return (TOrder) this.jdbcTemplate.queryForObject
                ("SELECT o.*," +
                                "  t.title,\n" +
                                "  t.specification,\n" +
                                "  t.price,\n" +
                                "  t.microImage\n" +
                                "FROM\n" +
                                "t_order o \n" +
                                "LEFT JOIN t_item t ON o.itemId = t.id where o.id = ?",
                        new Object[]{id}, new OrderRowMapper());
    }


    public Integer orderEnd(TOrder order) {
        return this.jdbcTemplate.update("update t_order set status = ?,endTime=? where id = ? ",
                //设置订单状态  1、未付款，2、待付尾款 3、已付款，4.退款  5.退款完成  6、待发货，7、已发货，8.退货 9.退货完成，10。确认收货  11.系统确认收货 12、交易关闭
                new Object[]{order.getStatus(), order.getEndTime(), order.getId()});
    }

    public Integer updateStatusAndIfCommission(int id) {
        return this.jdbcTemplate.update("update t_order set status = ?,endTime=?,ifCommission=? where id = ? ",
                //设置订单状态  1、未付款，2、待付尾款 3、已付款，4.退款  5.退款完成  6、待发货，7、已发货，8.退货 9.退货完成，10。确认收货  11.系统确认收货 12、交易关闭
                new Object[]{"10", new Date(), 1, id});
    }

    public Integer updateOutRefundNo(TOrder order){
        //设置订单状态  1、未付款，2、待付尾款 3、已付款，4.退款  5.退款完成  6、待发货，7、已发货，8.退货 9.退货完成，10。确认收货  11.系统确认收货 12、交易关闭
        return this.jdbcTemplate.update("update t_order set outRefundNo = ?,status = ?,refundTime = ? where id = ?",
                new Object[]{order.getOutRefundNo(),"4",order.getRefundTime(),order.getId()});
    }

    //确认收货
    public Integer updateSatusSh(TOrder order) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(new Date());
        //receivedType 1.用户确认收货 ，0.时间到系统确认收货
        return this.jdbcTemplate.update("update t_order set status = ? , sellerReceivedTime = ? , receivedType = 1 where id = ? ",
                //设置订单状态  1、未付款，2、待付尾款 3、已付款，4.退款  5.退款完成  6、待发货，7、已发货，8.退货 9.退货完成，10。确认收货  11.系统确认收货 12、交易关闭
                new Object[]{order.getResultStatus(),date,order.getId()});
    }

    //确认退货
    public Integer updateSatusTh(TOrder order) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(new Date());
        return this.jdbcTemplate.update("update t_order set status = ? , applyReturnsTime = ? where id = ? ",
                //设置订单状态  1、未付款，2、待付尾款 3、已付款，4.退款  5.退款完成  6、待发货，7、已发货，8.退货 9.退货完成，10。确认收货  11.系统确认收货 12、交易关闭
                new Object[]{order.getResultStatus(),date,order.getId()});
    }

    public Integer upadateRefundTime(TOrder order){
        //设置订单状态  1、未付款，2、待付尾款 3、已付款，4.退款  5.退款完成  6、待发货，7、已发货，8.退货 9.退货完成，10。确认收货  11.系统确认收货 12、交易关闭
        return this.jdbcTemplate.update("update t_order set status = ?,outRefundTime = ? where id = ?",
                new Object[]{"5",order.getOutRefundTime(),order.getId()});
    }

    public Integer updateSatus(TOrder order) {
        return this.jdbcTemplate.update("update t_order set status = ? where id = ? ",
                //设置订单状态  1、未付款，2、待付尾款 3、已付款，4.退款  5.退款完成  6、待发货，7、已发货，8.退货 9.退货完成，10。确认收货  11.系统确认收货 12、交易关闭
                new Object[]{order.getStatus(), order.getId()});
    }

    public Integer updateSatusByOrderNo(TOrder order) {
        return this.jdbcTemplate.update("update t_order set status = ?,outRefundTime = ? where orderNO = ?",
                //设置订单状态  1、未付款，2、待付尾款 3、已付款，4.退款  5.退款完成  6、待发货，7、已发货，8.退货 9.退货完成，10。确认收货  11.系统确认收货 12、交易关闭
                new Object[]{order.getStatus(),order.getOutRefundTime(), order.getOrderNO()});
    }
    public Integer updateOrderCallBack(String sql,Object[] var) {
        return this.jdbcTemplate.update(sql,var);
    }

    public Integer orderNoIfExists(String orderNo) {
        return this.jdbcTemplate.queryForObject("select COUNT(*) from t_order where orderNO = ? ",
                new Object[]{orderNo},
                Integer.class);
    }


    public TOrder getOrderInformationByOrderNo(String orderNo) {
        return (TOrder) this.jdbcTemplate.queryForObject
                ("SELECT o.*," +
                                "  t.title,\n" +
                                "  t.price,\n" +
                                "  t.specification,\n" +
                                "  t.microImage\n" +
                                "FROM\n" +
                                "t_order o \n" +
                                "LEFT JOIN t_item t ON o.itemId = t.id where o.orderNO = ?",
                        new Object[]{orderNo}, new OrderRowMapper()
                );
    }


    public Integer updatePrepayId(TOrder order) {
        return this.jdbcTemplate.update("update t_order set prepayId = ? , orderNO = ? where id=?",
                new Object[]{order.getPrepayId(), order.getOrderNO(),order.getId()});
    }

    public Integer updatePrepayIdFirst(TOrder order) {
        return this.jdbcTemplate.update("update t_order set prepayId = ? , firstOrderNO = ?, orderNO = ? where id=?",
                new Object[]{order.getPrepayId(), order.getOrderNO(), order.getOrderNO(),order.getId()});
    }


    public Integer countOrder(Object[] var) {
        return this.jdbcTemplate.queryForObject("select COUNT(*) from t_order where STATUS = 1 AND userId= ? ",
                var,
                Integer.class);
    }

    class OrderRowMapper implements RowMapper<TOrder> {
        @Override
        public TOrder mapRow(ResultSet rs, int i) throws SQLException {

            TOrder order = new TOrder();
            order.setId(rs.getInt("id"));
            order.setFirstOrderNO(rs.getString("firstOrderNO"));
            order.setOrderNO(rs.getString("orderNO"));
            order.setOutRefundNo(rs.getString("outRefundNo"));
            order.setType(rs.getInt("type"));
            order.setItemId(rs.getInt("itemId"));
            order.setNum(rs.getShort("num"));
            order.setRealAmount(rs.getBigDecimal("realAmount"));
            order.setAmount(rs.getBigDecimal("amount"));
            order.setFirstPay(rs.getBigDecimal("firstPay"));
            order.setEndPay(rs.getBigDecimal("endPay"));
            order.setStatus(rs.getInt("status"));
            order.setCreateTime(rs.getString("createTime"));
            order.setUpdateTime(rs.getString("updateTime"));
            order.setFirstPaymentTime(rs.getString("firstPaymentTime"));
            order.setPaymenTime(rs.getString("paymenTime"));
            order.setConsignTime(rs.getString("consignTime"));
            order.setEndTime(rs.getString("endTime"));
            order.setShippingName(rs.getString("shippingName"));
            order.setShippingCode(rs.getString("shippingCode"));
            order.setUserId(rs.getInt("userId"));
            order.setItemConfigAgioId(rs.getInt("itemConfigAgioId"));
            order.setIfCommission(rs.getInt("ifCommission"));
            order.setReceiverName(rs.getString("receiverName"));
            order.setReceiverPhone(rs.getString("receiverPhone"));
            order.setReceiverMobile(rs.getString("receiverMobile"));
            order.setReceiverState(rs.getString("receiverState"));
            order.setReceiverCity(rs.getString("receiverCity"));
            order.setReceiverAddress(rs.getString("receiverAddress"));
            order.setReceiverZip(rs.getString("receiverZip"));
            order.setTitle(rs.getString("title"));
            order.setMicroImage(rs.getString("microImage"));
            order.setPrice(rs.getBigDecimal("price"));
            order.setSpecification(rs.getString("specification"));
            return order;
        }
    }

    /**
     * 根据id更新金额
     * @param decimal
     * @param id
     * @return
     */
    public Integer updateTorderAmount(BigDecimal decimal,int id){
        final String sql = "UPDATE t_order SET amount = amount - ? WHERE id = ?";
        return jdbcTemplate.update(sql,new Object[]{decimal,id});

    }


    /**
     *
     * @param tCoupon
     * @return
     */
    public Integer updateTcouponStatus(TCoupon tCoupon){
        final String sql = "UPDATE t_coupon SET STATUS = ?,ordId =? WHERE id = ?";
        return jdbcTemplate.update(sql,new Object[]{"2",tCoupon.getOrdId(),tCoupon.getId()});

    }


    public Integer insertTcouponHisList(TCouponHis tCouponHis){
        final String sql = "INSERT INTO t_coupon_his (userId,couponId,amount,TYPE) VALUES (?,?,?,?)";
        return jdbcTemplate.update(sql,new Object[]{
            tCouponHis.getUserId(),
            tCouponHis.getCouponId(),
            tCouponHis.getAmount(),
            tCouponHis.getType()    
        });

    }

}
