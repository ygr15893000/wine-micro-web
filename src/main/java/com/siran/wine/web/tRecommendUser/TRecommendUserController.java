package com.siran.wine.web.tRecommendUser;

import com.siran.common.constant.DefineConstant;
import com.siran.wine.model.TRecommendUser;
import com.siran.wine.service.TRecommendUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 唐正川 on 2017/6/26.
 */
@Controller
public class TRecommendUserController {


    @Autowired
    private TRecommendUserService tRecommendUserService;

    @RequestMapping(value = "tRecommendUser/insertTRecommendUser")
    @ResponseBody
    public Map insertTRecommendUser(TRecommendUser tRecommendUser){
        Map map  = new HashMap();
       boolean flag  =  tRecommendUserService.insertTRecommendUser(tRecommendUser);
        map.put(DefineConstant.CODE,null);
        map.put(DefineConstant.DESC,null);
        map.put(DefineConstant.DATA,flag);
        return  map;
    }


    @RequestMapping(value = "tRecommendUser/getRecommendUserIdByUserId")
    @ResponseBody
    public Map getRecommendUserIdByUserId(Integer userId){
        Map map  = new HashMap();
        Integer num  =  tRecommendUserService.getRecommendUserIdByUserId(userId);
        map.put(DefineConstant.CODE,null);
        map.put(DefineConstant.DESC,null);
        map.put(DefineConstant.DATA,num);
        return  map;
    }



}
