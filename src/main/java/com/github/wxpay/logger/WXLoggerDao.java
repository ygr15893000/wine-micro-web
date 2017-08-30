package com.github.wxpay.logger;

import com.siran.wine.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * Created by guangrongyang on 2017/8/18.
 */
@Repository
public class WXLoggerDao  extends BaseDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }



    public int saveWXRequest(TWxRequest tWxRequest) {
        //TODO 实现sql
        final String sql = "INSERT INTO t_wx_request (out_trade_no,url_suffix,data,appid,mch_id,openid,trade_type,sign_type,transaction_id,time_end) VALUES (?,?,?,?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql,new Object[]{
                tWxRequest.getOutTradeNo(),
                tWxRequest.getUrlSuffix(),
                tWxRequest.getData(),
                tWxRequest.getAppid(),
                tWxRequest.getMchId(),
                tWxRequest.getOpenid(),
                tWxRequest.getTradeType(),
                tWxRequest.getSignType(),
                tWxRequest.getTransactionId(),
                tWxRequest.getTimeEnd()
        });
    }

    public int saveWXResponse(TWxResponse tWxResponse) {
        //TODO 实现sql
        final String sql = "INSERT INTO t_wx_response (out_trade_no,url_suffix,DATA,appid,mch_id,result_code,return_code,err_code,err_code_des,openid,total_fee,trade_type,sign_type,bank_type,transaction_id,time_end) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql,new Object[]{
                tWxResponse.getOutTradeNo(),
                tWxResponse.getUrlSuffix(),
                tWxResponse.getData(),
                tWxResponse.getAppid(),
                tWxResponse.getMchId(),
                tWxResponse.getResultCode(),
                tWxResponse.getReturnCode(),
                tWxResponse.getErrCode(),
                tWxResponse.getErrCodeDes(),
                tWxResponse.getOpenid(),
                tWxResponse.getTotalFee(),
                tWxResponse.getTradeType(),
                tWxResponse.getSignType(),
                tWxResponse.getBankType(),
                tWxResponse.getTransactionId(),
                tWxResponse.getTimeEnd()

        });
    }
}
