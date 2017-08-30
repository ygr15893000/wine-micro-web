package com.siran.wine.web.itemConfig;

import com.siran.common.constant.DefineConstant;
import com.siran.wine.model.TItemConfig;
import com.siran.wine.service.ItemConfigService;
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
public class ItemConfigController {

    @Autowired
    private ItemConfigService itemConfigService;


    @RequestMapping(value="/itemConfig/getItemConfigById")
    @ResponseBody
    public Map getItemConfigById ( Integer id ){
        Map map = new HashMap();
        TItemConfig tItemConfig = itemConfigService.getTItemConfigById(id);
        map.put(DefineConstant.CODE,null);
        map.put(DefineConstant.DESC,null);
        map.put(DefineConstant.DATA,tItemConfig);
        return  map;
    }
}
