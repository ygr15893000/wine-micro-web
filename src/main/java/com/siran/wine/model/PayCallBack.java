package com.siran.wine.model;

/**
 * Created by 唐正川 on 2017/7/7.
 */
/**
 * 支付成功回复
 * @ClassName: PayCallBack
 * @Description: TODO
 * @author 唐正川
 * @date 2017年7月7日
 *
 */
public class PayCallBack {
    private String return_code;
    private String return_msg;

    public PayCallBack() {
        this.return_code = "SUCCESS";
        this.return_msg = "OK";
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

}
