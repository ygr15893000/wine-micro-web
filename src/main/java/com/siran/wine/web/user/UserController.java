package com.siran.wine.web.user;

import com.google.code.kaptcha.Constants;
import com.siran.common.constant.DefineConstant;
import com.siran.common.EnumReturnCode;
import com.siran.wine.model.TUser;
import com.siran.wine.service.IUserService;
import com.siran.wine.service.TRecommendUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 唐正川 on 2017/6/14.
 * 用户逻辑
 */
//解决跨域 方式二 只针对SpringMvc4.2以上
//@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    public static  final  String PHONE_CODE  ="phoneCode";

    @Autowired
    private IUserService userService;

    @Autowired
    private TRecommendUserService tRecommendUserService;


    //用户注册
    @RequestMapping(value="/user/register")
    @ResponseBody
    public Map userRegister(TUser user, String checkCode, String phoneCode ,String openid,
                          HttpSession session,HttpServletResponse response,HttpServletRequest request) {

        Map map = new HashMap();

        //生成随机手机验证码
/*        Random r = new Random();
        String a = String.valueOf(r.nextInt(9000) + 1000  );
        session.setAttribute(PHONE_CODE,a);
        String s=(String) session.getAttribute(PHONE_CODE);*/

        String code = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        logger.info("code = "+ code  );
        logger.info("checkCode = " +checkCode);
        logger.info("openid = "+ openid);
        //判断图片验证码
        if (!checkCode.equalsIgnoreCase(code)) {
            map.put(DefineConstant.CODE, EnumReturnCode.error_100.getCode());
            map.put(DefineConstant.DESC, EnumReturnCode.error_100.getDesc());
            map.put(DefineConstant.DATA, null);
            return map;
        } else if (!phoneCode.equals("7717")) {//判断手机验证码
            map.put(DefineConstant.CODE, EnumReturnCode.error_103.getCode());
            map.put(DefineConstant.DESC, EnumReturnCode.error_103.getDesc());
            map.put(DefineConstant.DATA, null);
            return map;
        } else {
            //判断用户名是否存在
            boolean flag1 = userService.userNameIfExists(user.getUsername());
            if ( flag1 ){
                boolean flag = userService.register(user,openid);
                if (flag) {
                    map.put(DefineConstant.CODE, EnumReturnCode.success_997.getCode());
                    map.put(DefineConstant.DESC, EnumReturnCode.success_997.getDesc());
                    map.put(DefineConstant.DATA, null);
                    return map;
                } else {
                    map.put(DefineConstant.CODE, EnumReturnCode.error_102.getCode());
                    map.put(DefineConstant.DESC, EnumReturnCode.error_102.getDesc());
                    map.put(DefineConstant.DATA, null);
                    return map;
                }
            }else {
                map.put(DefineConstant.CODE, EnumReturnCode.error_106.getCode());
                map.put(DefineConstant.DESC, EnumReturnCode.error_106.getDesc());
                map.put(DefineConstant.DATA, null);
                return map;
            }
        }
    }


  //用户登录
    @RequestMapping(value="/user/login")
    @ResponseBody
    public Map userLogin(TUser user,HttpSession session) {
        Map map = new HashMap();
        TUser tUser = userService.login(user);
        if ( tUser != null ) {
            map.put(DefineConstant.CODE, EnumReturnCode.success_999.getCode());
            map.put(DefineConstant.DESC, EnumReturnCode.success_999.getDesc());
            map.put(DefineConstant.DATA, tUser);
            return map;
        } else {
            map.put(DefineConstant.CODE, EnumReturnCode.error_104.getCode());
            map.put(DefineConstant.DESC, EnumReturnCode.error_104.getDesc());
            map.put(DefineConstant.DATA, null);
            return map;
        }
    }
//通过id得到用户
  @RequestMapping(value = "/user/getUserById")
    @ResponseBody
    public Map getTUserById( Integer id  ){
      Map map = new HashMap();

      TUser user = userService.getTUserById(id);
      logger.info("这是返回的对象信息" + user);
      map.put(DefineConstant.CODE, EnumReturnCode.success_000.getCode());
      map.put(DefineConstant.DESC, EnumReturnCode.success_000.getDesc());
      map.put(DefineConstant.DATA, user);
      return map;
  }

    @RequestMapping(value = "/user/reffereeIfExists")
    @ResponseBody
    public Map reffereeIfExists( String  refferee ){
        Map map = new HashMap();
        boolean flag = userService.reffereeIfExists(refferee);
        map.put(DefineConstant.CODE, null);
        map.put(DefineConstant.DESC, null);
        map.put(DefineConstant.DATA, flag);
        return map;
    }
//返利
    @RequestMapping(value="/user/finddecutdetail")
    @ResponseBody
    public Map finddecutdetail(Integer userId){
        Map map = new HashMap();
         Map decttDetail = userService.findDecttDetail(userId);
        if (decttDetail.isEmpty()){
            map.put(DefineConstant.CODE, EnumReturnCode.error_107.getCode());
            map.put(DefineConstant.DESC,EnumReturnCode.error_107.getDesc());
        }else {
            map.put(DefineConstant.CODE, EnumReturnCode.success_000.getCode());
            map.put(DefineConstant.DESC,EnumReturnCode.success_000.getDesc());
            map.put(DefineConstant.DATA, decttDetail);
        }
        return map;
    }

}
