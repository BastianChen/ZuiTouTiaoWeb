package com.cb.web.zuitoutiao.interceptor;

import com.cb.web.zuitoutiao.controller.RootController;
import com.cb.web.zuitoutiao.entity.Token;
import com.cb.web.zuitoutiao.entity.User;
import com.cb.web.zuitoutiao.service.TokenService;
import com.cb.web.zuitoutiao.service.UserService;
import com.cb.web.zuitoutiao.utils.CookieConstantTable;
import com.cb.web.zuitoutiao.utils.CookieUtils;
import com.cb.web.zuitoutiao.utils.EncryptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * @Description:
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-08-30 10:40
 **/
@Component
public class UserInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(UserInterceptor.class);
    /**
     * @Description: 用于处理自动登录
     * @Param: [request, response, handler]
     * @return: boolean
     * @Author: Chen Ben
     * @Date: 2018/8/31
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        logger.info("拦截器开始拦截");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // 已登录
        if (user != null) {
            return true;
        } else {
            // 从cookie中取值
            Cookie rememberme = CookieUtils.getCookie(request, CookieConstantTable.RememberMe);
            if (rememberme != null) {
                String cookieValue = EncryptionUtil.base64Decode(rememberme.getValue());
                String[] cValues = cookieValue.split(":");
                if (cValues.length == 2) {
                    // 获取用户名
                    String usernameByCookie = cValues[0];
                    // 获取UUID值
                    String uuidByCookie = cValues[1];
                    // 到数据库中查询自动登录记录
                    Token pLogins = tokenService.selectByUsernameAndSeries(usernameByCookie, uuidByCookie);
                    // 数据库中保存的密文
                    if (pLogins != null) {
                        String savedToken = pLogins.getToken();
                        // 获取有效时间
                        Date savedValidtime = pLogins.getValidTime();
                        Timestamp ts = new Timestamp(System.currentTimeMillis());
                        Date currentTime = new Date(ts.getTime());
                        // 如果还在cookie有效期之内，继续判断是否可以自动登录
                        if (currentTime.before(savedValidtime)) {
                            User u = userService.selectByAccountName(usernameByCookie);
                            if (u != null) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(pLogins.getValidTime());
                                // 精确到分的时间字符串
                                String timeString = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH)
                                        + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "-"
                                        + calendar.get(Calendar.HOUR_OF_DAY) + "-" + calendar.get(Calendar.MINUTE);
                                // 为了校验而生成的密文
                                String newToken = EncryptionUtil.sha256Hex(u.getAccountName() + "_" + u.getPassword() + "_"
                                        + timeString + "_" + CookieConstantTable.salt);
                                // 校验sha256加密的值，如果不一样则表示用户部分信息已被修改，需要重新登录
                                if (savedToken.equals(newToken)) {
                                    /**
                                     * 为了提高安全性，每次登录之后都更新自动登录的cookie值
                                     */
                                    // 更新cookie值
                                    String uuidNewString = UUID.randomUUID().toString();
                                    String newCookieValue = EncryptionUtil
                                            .base64Encode(u.getAccountName() + ":" + uuidNewString);
                                    newCookieValue = newCookieValue.replaceAll("\r|\n", "");
                                    CookieUtils.editCookie(request, response, CookieConstantTable.RememberMe,
                                            newCookieValue, null);
                                    // 更新数据库
                                    pLogins.setSeries(uuidNewString);
                                    tokenService.updateByPrimaryKeySelective(pLogins);
                                    /**
                                     * 将用户加到session中，不清空浏览器cookie时就只需判断session即可
                                     */
                                    session.setAttribute("user", u);
                                    //校验成功，此次拦截操作完成
                                    return true;
                                } else {
                                    // 用户部分信息被修改，删除cookie并清空数据库中的记录
                                    CookieUtils.delCookie(response, rememberme);
                                    tokenService.deleteByPrimaryKey(pLogins.getId());
                                }
                            }
                        } else {
                            // 超过保存的有效期，删除cookie并清空数据库中的记录
                            CookieUtils.delCookie(response, rememberme);
                            tokenService.deleteByPrimaryKey(pLogins.getId());
                        }
                    }
                }
            }
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}