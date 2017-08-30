package com.siran.wine.dao.impl;

import com.siran.wine.model.TArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by 唐正川 on 2017/6/27.
 */
@Repository
public class AreaDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List getArea(){
        return  this.jdbcTemplate.query("select provId,provName,areaId,areaName from t_area",
                new RowMapper() {
                    @Override
                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                        TArea area = new TArea();
                        area.setProvId(rs.getString("provId"));
                        area.setProvName(rs.getString("provName"));
                        area.setAreaId(rs.getString("areaId"));
                        area.setAreaName(rs.getString("areaName"));
                        return area;
                    }
                });
    }
}
