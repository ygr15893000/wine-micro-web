package com.siran.wine.web.decutdetail;

import com.siran.common.constant.DefineConstant;
import com.siran.wine.model.TDecutDetail;
import com.siran.wine.service.DecutDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 唐正川 on 2017/6/27.
 *
 * 提成计算
 */
@Controller
public class DecutDetailController {

    @Autowired
    private DecutDetailService decutDetailService;


    @RequestMapping(value = "decutdetail/addDecutDetail")
    @ResponseBody
    public Map addDecutDetail(TDecutDetail decutDetail){
        Map map = new HashMap();
        boolean flag = decutDetailService.addDecutDetail(decutDetail);
        map.put(DefineConstant.CODE,null);
        map.put(DefineConstant.DESC,null);
        map.put(DefineConstant.DATA,flag);
        return  map;

    }
}
