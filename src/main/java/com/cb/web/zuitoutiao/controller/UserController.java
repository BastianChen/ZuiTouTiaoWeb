package com.cb.web.zuitoutiao.controller;

import com.cb.web.zuitoutiao.entity.User;
import com.cb.web.zuitoutiao.service.UserService;
import com.cb.web.zuitoutiao.utils.CaptchaUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-09-04 15:58
 **/
@RestController
@Api(value = "User",description = "用户接口")
@RequestMapping("/User")
public class UserController {
    @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private String phoneCode;
    private String code;

    /**
     * @Description: 注册
     * @Param: [user]
     * @return: java.util.Map<java.lang.String , java.lang.Object>
     * @Author: Chen Ben
     * @Date: 2018/8/20
     */
    @RequestMapping(value = "/regist", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "注册成功"), @ApiResponse(code = 400, message = "接口异常"),})
    @ResponseBody
    @ApiOperation(value="注册接口", notes="注册")
    public Map<String, Object> regist(@ApiParam(name="accountName", value = "用户" ,required=true ) @RequestBody User user) {
        logger.info("注册");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        User user1 = userService.regist(user);
        resultMap.put("user", user1);
        return resultMap;
    }

    /**
     * @Description: 登陆
     * @Param: [accountName, password]
     * @return: java.util.Map<java.lang.String , java.lang.Object>
     * @Author: Chen Ben
     * @Date: 2018/8/20
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value="登陆接口", notes="登陆")
    public Map<String, Object> login(@ApiParam(required=true, name="accountName", value="账号")
                                     @RequestParam(value = "accountName", required = true) String accountName,
                                     @ApiParam(required=true, name="password", value="密码")
                                     @RequestParam(value = "password", required = true) String password,
                                     @ApiParam(required=true, name="captcha", value="验证码")
                                     @RequestParam(value = "captcha", required = true) String captcha,
                                     @ApiParam(required=false, name="rememberMe", value="30天内是否自动登录")
                                     @RequestParam(name = "rememberMe", required = false) boolean rememberMe,
                                     HttpServletResponse response, HttpServletRequest request) {
        logger.info("登陆");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (captcha.equals(code)) {
            logger.info("验证码正确");
            resultMap = userService.getUser(accountName, password, rememberMe, response);
            request.getSession().setAttribute("user", resultMap.get("user"));
            return resultMap;
        } else {
            logger.info("验证码错误");
            resultMap.put("user", "验证码错误");
            return resultMap;
        }
    }

    /**
     * @Description: 获取手机验证码
     * @Param: [accountName, password]
     * @return: java.util.Map<java.lang.String , java.lang.Object>
     * @Author: Chen Ben
     * @Date: 2018/8/20
     */
    @RequestMapping(value = "/getCode", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    @ApiOperation(value="获取手机验证码接口", notes="获取手机验证码")
    public void getCode(@ApiParam(required=true, name="tel", value="手机号")
                        @RequestParam(value = "tel", required = true) String tel) {
        logger.info("获取手机验证码");
        phoneCode = userService.getCode(tel);
    }

    /**
     * @Description: 根据手机验证码登陆
     * @Param: [accountName, password]
     * @return: java.util.Map<java.lang.String , java.lang.Object>
     * @Author: Chen Ben
     * @Date: 2018/8/20
     */
    @RequestMapping(value = "/loginByCode", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value="根据手机验证码登陆接口", notes="根据手机验证码登陆")
    public Map<String, Object> loginByCode(@ApiParam(required=true, name="tel", value="手机号")
                                           @RequestParam(value = "tel", required = true) String tel,
                                           @ApiParam(required=true, name="code", value="手机验证码")
                                           @RequestParam(value = "code", required = true) String code,
                                           HttpServletRequest request) {
        logger.info("根据手机验证码登陆");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        User user = userService.getUserByCode(tel, code, phoneCode);
        resultMap.put("user", user);
        request.getSession().setAttribute("user", resultMap.get("user"));
        return resultMap;
    }

    /**
     * @Description: 获取用户用账号密码登陆时的验证码
     * @Param: [response, rad]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/22
     */
    @ApiIgnore
    @RequestMapping(value = "/getCaptcha", method = RequestMethod.GET)
    public void getCaptcha(HttpServletResponse response,String rad) throws IOException {
        logger.info("获取登陆验证码");
        CaptchaUtil captchaUtil = new CaptchaUtil();
        code = captchaUtil.getCode3(response);
        logger.info("验证码为："+code);
    }

    /**
     * @Description: 注销
     * @Param: [request, response]
     * @return: org.springframework.web.servlet.ModelAndView
     * @Author: Chen Ben
     * @Date: 2018/8/31
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ApiOperation(value="注销接口", notes="注销")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        logger.info("注销成功");
        userService.logout(request, response);
    }

    /**
     * @Description: 修改用户个人信息
     * @Param: [user]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/9/3
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value="修改用户个人信息接口", notes="修改用户个人信息(只用填用户id、user_name、age以及gender)")
    public void updateInfo(@ApiParam(required=true, name="user", value="用户") @RequestBody User user) {
        userService.updateUser(user);
    }

    /**
     * @Description: 验证手机验证码
     * @Param: [tel, code]
     * @return: boolean
     * @Author: Chen Ben
     * @Date: 2018/9/3
     */
    @RequestMapping(value = "/verifyPhoneCode", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value="验证手机验证码接口", notes="验证手机验证码并跳转到修改密码界面")
    public Map<String, Object> verifyPhoneCode(@ApiParam(required=true, name="tel", value="手机号")
                                   @RequestParam(value = "tel", required = true) String tel,
                                   @ApiParam(required=true, name="code", value="手机验证码")
                                   @RequestParam(value = "code", required = true) String code) {
        logger.info("验证手机验证码并修改密码");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        User user = userService.getUserByCode(tel, code, phoneCode);
        if (user != null) {
            resultMap.put("verifyPhoneCode", "true");
            return resultMap;
        } else {
            resultMap.put("verifyPhoneCode", "false");
            return resultMap;
        }
    }

    /**
     * @Description: 修改用户密码
     * @Param: [user]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/9/3
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value="修改用户密码接口", notes="修改用户密码(只用填用户id、用户新密码以及用户账号)")
    public void updatePassword(@ApiParam(required=true, name="user", value="用户") @RequestBody User user) {
        userService.updatePassword(user);
    }
}
