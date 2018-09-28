package com.cb.web.zuitoutiao.service.impl;

import com.cb.web.zuitoutiao.dao.UserMapper;
import com.cb.web.zuitoutiao.entity.Token;
import com.cb.web.zuitoutiao.entity.User;
import com.cb.web.zuitoutiao.service.TokenService;
import com.cb.web.zuitoutiao.service.UserService;
import com.cb.web.zuitoutiao.utils.CookieConstantTable;
import com.cb.web.zuitoutiao.utils.CookieUtils;
import com.cb.web.zuitoutiao.utils.EncryptionUtil;
import com.cb.web.zuitoutiao.utils.GetMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author YFZX-CB-1784 ChenBen
 * @create 2018-08-20 10:26
 **/
@Service("UserService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TokenService tokenService;
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * @Description: 注册
     * @Param: [user]
     * @return: com.cb.web.entity.User
     * @Author: Chen Ben
     * @Date: 2018/8/20
     */
    @Override
    public User regist(User user) {
        user.setPassword(EncryptionUtil.md5Hex(user.getAccountName() + user.getPassword()));
        user.setImage("/images/default.jpg");
        user.setType("0");
        user.setTotalTimes(0.0);
        userMapper.regist(user);
        User user1 = userMapper.selectByAccountName(user.getAccountName());
        return user1;
    }

    /**
     * @Description: 登陆
     * @Param: [accountName, password]
     * @return: com.cb.web.entity.User
     * @Author: Chen Ben
     * @Date: 2018/8/20
     */
    @Override
    public Map<String, Object> getUser(String accountName, String password, boolean rememberMe, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        User user1 = userMapper.selectByAccountName(accountName);
        if (user1 == null) {
            logger.info("不存在该用户");
            resultMap.put("user", "不存在该用户");
            return resultMap;
        } else {
            password = EncryptionUtil.md5Hex(accountName + password);
            User user = userMapper.getUser(accountName, password);
            if (user != null && rememberMe == true) {
                logger.info("登录成功");
                // 有效期
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MONTH, 1);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String dateStr = sdf.format(calendar.getTime());
                Timestamp validTime = Timestamp.valueOf(dateStr);
                // 精确到分的时间字符串
                String timeString = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-"
                        + calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.HOUR_OF_DAY) + "-"
                        + calendar.get(Calendar.MINUTE);
                // sha256加密用户信息
                String userInfoBySha256 = EncryptionUtil
                        .sha256Hex(user.getAccountName() + "_" + user.getPassword() + "_" + timeString + "_" + CookieConstantTable.salt);
                // UUID值
                String uuidString = UUID.randomUUID().toString();
                // Cookie值
                String cookieValue = EncryptionUtil.base64Encode(user.getAccountName() + ":" + uuidString);
                cookieValue = cookieValue.replaceAll("\r|\n", "");
                // 在数据库中保存自动登录记录（如果已有该用户的记录则更新记录）
                Token token = tokenService.selectByUsername(user.getAccountName());
                if (token == null) {
                    token = new Token();
                    token.setAccountName(user.getAccountName());
                    token.setSeries(uuidString);
                    token.setToken(userInfoBySha256);
                    token.setValidTime(validTime);
                    tokenService.insertSelective(token);
                } else {
                    token.setSeries(uuidString);
                    token.setToken(userInfoBySha256);
                    token.setValidTime(validTime);
                    tokenService.updateByPrimaryKeySelective(token);
                }
                // 保存cookie
                CookieUtils.addCookie(response, CookieConstantTable.RememberMe, cookieValue, null);
                resultMap.put("user", user);
                return resultMap;
            } else if (user != null && rememberMe == false) {
                logger.info("登录成功");
                resultMap.put("user", user);
                return resultMap;
            } else {
                logger.info("密码错误");
                resultMap.put("user", "密码错误");
                return resultMap;
            }
        }
    }

    /**
     * @Description: 获取手机验证码
     * @Param: [tel]
     * @return: java.lang.String
     * @Author: Chen Ben
     * @Date: 2018/8/21
     */
    @Override
    public String getCode(String tel) {
        String code = GetMessage.getResult(tel);
        return code;
    }

    /**
     * @Description: 根据手机验证码登陆
     * @Param: [tel, code]
     * @return: com.cb.web.entity.User
     * @Author: Chen Ben
     * @Date: 2018/8/21
     */
    @Override
    public User getUserByCode(String tel, String code, String result) {
        if (code.equals(result)) {
            logger.info("根据手机验证码验证成功");
            User user = userMapper.getUserByTel(tel);
            return user;
        } else {
            logger.info("根据手机验证码验证失败");
            return null;
        }
    }

    /**
     * @Description: 上传用户新头像
     * @Param: [id, imageUrl]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/24
     */
    @Override
    public void updateImage(Integer id, String imageUrl) {
        userMapper.updateImage(id, imageUrl);
    }

    /**
     * @Description: 更新用户总阅读数
     * @Param: [userId]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/24
     */
    @Override
    public void updateTotalTimes(Integer userId) {
        User user = userMapper.getUserById(userId);
        user.setTotalTimes(user.getTotalTimes() + 1);
        userMapper.updateTotalTimes(user);
    }

    /**
     * @Description: 注销
     * @Param: [request, response]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/31
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        //从session中获取用户详情
        User user = (User) request.getSession().getAttribute("user");
        //删除数据库中的自动登录记录
        Token pLogins = tokenService.selectByUsername(user.getAccountName());
        if (pLogins != null) {
            tokenService.deleteByPrimaryKey(pLogins.getId());
        }
        //清除session和用于自动登录的cookie
        request.getSession().removeAttribute("user");
        CookieUtils.delCookie(request, response, CookieConstantTable.RememberMe);
        try {
            response.sendRedirect(request.getContextPath() + "/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 根据accountName搜索用户
     * @Param: [accountName]
     * @return: com.cb.web.entity.User
     * @Author: Chen Ben
     * @Date: 2018/9/3
     */
    @Override
    public User selectByAccountName(String accountName) {
        return userMapper.selectByAccountName(accountName);
    }

    /**
     * @Description: 根据用户id更改用户个人信息
     * @Param: [user]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/9/3
     */
    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    /**
     * @Description: 修改用户密码
     * @Param: [user]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/9/3
     */
    @Override
    public void updatePassword(User user) {
        user.setPassword(EncryptionUtil.md5Hex(user.getAccountName() + user.getPassword()));
        userMapper.updatePassword(user);
    }

    /**
     * @Description: 获取用户的总阅读数
     * @Param: [userId]
     * @return: java.lang.Integer
     * @Author: Chen Ben
     * @Date: 2018/9/27
     */
    @Override
    public Integer getUserTotalTimes(Integer userId) {
        Integer totalTimes = userMapper.getTotalTimes(userId);
        return totalTimes;
    }

    @Override
    public User queryUserById(Integer userId) {
        User user = userMapper.queryUserById(userId);
        return user;
    }
}
