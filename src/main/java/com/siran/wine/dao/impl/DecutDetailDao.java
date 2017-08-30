package com.siran.wine.dao.impl;

import com.siran.wine.dao.BaseDao;
import com.siran.wine.model.TDecutDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by 唐正川 on 2017/6/27.
 */
@Repository
public class DecutDetailDao extends BaseDao {


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


     public Integer addDecutDetail(TDecutDetail decutDetail){
         return   this.jdbcTemplate.update(
                 "insert into t_decut_detail (commissionUserId, userId,orderId ,createtime,amount,decutAmount,status) values (? ,?,?,?,?,?,?)",
                 new Object[]{decutDetail.getCommissionUserId(),
                         decutDetail.getUserId(),
                         decutDetail.getOrderId(),
                         decutDetail.getCreatetime(),
                         decutDetail.getAmount(),
                         decutDetail.getDecutAmount(),
                         decutDetail.getStatus()});
     }

    @Override
    public List<TDecutDetail> findAll(Object[] var) {
        final String sql = " SELECT de.createTime,de.amount,de.decutAmount,us.username FROM t_decut_detail de LEFT JOIN t_user us ON de.userId = us.id WHERE de.userId= ? ";
        return jdbcTemplate.query(sql, var, new BeanPropertyRowMapper<TDecutDetail>(TDecutDetail.class));
    }

    /**
     * 根据userId查询邀请返利总额
     * @param userId
     * @return
     */
    public Integer decutAmountSum(Integer userId) {
        return this.jdbcTemplate.queryForObject("SELECT SUM(de.decutAmount) FROM t_decut_detail de LEFT JOIN t_user us ON de.userId = us.id WHERE de.userId= ? ",
                new Object[]{userId},
                Integer.class);
    }


}
