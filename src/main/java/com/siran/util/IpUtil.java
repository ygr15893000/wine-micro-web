package com.siran.util;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {

	/**
	 * 获取当前访问http的IP地址，获取真实的IP，越过各种代理
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
	    
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;
	}
	public static String getPath(HttpServletRequest request) {
		
		return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";  
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
