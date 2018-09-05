package com.cb.web.zuitoutiao.service;

import com.cb.web.zuitoutiao.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author YFZX-CB-1784 ChenBen
 * @create 2018-08-20 10:25
 **/
public interface UserService {
    User regist(User user);

    Map<String, Object> getUser(String accountName, String password, boolean rememberMe, HttpServletResponse response);

    String getCode(String tel);

    User getUserByCode(String tel, String code, String result);

    void updateImage(Integer id, String imageUrl);

    void updateTotalTimes(Integer userId);

    void logout(HttpServletRequest request, HttpServletResponse response);

    User selectByAccountName(String accountName);

    void updateUser(User user);

    void updatePassword(User user);
}
