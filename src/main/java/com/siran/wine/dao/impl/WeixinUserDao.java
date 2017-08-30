package com.siran.wine.dao.impl;

import com.siran.wine.model.TWeixinUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by 唐正川 on 2017/7/28.
 */
@Repository
public class WeixinUserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }



    public Integer openidIfExists(String openid){
        return this.jdbcTemplate.queryForObject("select COUNT(*) from t_weixin_user where openid = ? ",
                new Object[]{openid},
                Integer.class);
    }



    public TWeixinUser getUserIdByOpenId (String openId ){
        return this.jdbcTemplate.queryForObject("SELECT\n" +
                        "\tw.id,\n" +
                        "\tw.openid,\n" +
                        "\tw.userId,\n" +
                        "\tu.username\n" +
                        "FROM\n" +
                        "\tt_weixin_user w\n" +
                        "LEFT JOIN t_user u ON w.userId = u.id\n" +
                        "WHERE\n" +
                        "\tw.openid = ? ",new Object[]{openId},
                new RowMapper<TWeixinUser>() {
                    @Override
                    public TWeixinUser mapRow(ResultSet rs, int i) throws SQLException {
                        TWeixinUser tWeixinUser = new TWeixinUser();
                        tWeixinUser.setId(rs.getInt("id"));
                        tWeixinUser.setUserId(rs.getInt("userId"));
                        tWeixinUser.setOpenid(rs.getString("openid"));
                        tWeixinUser.setUsername(rs.getString("username"));
                        return tWeixinUser;
                    }
                });
    }

    public Integer  insertTWeixinUser(TWeixinUser tWeixinUser){
        return   this.jdbcTemplate.update(
                "insert into t_weixin_user ( openid ,userId , authtime ) values ( ?,?,? )",
                new Object[]{ tWeixinUser.getOpenid(),tWeixinUser.getUserId(),tWeixinUser.getAuthtime() });
    }


    public TWeixinUser getOpenidByUserId (Integer userId ){
        return this.jdbcTemplate.queryForObject("SELECT\n" +
                        "\topenid\n" +
                        "FROM\n" +
                        "\t t_weixin_user \n" +
                        "WHERE\n" +
                        "\t userId = ? ",new Object[]{userId},
                new RowMapper<TWeixinUser>() {
                    @Override
                    public TWeixinUser mapRow(ResultSet rs, int i) throws SQLException {
                        TWeixinUser tWeixinUser = new TWeixinUser();
                        tWeixinUser.setOpenid(rs.getString("openid"));
                        return tWeixinUser;
                    }
                });
    }


}
