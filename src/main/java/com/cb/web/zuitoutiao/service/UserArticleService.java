package com.cb.web.zuitoutiao.service;

/**
 * @Description: 负责对用户阅读资讯方面的操作
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-08-22 9:49
 **/
public interface UserArticleService {
    void insertUserArticle(Integer userId, Integer articleId);
}
