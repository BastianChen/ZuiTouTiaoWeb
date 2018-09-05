package com.cb.web.zuitoutiao.service.impl;

import com.cb.web.zuitoutiao.dao.UserArticleMapper;
import com.cb.web.zuitoutiao.entity.UserArticle;
import com.cb.web.zuitoutiao.service.UserArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 负责对用户阅读资讯方面的操作
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-08-22 10:00
 **/
@Service("UserArticleService")
public class UserArticleServiceImpl implements UserArticleService {
    @Autowired
    private UserArticleMapper userArticleMapper;
    private Logger logger = LoggerFactory.getLogger(UserArticleServiceImpl.class);

    /**
    * @Description: 添加用户的阅读记录
    * @Param: [userId, articleId]
    * @return: void
    * @Author: Chen Ben
    * @Date: 2018/8/22
    */
    @Override
    public void insertUserArticle(Integer userId, Integer articleId) {
        logger.info("查询该用户是否已经阅读过该资讯");
        UserArticle userArticle = userArticleMapper.getUserArticle(userId,articleId);
        Date date = new Date();
        String dateStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
        UserArticle userArticle1 = new UserArticle();
        userArticle1.setUserId(userId);
        userArticle1.setArticleId(articleId);
        userArticle1.setReadDate(dateStr);
        if(userArticle == null){
            logger.info("用户之前并没有阅读过该资讯");
            userArticleMapper.insertUserArticle(userArticle1);
        }else{
            logger.info("用户之前阅读过该资讯");
            userArticleMapper.updateUserArticle(userArticle1);
        }
    }
}
