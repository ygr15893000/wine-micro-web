package com.siran.wx;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Created by guangrongyang on 2017/8/18.
 */
//@Component
//@Aspect
public class WXAdvices {
    @Pointcut("execution(* com.github.wxpay.sdk.WXPay.*(..))")
    public void pointcut() {
    }

    @Before("execution(* com.github.wxpay.sdk.WXPay.*(..))")
    public void before(JoinPoint jp) {
        System.out.println("----------前置通知----------");
        System.out.println(jp.getSignature().getName());
    }

    @After("execution(* com.siran.wx.WxPayController.*(..))")
    public void after(JoinPoint jp) {
        System.out.println("----------最终通知----------");
    }

    //环绕通知
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjd) throws Throwable {
        System.out.println("--------------------环绕开始--------------------");
        System.out.println(pjd.getSignature().getName());
        Object object = pjd.proceed();
        System.out.println("--------------------环绕结束--------------------");
        return object;
    }
}
