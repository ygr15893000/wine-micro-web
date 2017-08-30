package com.siran.wine.service.impl;

import com.siran.wine.dao.impl.AddressDao;
import com.siran.wine.model.TAddress;
import com.siran.wine.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 唐正川 on 2017/6/21.
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDao addressDao;

//添加地址
    @Override
    public boolean insertAddress(TAddress address) {
        boolean flag = false;
        try{
            Integer num = addressDao.insertAddress(address);
            if ( num > 0 ){
                flag = true ;
            }
        }catch ( Exception e ){
            e.printStackTrace();
        }
        return flag;
    }

    //删除地址
    @Override
    public boolean deleteAddress(Integer id) {
    boolean flag =  false;
    try{
        //把t_address表里 status改为2  '1:启用 2:禁用
        Integer num = addressDao.updateAddressStatus(id);
        if ( num > 0 ) {
            flag = true;
        }
    }catch ( Exception e ){
        e.printStackTrace();
    }

        return flag;
    }

    //通过地址id查询地址
    @Override
    public TAddress selectAddressById(Integer id) {
        return addressDao.selectAddressById(id);
    }

    //修改地址
    @Override
    public boolean updateAddress(TAddress address) {
        boolean flag =  false;
        try{
            Integer num = addressDao.updateAddress(address);
            if ( num > 0 ) {
                flag = true;
            }
        }catch ( Exception e ){
            e.printStackTrace();
        }
        return flag;
    }

    //通过userId查询收货地址
    @Override
    public List<TAddress> getTAddressByUid(Integer uid) {
        List<TAddress> arrayList = new ArrayList();
        List<TAddress> list = addressDao.getTAddressByUid(uid);
        for ( TAddress address : list ){
            //status  '1:启用 2:禁用'
            if (address.getStatus() == 1){
                arrayList.add(address);
            }
        }
       return arrayList;
    }
}
