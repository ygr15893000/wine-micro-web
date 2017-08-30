package com.siran.wine.web.user;

import com.siran.common.constant.DefineConstant;
import com.siran.wine.service.impl.QueryTWithdrawScervie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/17.
 */

@Controller
@RequestMapping("/user")
public class TWithdrawController {

    private static final Logger logger = LoggerFactory.getLogger(TWithdrawController.class);

    @Autowired
    private QueryTWithdrawScervie queryTWithdrawScervie;

    @RequestMapping("/getUserTwith")
    @ResponseBody
    public Map selectTwithByUserId(Integer userId){
        Map map = new HashMap();
        map.put(DefineConstant.DATA,queryTWithdrawScervie.queryTwithList(userId));
        return map;
    }

}
