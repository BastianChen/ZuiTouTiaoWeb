package com.cb.web.zuitoutiao.service.impl;

import com.cb.web.zuitoutiao.dao.UserMapper;
import com.cb.web.zuitoutiao.entity.User;
import com.cb.web.zuitoutiao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-09-04 16:32
 **/
@Service("UserService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUser(String accountName) {
        return userMapper.selectByAccountName(accountName);
    }
}
