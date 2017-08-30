package com.siran.wine.dao.impl;

import com.siran.wine.dao.BaseDao;
import com.siran.wine.model.TAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;


/**
 * Created by 唐正川 on 2017/6/21.
 */
@Repository
public class AddressDao extends BaseDao{


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public Integer  insertAddress(TAddress address){
        return   this.jdbcTemplate.update(
                "insert into t_address (receiverName,receiverPhone,receiverMobile,receiverState," +
                        "receiverCity,receiverAddress,receiverZip,created,userId) values " +
                        "(? ,?,?,?,?,?,?,?,? )",
                new Object[]{address.getReceiverName(),
                        address.getReceiverPhone(),
                        address.getReceiverMobile(),
                        address.getReceiverState(),
                        address.getReceiverCity(),
                        address.getReceiverAddress(),
                        address.getReceiverZip(),new Date(),
                        address.getUserId()
                        });
    }


    public Integer updateAddressStatus( Integer id ){
        return  this.jdbcTemplate.update(
                "UPDATE  t_address set status  = 2 where id = ? ",
                new Object[]{ id });

    }

    public TAddress selectAddressById( Integer id ){
        return  (TAddress) this.jdbcTemplate.queryForObject
                ("select receiverName,receiverPhone,receiverMobile,receiverState,receiverCity," +
                                "receiverAddress,receiverZip from t_address where id = ?",
                        new Object[]{id},
                        new AddressRowMaper()
                       );
    }



    public List<TAddress> getTAddressByUid( Integer userId ){
        return this.jdbcTemplate.query("select id,receiverName,receiverPhone,receiverMobile,receiverState," +
                        "receiverCity,receiverAddress,receiverZip,status from t_address where userId = ?",
                new Object[]{userId}, new RowMapper() {
            @Override
            public Object mapRow(ResultSet rs, int i) throws SQLException {
                TAddress address = new TAddress();
                address.setId(rs.getInt("id"));
                address.setReceiverName(rs.getString("receiverName"));
                address.setReceiverPhone(rs.getString("receiverPhone"));
                address.setReceiverMobile(rs.getString("receiverMobile"));
                address.setReceiverState(rs.getString("receiverState"));
                address.setReceiverCity(rs.getString("receiverCity"));
                address.setReceiverAddress(rs.getString("receiverAddress"));
                address.setReceiverZip(rs.getString("receiverZip"));
                address.setStatus(rs.getByte("status"));
                return address;
            }
        });
    }

    //修改地址信息
    public Integer updateAddress( TAddress address ){
        return   this.jdbcTemplate.update(
                "UPDATE t_address set receiverName = ? , receiverPhone = ? , receiverMobile = ? , receiverState= ?" +
                        ", receiverCity = ? ,  receiverAddress= ? , receiverZip = ? where id = ?",
                new Object[]{address.getReceiverName(),
                        address.getReceiverPhone(),
                        address.getReceiverMobile(),
                        address.getReceiverState(),
                        address.getReceiverCity(),
                        address.getReceiverAddress(),
                        address.getReceiverZip(),
                        address.getId()
                });
    }

    class AddressRowMaper implements RowMapper<TAddress>{
            @Override
            public TAddress mapRow(ResultSet rs, int i) throws SQLException {
                TAddress address = new TAddress();
                address.setReceiverName(rs.getString("receiverName"));
                address.setReceiverPhone(rs.getString("receiverPhone"));
                address.setReceiverMobile(rs.getString("receiverMobile"));
                address.setReceiverState(rs.getString("receiverState"));
                address.setReceiverCity(rs.getString("receiverCity"));
                address.setReceiverAddress(rs.getString("receiverAddress"));
                address.setReceiverZip(rs.getString("receiverZip"));
                address.setCreated(new Date());
                return address;
            }
    }
}
