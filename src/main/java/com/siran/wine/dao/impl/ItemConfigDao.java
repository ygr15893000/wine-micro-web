package com.siran.wine.dao.impl;

import com.siran.wine.dao.BaseDao;
import com.siran.wine.model.TItem;
import com.siran.wine.model.TItemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 唐正川 on 2017/6/21.
 */
@Repository
public class ItemConfigDao extends BaseDao{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public TItemConfig getTItemConfigById(Integer id ){
        return  (TItemConfig) jdbcTemplate.queryForObject
                ("select name,status from t_item_config where id = ?",
                        new Object[]{id},
                        new RowMapper() {
                            @Override
                            public Object mapRow(ResultSet rs, int i) throws SQLException {
                                TItemConfig itemConfig = new TItemConfig();
                                itemConfig.setName(rs.getString("name"));
                                itemConfig.setStatus(rs.getByte("status"));
                                return itemConfig;
                            }
                        }
                );
    }



}
