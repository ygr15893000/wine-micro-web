package com.siran.common;

/**
 * Created by guangrongyang on 17/6/15.
 */
public enum EnumReturnCode {


    //******** info 900 ~ 999
    /**
     * 成功
     */
    success_000("000","成功"),
    success_999("999","成功"),
    success_998("998","验证成功"),
    success_997("997","注册成功"),
    success_996("996","用户名不存在，可以注册"),
    success_995("995","已确认收货"),
    success_994("994","已确认退货"),
    //******** error 100 ~ 399
    error_100("100","验证码错误"),
    error_101("101","密码错误"),
    error_102("102","注册失败"),
    error_103("103","手机验证码输入错误"),
    error_104("104","用户名或者密码错误"),
    error_105("105","请输入验证码"),
    error_106("106","用户名已存在"),
    error_107("107","暂无数据"),
    error_108("108","推荐人不存在"),
    error_109("109","数据操作失败"),
    error_110("110","参数非法"),
    //******** warn 400 ~ 499
    warn_401("401","警告");





    private String code;
    private String desc;

    EnumReturnCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "EnunReturnCode{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

    public static void main(String[] args) {
        System.out.println(EnumReturnCode.error_100.getCode());
        System.out.println(EnumReturnCode.error_100.getDesc());
        System.out.println(EnumReturnCode.error_100);
    }
}
