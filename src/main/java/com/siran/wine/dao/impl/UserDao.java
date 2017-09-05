package com.siran.wine.dao.impl;

import com.siran.wine.dao.BaseDao;
import com.siran.wine.model.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 唐正川 on 2017/6/14.
 */

@Repository
public class UserDao extends BaseDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //用户登录
    public TUser login(TUser user) throws Exception {
        return (TUser)this.jdbcTemplate.queryForObject("select u.id,username,password,refferee,lastIP,createTime,usableSum,freezeSum,headImg,isLoginLimit,realName,sex,birthday,idNo,acount,level ,twu.openid " +
                        "                              from t_user u left JOIN  t_weixin_user twu on u.id= twu.userId " +
                        "                               where   username = ? and password = ?"
                , new Object[]{user.getUsername(), user.getPassword()}
                , new UserRowMapper()
        );

    }

    //注册
    public Integer save(TUser user) {
        final String sql = "insert into t_user (username, password,createTime) values (? ,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int autoIncId;
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword());
                //订单创建时间 以及 首付款时间
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = format.format(new Date());
                ps.setString(3, date);
                return ps;
            }
        }, keyHolder);
        autoIncId = keyHolder.getKey().intValue();
        return autoIncId;

    }

    //根据用户id查询用户信息
    public TUser getTUserById(Integer id) {
        TUser user = (TUser) this.jdbcTemplate.queryForObject
                ("select \n" +
                                "u.id,\n" +
                                "username,\n" +
                                "password,\n" +
                                "refferee,\n" +
                                "lastIP,\n" +
                                "createTime,\n" +
                                "usableSum,\n" +
                                "freezeSum,\n" +
                                "headImg,\n" +
                                "realName,\n" +
                                "sex,\n" +
                                "birthday,\n" +
                                "acount,\n" +
                                "level,\n" +
                                "twu.openid " +
                                "from t_user u left JOIN  t_weixin_user twu on u.id= twu.userId \n" +
                                "where \n" +
                                "u.id = ?",
                        new Object[]{id}, new UserRowMapper()
                );
        return user;
    }

    //根据用户id查询用户信息
    public TUser getUserById(Integer id) {
        TUser user = (TUser) this.jdbcTemplate.queryForObject
                ("select * from t_user where id = ?",
                        new Object[]{id}, new RowMapper() {
                            @Override
                            public Object mapRow(ResultSet rs, int i) throws SQLException {
                                TUser user = new TUser();
                                user.setId(rs.getInt("id"));
                                user.setUsername(rs.getString("username"));
                                user.setRefferee(rs.getString("refferee"));
                                user.setUsableSum(rs.getBigDecimal("usableSum"));
                                user.setFreezeSum(rs.getBigDecimal("freezeSum"));
                                return user;
                            }
                        });
        return user;
    }


    public Integer updatePwdById(Integer id, String password) {
        return this.jdbcTemplate.update("update t_user set password = ? where id = ? ",
                new Object[]{password, id});
    }

    //添加用户并且获取主键
    public Integer insertObjectAndGetAutoIncreaseId(TUser user) {
        final String sql = "insert into t_user (username, password,refferee,createTime) values (? ,?,?,?)  ";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int autoIncId;
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getRefferee());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = format.format(new Date());
                ps.setString(4, date);
                return ps;
            }
        }, keyHolder);
        autoIncId = keyHolder.getKey().intValue();
        return autoIncId;
    }


    public TUser getIdByUsername(String username) {
        return (TUser) this.jdbcTemplate.queryForObject("select id from t_user where username = ?", new Object[]{username},
                new RowMapper() {
                    @Override
                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                        TUser user = new TUser();
                        user.setId(rs.getInt("id"));
                        return user;
                    }
                });
    }

    public Integer reffereeIfExists(String refferee) {
        return this.jdbcTemplate.queryForObject("select COUNT(*) from t_user where username = ? ",
                new Object[]{refferee},
                Integer.class);
    }

    public Integer updateUsableSum(Integer id, BigDecimal usableSum) {
        return this.jdbcTemplate.update("update t_user set usableSum = ? where id = ?",
                new Object[]{usableSum, id});
    }


    public Integer userNameIfExists(String username) {
        return this.jdbcTemplate.queryForObject("select COUNT(*) from t_user where username = ? ",
                new Object[]{username},
                Integer.class);
    }

    public Integer countUser() {
        return this.jdbcTemplate.queryForObject("select COUNT(*) from t_user",
                new Object[]{},
                Integer.class);
    }


    class UserRowMapper implements RowMapper<Object> {
        public TUser mapRow(ResultSet rs, int i) throws SQLException {
            TUser user = new TUser();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
//            user.setPassword(rs.getString("password"));
            user.setRefferee(rs.getString("refferee"));
            user.setLastIP(rs.getString("lastIP"));
            user.setCreateTime(rs.getDate("createTime"));
            user.setUsableSum(rs.getBigDecimal("usableSum"));
            user.setFreezeSum(rs.getBigDecimal("freezeSum"));
            user.setHeadImg(rs.getString("headImg"));
            user.setRealName(rs.getString("realName"));
            user.setSex(rs.getInt("sex"));
            user.setBirthday(rs.getDate("birthday"));
            user.setAcount(rs.getString("acount"));
            user.setLevel(rs.getInt("level"));
            user.setOpenid(rs.getString("openid"));
            return user;
        }
    }

    /**
     * User 可用=可用-amount，冻结=冻结+amount
     * @param id
     * @return
     */
    public boolean updateUserSum(BigDecimal amount, Integer id){
        final String sql = "update t_user set usableSum = usableSum - ?,freezeSum = freezeSum + ? where id = ?";
        return jdbcTemplate.update(sql,new Object[]{amount,amount,id}) > 0 ? true:false ;
    }

    /**
     * 更新 User 冻结=冻结-amount
     * @param amount
     * @param id
     * @return
     */
    public boolean updateUserFreeSum(BigDecimal amount,Integer id){
        final String sql = "update t_user set freezeSum = freezeSum - ? where id = ?";
        return jdbcTemplate.update(sql,new Object[]{amount,id}) > 0 ? true : false;
    }

}
