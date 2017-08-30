package com.siran.wine.web.address;

import com.siran.common.constant.DefineConstant;
import com.siran.wine.model.TAddress;
import com.siran.wine.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 唐正川 on 2017/6/21.
 */
@Controller
public class AddressController {


    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/address/insertAddress" )
    @ResponseBody
    public Map insertAddress(TAddress address){
        Map map = new HashMap();
        boolean flag = addressService.insertAddress(address);
        map.put(DefineConstant.CODE,null);
        map.put(DefineConstant.DESC,null);
        map.put(DefineConstant.DATA,flag);
        return  map;
    }


    @RequestMapping(value = "/address/deleteAddress" )
    @ResponseBody
    public Map deleteAddress(Integer id){
        Map map = new HashMap();
        boolean flag = addressService.deleteAddress(id);
        map.put(DefineConstant.CODE,null);
        map.put(DefineConstant.DESC,null);
        map.put(DefineConstant.DATA,flag);
        return  map;
    }


    @RequestMapping(value = "/adress/selectAddressById" )
    @ResponseBody
    public Map selectAddressById(Integer id){
        Map map = new HashMap();
        TAddress address = addressService.selectAddressById(id);
        map.put(DefineConstant.CODE,null);
        map.put(DefineConstant.DESC,null);
        map.put(DefineConstant.DATA,address);
        return  map;
    }

    @RequestMapping(value = "/address/getTAddressByUid" )
    @ResponseBody
    public Map getTAddressByUid(Integer  userId){
        Map map = new HashMap();
        List<TAddress> list = addressService.getTAddressByUid(userId);
        map.put(DefineConstant.CODE,null);
        map.put(DefineConstant.DESC,null);
        map.put(DefineConstant.DATA,list);
        return  map;
    }


    @RequestMapping(value = "/address/updateAddress" )
    @ResponseBody
    public Map updateAddress(TAddress  address){
        Map map = new HashMap();
        boolean flag = addressService.updateAddress(address);
        map.put(DefineConstant.CODE,null);
        map.put(DefineConstant.DESC,null);
        map.put(DefineConstant.DATA,flag);
        return  map;
    }
}
