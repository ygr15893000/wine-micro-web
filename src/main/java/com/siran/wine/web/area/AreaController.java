package com.siran.wine.web.area;

import com.siran.wine.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by 唐正川 on 2017/6/27.
 */
@Controller
public class AreaController {

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/area/getProv")
    @ResponseBody
    public List getArea(){
        List list = areaService.getArea();
        return  list;
    }
}
