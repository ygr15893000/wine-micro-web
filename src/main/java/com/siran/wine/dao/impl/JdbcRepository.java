package com.siran.wine.dao.impl;

import org.springframework.stereotype.Repository;

/**
 * Created by guangrongyang on 17/3/31.
 */
@Repository
public class JdbcRepository {

   /* @Autowired
    private JdbcOperations jdbcOperations;

    public List<String> findAll (){

        final String sql = "select username from t_user";
        return jdbcOperations.queryForList(sql , String.class);

//        return jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<SysUser>(SysUser.class));
    }

    public List<TUser> findAll4() {
        final String sql = "select id, email, username from t_user";

        return jdbcOperations.query(sql, new Object[]{}, new BeanPropertyRowMapper<TUser>(TUser.class));
    }*/
}
