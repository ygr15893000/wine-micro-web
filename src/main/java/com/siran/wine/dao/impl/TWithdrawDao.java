package com.siran.wine.dao.impl;

import com.siran.wine.dao.BaseDao;
import com.siran.wine.model.TWithdraw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/17.
 */

@Repository
public class TWithdrawDao extends BaseDao{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<TWithdraw> getTWithdrawByUserId(Integer userId){
        final String sql = "SELECT * FROM t_withdraw WHERE userId = ? ";
        return jdbcTemplate.query(sql,new Object[]{userId},new BeanPropertyRowMapper<TWithdraw>(TWithdraw.class));
    }


    //提现记录表插入提现记录并获取主键id
    public int InsertTwithdrawsList(TWithdraw tWithdraw){
        final String sql = "INSERT INTO t_withdraw (NAME,cellPhone,acount,SUM,applyTime,userId,partnerTradeNo) VALUES (?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int autoIncId;
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, tWithdraw.getName());
                ps.setString(2, tWithdraw.getCellPhone());
                ps.setString(3, tWithdraw.getAcount());
                ps.setBigDecimal(4, tWithdraw.getSum());
                ps.setString(5, tWithdraw.getApplyTime());
                ps.setInt(6, tWithdraw.getUserId());
                ps.setString(7, tWithdraw.getPartnerTradeNo());
                return ps;
            }
        }, keyHolder);
        autoIncId = keyHolder.getKey().intValue();
        return autoIncId;
    }

    /**
     * 表 t_withdraw.status,提现成功时间
     * @param times
     * @param id
     * @return
     */
    public boolean updateTwithdrawsStatus(String times,int id){
        final String sql = "update t_withdraw set status = ?,paymentTime = ? where id = ?";
        return jdbcTemplate.update(sql,new Object[]{"2",times,id}) > 0 ? true :false;
    }

    /**
     * 表 t_withdraw.status,提现失败更新操作
     *
     * @param id
     * @return
     */
    public boolean updateTwiths(Integer id){
        final String sql = "update t_withdraw set status = ? where id = ?";
        return jdbcTemplate.update(sql,new Object[]{"5",id}) > 0 ? true : false;

    }

}
