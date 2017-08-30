package com.siran.util;

import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 唐正川 on 2017/6/20.
 *
 * 解决跨域 方式一
 */
@Component
public class SimpleCORSFilter implements Filter{

    @Override public void init(FilterConfig filterConfig) throws ServletException { }

    @Override public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

    /*String origin = (String) servletRequest.getRemoteHost()+":"+servletRequest.getRemotePort();*/
    response.setHeader("Access-Control-Allow-Origin",  request.getHeader("Origin"));
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,Content-type");
    response.setHeader("Access-Control-Allow-Credentials","true");
    filterChain.doFilter(request, response); }

    @Override public void destroy() { }


}