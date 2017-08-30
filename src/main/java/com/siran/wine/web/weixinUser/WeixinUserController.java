package com.siran.wine.web.weixinUser;

import com.siran.wine.model.TWeixinUser;
import com.siran.wine.service.WeixinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 唐正川 on 2017/7/28.
 */
@Controller
public class WeixinUserController {


    @Autowired
    private WeixinService weixinService;

    @RequestMapping( value = "/weixinUser/getOpenidByUserId")
    @ResponseBody
    public TWeixinUser getOpenidByUserId( Integer userId ){
        TWeixinUser tWeixinUser ;
        tWeixinUser = weixinService.getOpenidByUserId(userId);
        if ( tWeixinUser == null ){
            return null;
        }else{
            return tWeixinUser;
        }
    }



}
