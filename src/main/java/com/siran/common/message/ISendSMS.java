package com.siran.common.message;

/**
 * 短信发送接口
 * @author ygr
 *
 */
public interface ISendSMS {
	/**
	 * @param mobile 手机号
	 * @param content 短信内容
	 * @return success </br>failed
	 */
	 String send(String mobile, String content) ;
}
