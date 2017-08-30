package com.siran.wine.web.itemConfigAgio;

import com.siran.common.constant.DefineConstant;
import com.siran.wine.model.TItemConfigAgio;
import com.siran.wine.service.ItemConfigAgioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 唐正川 on 2017/6/21.
 */
@Controller
public class ItemConfigAgioController {


    @Autowired
    private ItemConfigAgioService itemConfigAgioService;


    @RequestMapping(value="/itemConfigAgio/getItemConfigAgioById")
    @ResponseBody
    public Map getTItemConfigAgioById(Integer id){
        Map map = new HashMap();
        TItemConfigAgio tItemConfigAgio = itemConfigAgioService.getTItemConfigAgioById(id);
        map.put(DefineConstant.CODE,null);
        map.put(DefineConstant.DESC,null);
        map.put(DefineConstant.DATA,tItemConfigAgio);
        return  map;
    }
}
