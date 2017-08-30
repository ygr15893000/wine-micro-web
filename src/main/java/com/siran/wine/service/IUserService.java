package com.siran.wine.service;

import com.siran.wine.model.TDecutDetail;
import com.siran.wine.model.TUser;

import java.util.List;
import java.util.Map;

/**
 * Created by guangrongyang on 17/6/9.
 */
public interface IUserService<T>{

    /**
     * 登录
     * @param user
     * @return
     */
    TUser login(T user);

    /**
     * 注册
     * @return
     */
    boolean register( T user , String openid );



    /**
     * 通过用户ID得到用户
     * @param id
     * @return
     */
    TUser getTUserById(Integer id);

    /**
     * 通过用户id修改密码
     * @param id
     * @return
     */
    boolean updatePwdById(Integer id , String password );

    /**
     * 判断推荐人是否存在
     * @param refferee
     * @return
     */
    boolean reffereeIfExists( String refferee );


    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    boolean userNameIfExists(String username);


    /**
     * 查找推荐信息
     * @param userId
     * @return
     */
    Map findDecttDetail(Integer userId);

}
