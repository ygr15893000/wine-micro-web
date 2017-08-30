package com.siran.wine.web.item;

import com.siran.common.constant.DefineConstant;
import com.siran.wine.model.TItem;
import com.siran.wine.model.TItemConfigAgio;
import com.siran.wine.service.ItemConfigAgioService;
import com.siran.wine.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 唐正川 on 2017/6/20.
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemConfigAgioService itemConfigAgioService;


    @RequestMapping(value = "/item/getItem" )
    @ResponseBody
    public Map getItem( Integer id ){
        Map map = new HashMap();
        TItem item = itemService.getItemById(id);
        List<TItemConfigAgio> list = itemConfigAgioService.findAllByCid(item.getType());
        map.put(DefineConstant.CODE,null);
        map.put(DefineConstant.DESC,null);
        map.put("item",item);
        map.put("config",list);
    return  map;
    }


    @RequestMapping(value = "/item/getAllItem" )
    @ResponseBody
    public Map getAllItem(){
        Map map = new HashMap();
        List<TItem> list = itemService.getAllItem();
        map.put(DefineConstant.CODE,null);
        map.put(DefineConstant.DESC,null);
        map.put(DefineConstant.DATA,list);
        return  map;
    }
}
