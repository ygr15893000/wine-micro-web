package com.siran.wine.dao.impl;

import com.siran.wine.dao.BaseDao;
import com.siran.wine.model.TItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by 唐正川 on 2017/6/20.
 */
@Repository
public class ItemDao extends BaseDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public TItem getItemById(Integer id ){
        return  (TItem) this.jdbcTemplate.queryForObject
                ("select id,title,sellPoint,price,num,image,microImage,status,level,type,specification,remark from t_item where id = ?",
                        new Object[]{id},
                        new RowMapper() {
                            @Override
                            public Object mapRow(ResultSet rs, int i) throws SQLException {
                                TItem item = new TItem();
                                item.setId(rs.getInt("id"));
                                item.setTitle(rs.getString("title"));
                                item.setSellPoint(rs.getString("sellPoint"));
                                item.setPrice(rs.getBigDecimal("price"));
                                item.setNum(rs.getInt("num"));
                                item.setImage(rs.getString("image"));
                                item.setMicroImage(rs.getString("microImage"));
                                item.setStatus(rs.getByte("status"));
                                item.setLevel(rs.getInt("level"));
                                item.setType(rs.getInt("type"));
                                item.setSpecification(rs.getString("specification"));
                                item.setRemark(rs.getInt("remark"));
                                return item;
                            }
                        }
                );
    }


    public List<TItem> getAllItem(){
        return this.jdbcTemplate.query("select *  from t_item where status =1",
                 new RowMapper() {
                    @Override
                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                        TItem item = new TItem();
                        item.setId(rs.getInt("id"));
                        item.setTitle(rs.getString("title"));
                        item.setSellPoint(rs.getString("sellPoint"));
                        item.setPrice(rs.getBigDecimal("price"));
                        item.setNum(rs.getInt("num"));
                        item.setImage(rs.getString("image"));
                        item.setMicroImage(rs.getString("microImage"));
                        item.setStatus(rs.getByte("status"));
                        item.setLevel(rs.getInt("level"));
                        item.setStatus(rs.getByte("status"));
                        item.setType(rs.getInt("type"));
                        item.setSpecification(rs.getString("specification"));
                        item.setRemark(rs.getInt("remark"));
                        item.setPrepayType(rs.getBoolean("prepayType"));
                        return item;
                    }
                });
    }


}
