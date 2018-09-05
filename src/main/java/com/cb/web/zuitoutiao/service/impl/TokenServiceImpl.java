package com.cb.web.zuitoutiao.service.impl;

import com.cb.web.zuitoutiao.dao.TokenMapper;
import com.cb.web.zuitoutiao.entity.Token;
import com.cb.web.zuitoutiao.service.TokenService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-08-30 11:18
 **/
@Service("PersistentLoginsManager")
public class TokenServiceImpl implements TokenService {
    @Autowired
    private TokenMapper tokenMapper;
    @Override
    public Token selectByUsernameAndSeries(String accountName, String series) {
        if(StringUtils.isNotBlank(accountName) && StringUtils.isNotBlank(series)) {
            return tokenMapper.selectByUsernameAndSeries(accountName, series);
        } else {
            return null;
        }
    }

    @Override
    public Token selectByUsername(String accountName) {
        return tokenMapper.selectByAccountName(accountName);
    }

    @Override
    public int insertSelective(Token pLogins) {
        return tokenMapper.insertSelective(pLogins);
    }

    @Override
    public int updateByPrimaryKeySelective(Token pLogins) {
        return tokenMapper.updateByPrimaryKeySelective(pLogins);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return tokenMapper.deleteByPrimaryKey(id);
    }
}
