package com.cb.web.zuitoutiao.service;

import com.cb.web.zuitoutiao.entity.Token;

/**
 * @Description:
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-08-30 11:18
 **/
public interface TokenService {
    /**
     * 通过用户名和UUID值查询自动登录记录
     *
     * @param username
     *            用户名
     * @param series
     *            UUID值
     */
    Token selectByUsernameAndSeries(String username, String series);
    /**
     * 通过用户名查询自动登录记录
     *
     * @param username
     *            用户名
     */
    Token selectByUsername(String username);

    int insertSelective(Token pLogins);

    public int updateByPrimaryKeySelective(Token pLogins);

    public int deleteByPrimaryKey(Integer id);
}
