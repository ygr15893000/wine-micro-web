package com.siran.wine.dao.impl;

import com.siran.wine.dao.BaseDao;
import com.siran.wine.model.TItemConfigAgio;
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
 * Created by 唐正川 on 2017/6/21.
 */
@Repository
public class ItemConfigAgioDao extends BaseDao{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public TItemConfigAgio getTItemConfigAgioById(Integer id){
        return  (TItemConfigAgio) jdbcTemplate.queryForObject
                ("select cId,firstPay,totalAmount,discount,status,orderBy,type from t_item_config_agio where id = ?",
                        new Object[]{id},
                        new RowMapper() {
                            @Override
                            public Object mapRow(ResultSet rs, int i) throws SQLException {
                                TItemConfigAgio item = new TItemConfigAgio();
                                item.setcId(rs.getInt("cId"));
                                item.setFirstPay(rs.getBigDecimal("firstPay"));
                                item.setTotalAmount(rs.getBigDecimal("totalAmount"));
                                item.setDiscount(rs.getBigDecimal("discount"));
                                item.setStatus(rs.getByte("status"));
                                item.setOrderBy(rs.getByte("orderBy"));
                                item.setType(rs.getByte("type"));
                                return item;
                            }
                        }
                );
    }


    public List<TItemConfigAgio> findAllByCid(Integer cid) {
        return  jdbcTemplate.query
                ("SELECT gio.id,gio.cId,gio.firstPay,gio.totalAmount,gio.discount,gio.status,gio.orderBy,gio.type,gio.remark,con.name FROM t_item_config_agio gio  JOIN t_item_config con ON gio.cId = con.id  WHERE gio.cid = ? AND gio.status=1",
                        new Object[]{cid},
                        new BeanPropertyRowMapper(TItemConfigAgio.class)

                );
    }

}
