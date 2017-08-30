package com.siran.wine.web.returnItem;

import com.siran.common.constant.DefineConstant;
import com.siran.wine.model.TReturnItem;
import com.siran.wine.service.ReturnItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 唐正川 on 2017/6/28.
 */
@Controller
public class ReturnItemController {

    @Autowired
    private ReturnItemService returnItemService;

    @RequestMapping(value = "/returnItem/getReturnItemById")
    @ResponseBody
    public Map getReturnItemById( Integer id ){
        Map map = new HashMap();
        TReturnItem tReturnItem = returnItemService.getReturnItemById(id);
        map.put(DefineConstant.CODE, null);
        map.put(DefineConstant.DESC, null);
        map.put(DefineConstant.DATA, tReturnItem);
        return  map;
    }

    @RequestMapping(value = "/returnItem/getReturnItemInformation")
    @ResponseBody
    public Map getReturnItemInformation( TReturnItem tReturnItem ){
        Map map = new HashMap();
        boolean flag = returnItemService.getReturnItemInformation(tReturnItem);
        map.put(DefineConstant.CODE, null);
        map.put(DefineConstant.DESC, null);
        map.put(DefineConstant.DATA, flag);
        return  map;
    }




}
