package com.siran.wine.dao.impl;

import com.siran.wine.dao.BaseDao;
import com.siran.wine.model.TFundrecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * Created by guangrongyang on 2017/8/30.
 */
@Repository
public class FundrecordDao extends BaseDao{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Integer save(Object var) {
        final TFundrecord fundrecord = (TFundrecord) var;

        StringBuffer sql = new StringBuffer();

        sql.append(" INSERT INTO t_fundrecord ( userId, operateType, fundMode, handleSum, usableSum, freezeSum, trader, recordTime, remarks, cost, income, spending) ");
        sql.append(" VALUES ");
        sql.append(" 	(  ?, ?, ?, ?, ?, ?,-1, CURRENT_TIMESTAMP, ?, ?, ?, ?) ");
        Object[] param = new Object[]{
                fundrecord.getUserId(), fundrecord.getOperateType(), fundrecord.getFundMode()
                , fundrecord.getHandleSum(), fundrecord.getUsableSum(), fundrecord.getFreezeSum()
                ,fundrecord.getRemarks(),fundrecord.getCost(), fundrecord.getIncome()
                , fundrecord.getSpending()
        };

        return jdbcTemplate.update(sql.toString(),param);
    }
}
