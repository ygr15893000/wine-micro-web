package com.siran.wine.dao.impl;

import com.siran.wine.dao.BaseDao;
import com.siran.wine.model.TOrder;
import com.siran.wine.model.TRecommendUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by 唐正川 on 2017/6/26.
 */
@Repository
public class TRecommendUserDao extends BaseDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Integer insertTRecommendUser(TRecommendUser tRecommendUser){
        return   this.jdbcTemplate.update(
                "insert into t_recommend_user_new (userId, recommendUserId,beginDate) values (?,? ,?)",
                new Object[]{tRecommendUser.getUserId(), tRecommendUser.getRecommendUserId(),new Date()});
    }


    public TRecommendUser getRecommendUserIdByUserId(Integer userId){
        return (TRecommendUser) this.jdbcTemplate.queryForObject("select id,recommendUserId,beginDate," +
                        " source from t_recommend_user_new where userId = ?",
               new Object[]{userId}, new RowMapper() {

                    @Override
                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                        TRecommendUser tRecommendUser = new TRecommendUser();
                        tRecommendUser.setId(rs.getInt("id"));
                        tRecommendUser.setRecommendUserId(rs.getInt("recommendUserId"));

                        tRecommendUser.setBeginDate(rs.getString("beginDate"));
                        tRecommendUser.setSource(rs.getShort("source"));
                        return tRecommendUser;
                    }
                });
    }

    public Integer getRecommendUserCount( Integer recommendUserId ){
        return this.jdbcTemplate.queryForObject("select count(*) from t_recommend_user_new where recommendUserId = ?",
                new Object[]{recommendUserId},
                Integer.class);
    }

    /**
     * 根据userId查找邀请人数
     * @param userId
     * @return
     */
    public Integer invitecount(Integer userId) {
        return this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM t_recommend_user_new WHERE recommendUserId = ? ",
                new Object[]{userId},
                Integer.class);
    }

    //查询推荐人关联用户
    @Override
    public List<TRecommendUser> findAll(Object[] var) {
        final String sql =" SELECT mend.recommendUserId,mend.userId,us.username ,mend.createTime FROM  t_recommend_user_new mend\n" +
                " LEFT JOIN t_user us  ON us.id = mend.userId  WHERE mend.recommendUserId = ? ";
        return jdbcTemplate.query(sql, var, new BeanPropertyRowMapper<TRecommendUser>(TRecommendUser.class));
    }

}
