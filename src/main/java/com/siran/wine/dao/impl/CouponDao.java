package com.siran.wine.dao.impl;

import com.siran.wine.dao.BaseDao;
import com.siran.wine.model.TCoupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by 唐正川 on 2017/8/10.
 */
@Repository
public class CouponDao extends BaseDao {


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<TCoupon> findAll(Object[] var) {
        final String sql = "select * from t_coupon where userId = ?";

        return jdbcTemplate.query(sql, var, new BeanPropertyRowMapper<TCoupon>(TCoupon.class));

    }


    public TCoupon getCouponByUserId(Integer userId ){
        return  (TCoupon) this.jdbcTemplate.queryForObject
                ("select id,price,status,created,beginDate,endDate,partnerTradeNo from t_coupon where userId = ?",
                        new Object[]{userId},
                        new RowMapper() {
                            @Override
                            public Object mapRow(ResultSet rs, int i) throws SQLException {
                                TCoupon tCoupon = new TCoupon();
                                tCoupon.setId(rs.getInt("id"));
                                tCoupon.setPrice(rs.getBigDecimal("price"));
                                tCoupon.setStatus(rs.getByte("status"));
                                tCoupon.setCreated(rs.getString("beginDate"));
                                tCoupon.setCreated(rs.getString("endDate"));
                                tCoupon.setPartnerTradeNo(rs.getString("partnerTradeNo"));
                                return tCoupon;
                            }
                        }
                );
    }

    public TCoupon getCouponById(Integer id) {
        return (TCoupon) this.jdbcTemplate.queryForObject
                ("select id,price,status,created,beginDate,endDate,partnerTradeNo from t_coupon where id = ?",
                        new Object[]{id},
                        new RowMapper() {
                            @Override
                            public Object mapRow(ResultSet rs, int i) throws SQLException {
                                TCoupon tCoupon = new TCoupon();
                                tCoupon.setId(rs.getInt("id"));
                                tCoupon.setPrice(rs.getBigDecimal("price"));
                                tCoupon.setStatus(rs.getByte("status"));
                                tCoupon.setBeginDate(rs.getString("beginDate"));
                                tCoupon.setEndDate(rs.getString("endDate"));
                                tCoupon.setPartnerTradeNo(rs.getString("partnerTradeNo"));
                                return tCoupon;
                            }
                        }
                );
    }


    public Integer getCouponCount( Integer userId ){
        return this.jdbcTemplate.queryForObject("select count(*) from t_coupon where userId = ?",
                new Object[]{userId},
                Integer.class);
    }
}
