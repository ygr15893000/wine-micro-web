package com.siran.wine.dao.impl;

import com.siran.wine.model.TReturnItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 唐正川 on 2017/6/28.
 */
@Repository
public class ReturnItemDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public TReturnItem getReturnItemById(Integer id){
        return (TReturnItem) this.jdbcTemplate.queryForObject("select userId,orderId,amount," +
                        " applyTime,status,type,checkId,checkTime,remark from t_return_item  where id = ?",
                new Object[]{id}, new RowMapper() {

                    @Override
                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                        TReturnItem tReturnItem = new TReturnItem();
                        tReturnItem.setUserId(rs.getInt("userId"));
                        tReturnItem.setOrderId(rs.getInt("orderId"));
                        tReturnItem.setAmount(rs.getBigDecimal("amount"));
                        tReturnItem.setApplyTime(rs.getDate("applyTime"));
                        tReturnItem.setStatus(rs.getInt("status"));
                        tReturnItem.setType(rs.getInt("type"));
                        tReturnItem.setCheckId(rs.getInt("checkId"));
                        tReturnItem.setCheckTime(rs.getDate("checkTime"));
                        tReturnItem.setRemark(rs.getString("remark"));
                        return tReturnItem;
                    }
                });
    }


    public Integer getReturnItemInformation(TReturnItem tReturnItem){
        return this.jdbcTemplate.update("insert into t_return_item (userId,orderId,amount,applyTime,remark) values( ?,?,?,?,? )",
                new Object[]{tReturnItem.getUserId(),
                tReturnItem.getOrderId(),
                tReturnItem.getAmount(),
                tReturnItem.getApplyTime(),
                tReturnItem.getRemark()});
    }

}
