package com.siran.wine.service.impl;

import com.siran.util.MD5;
import com.siran.wine.dao.impl.*;
import com.siran.wine.model.TDecutDetail;
import com.siran.wine.model.TRecommendUser;
import com.siran.wine.model.TUser;
import com.siran.wine.model.TWeixinUser;
import com.siran.wine.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by 唐正川 on 2017/6/14.
 */

@Service
@Transactional
public class UserServiceImpl implements IUserService {
    private static final Logger  LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;

    @Autowired
    private TRecommendUserDao tRecommendUserDao;

    @Autowired
    private WeixinUserDao weixinUserDao;

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private DecutDetailDao decutDetailDao;

    @Override
    //登录
    public TUser login(Object user) {

        try {
            TUser u = (TUser) user;
            String newPwd = MD5.getMD5Code(u.getPassword());
            u.setPassword(newPwd);
            TUser tUser = userDao.login(u);
            if (tUser != null) {
                return tUser;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        return null;

    }

    @Override
    //注册
    public boolean register(Object user, String openid) {

        boolean flag = false;
        try {
            TUser u = (TUser) user;
            //md5加密
            String newPwd = MD5.getMD5Code(u.getPassword());
            u.setPassword(newPwd);
            if (StringUtils.isBlank(u.getRefferee())) {
                //注册成功之后返回主键
                Integer userId = userDao.save(u);
                //创建微信用户对象，并赋值
                TWeixinUser tWeixinUser = new TWeixinUser();
                tWeixinUser.setUserId(userId);
                tWeixinUser.setOpenid(openid);
                tWeixinUser.setAuthtime(new Date());
                Integer num = weixinUserDao.insertTWeixinUser(tWeixinUser);
                if (num > 0) {
                    flag = true;
                }
            } else {
                //添加用户并返回主键
                Integer userId = userDao.insertObjectAndGetAutoIncreaseId(u);
                //获得用户的联系人id
                TUser reffereeUser = userDao.getIdByUsername(u.getRefferee());
                Integer recommendUserId = reffereeUser.getId();
                //创建一个推荐关系对象
                TRecommendUser tRecommendUser = new TRecommendUser();
                tRecommendUser.setUserId(userId);
                tRecommendUser.setRecommendUserId(recommendUserId);
                Integer num = tRecommendUserDao.insertTRecommendUser(tRecommendUser);

                //创建微信用户对象，并赋值
                TWeixinUser tWeixinUser = new TWeixinUser();
                tWeixinUser.setUserId(userId);
                tWeixinUser.setOpenid(openid);
                tWeixinUser.setAuthtime(new Date());
                Integer nu = weixinUserDao.insertTWeixinUser(tWeixinUser);
                //添加成功，结果大于0，返回true
                if (num > 0 && nu > 0) {
                    flag = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    //根据用户id查询用户信息
    @Override
    public TUser getTUserById(Integer id) {
        TUser tUser = userDao.getTUserById(id);
        Integer recommendUserCount = tRecommendUserDao.getRecommendUserCount(id);
        Integer couponCount = couponDao.getCouponCount(id);
        final Integer countOrder = orderDao.countOrder(new Object[]{id});
        tUser.setRecommendUserCount(recommendUserCount);
        tUser.setCouponCount(couponCount);
        tUser.setOrderCount(countOrder);
        return tUser;
    }


    //根据用户id修改密码
    @Override
    public boolean updatePwdById(Integer id, String password) {
        boolean flag = false;
        try {
            String newPwd = MD5.getMD5Code(password);
            Integer num = userDao.updatePwdById(id, newPwd);
            if (num > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    //判断推荐人是否存在
    @Override
    public boolean reffereeIfExists(String refferee) {
        boolean flag = false;
        try {
            Integer num = userDao.reffereeIfExists(refferee);
            if (num > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean userNameIfExists(String username) {
        boolean flag = false;
        try {
            Integer num = userDao.userNameIfExists(username);
            if (num == 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public Map findDecttDetail(Integer var) {
        Map map = new HashMap();
        List<TDecutDetail> decttDetaillist = decutDetailDao.findAll(new Object[]{var});
        Integer decutAmountSum = decutDetailDao.decutAmountSum(var);
        List<TRecommendUser> recommendUserList = tRecommendUserDao.findAll(new Object[]{var});
        Integer invitecount = tRecommendUserDao.invitecount(var);
        map.put("recommendUserList",recommendUserList);
        map.put("invitecount",invitecount);
        map.put("decttDetaillist",decttDetaillist);
        map.put("decutAmountSum",decutAmountSum);
        return map;
    }
}
