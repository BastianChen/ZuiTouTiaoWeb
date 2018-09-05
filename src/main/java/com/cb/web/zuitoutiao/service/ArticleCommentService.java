package com.cb.web.zuitoutiao.service;

import com.cb.web.zuitoutiao.entity.ArticleComment;
import com.cb.web.zuitoutiao.entity.Comment;

import java.util.List;

/**
 * @Description: 负责用户对资讯评论方面的相关操作
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-08-20 14:32
 **/
public interface ArticleCommentService {
    void addComment(ArticleComment articleComment);

    List<ArticleComment> getArticleCommentListById(Integer id);

    List<Comment> getCommentListById(Integer id);

    void addSubreview(Comment comment);

    void updateLikes(Integer id, Integer type);

    void updateDislikes(Integer id, Integer type);
}
