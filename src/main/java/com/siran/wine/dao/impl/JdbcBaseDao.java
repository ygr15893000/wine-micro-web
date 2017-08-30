package com.siran.wine.dao.impl;

import com.siran.wine.model.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by guangrongyang on 17/3/31.
 */
@Repository
public class JdbcBaseDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<String> findAll (){

        final String sql = "select username from t_user";
        return jdbcTemplate.queryForList(sql , String.class);

//        return jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<SysUser>(SysUser.class));
    }

    public List<TUser> findAll4() {
        final String sql = "select id, email, username from t_user";

        return jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<TUser>(TUser.class));
    }
}
