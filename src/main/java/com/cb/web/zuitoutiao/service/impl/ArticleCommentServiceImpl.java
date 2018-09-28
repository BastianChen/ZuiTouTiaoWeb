package com.cb.web.zuitoutiao.service.impl;

import com.cb.web.zuitoutiao.dao.ArticleCommentMapper;
import com.cb.web.zuitoutiao.dao.CommentMapper;
import com.cb.web.zuitoutiao.dao.UserCommentLikesMapper;
import com.cb.web.zuitoutiao.entity.ArticleComment;
import com.cb.web.zuitoutiao.entity.Comment;
import com.cb.web.zuitoutiao.entity.UserCommentLikes;
import com.cb.web.zuitoutiao.service.ArticleCommentService;
import com.cb.web.zuitoutiao.utils.UrlPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Description: 负责用户对资讯评论方面的相关操作
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-08-20 14:33
 **/
@Service("ArticleCommentService")
public class ArticleCommentServiceImpl implements ArticleCommentService {
    @Autowired
    private ArticleCommentMapper articleCommentMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserCommentLikesMapper userCommentLikesMapper;
    private Logger logger = LoggerFactory.getLogger(ArticleCommentServiceImpl.class);

    /**
     * @Description: 添加评论
     * @Param: [articleComment]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/20
     */
    @Override
    public void addComment(ArticleComment articleComment) {
        Date date = new Date();
        String dateStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
        articleComment.setCreateDate(dateStr);
        articleCommentMapper.addComment(articleComment);
        logger.info("评论资讯成功");
    }

    /**
     * @Description: 根据资讯id获取资讯的所有父评论
     * @Param: [id]
     * @return: com.cb.web.entity.ArticleComment
     * @Author: Chen Ben
     * @Date: 2018/8/20
     */
    @Override
    public List<ArticleComment> getArticleCommentListById(Integer id) {
        List<ArticleComment> articleCommentList = articleCommentMapper.getArticleCommentListById(id);
        return articleCommentList;
    }

    /**
     * @Description: 添加子评论
     * @Param: [comment]
     * @return: com.cb.web.entity.ArticleComment
     * @Author: Chen Ben
     * @Date: 2018/8/20
     */
    @Override
    public void addSubreview(Comment comment) {
        comment.setLikes(0);
        comment.setDislikes(0);
        Date date = new Date();
        String dateStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
        comment.setCreateDate(dateStr);
        commentMapper.addSubreview(comment);
    }

    /**
     * @Description: 根据评论的id获取其子评论
     * @Param: [id]
     * @return: java.util.List<com.cb.web.entity.Comment>
     * @Author: Chen Ben
     * @Date: 2018/8/21
     */
    @Override
    public List<Comment> getCommentListById(Integer id) {
        List<Comment> commentList = commentMapper.getCommentListById(id);
        return commentList;
    }

    /**
     * @Description: 根据评论的id以及type属性的值来辨别是给父评论进行点赞还是给子评论进行点赞
     * @Param: [id, type]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/21
     */
    @Override
    public Integer updateLikes(Integer userId, Integer commentId, String type) {
        if (type == null) {
            type = "3";
        }
        UserCommentLikes userCommentLikes = userCommentLikesMapper.getUserCommentLikes(userId, commentId, type);
        if (type.equals("3")) {
            ArticleComment articleComment = articleCommentMapper.getArticleCommentById(commentId);
            if (userCommentLikes == null) {
                logger.info("给父评论点赞成功");
                articleComment.setLikes(articleComment.getLikes() + UrlPath.number);
                userCommentLikesMapper.insertUserCommentLikes(userId, commentId, type);
            } else {
                logger.info("给父评论撤销点赞成功");
                articleComment.setLikes(articleComment.getLikes() - UrlPath.number);
                userCommentLikesMapper.deleteUserCommentLikes(userCommentLikes.getId());
            }
            articleCommentMapper.updateLikes(articleComment);
            return articleComment.getLikes();
        } else {
            Comment comment = commentMapper.getConmmentById(commentId);
            if (userCommentLikes == null) {
                logger.info("给子评论点赞成功");
                comment.setLikes(comment.getLikes() + UrlPath.number);
                userCommentLikesMapper.insertUserCommentLikes(userId, commentId, type);
            } else {
                logger.info("给子评论撤销点赞成功");
                comment.setLikes(comment.getLikes() - UrlPath.number);
                userCommentLikesMapper.deleteUserCommentLikes(userCommentLikes.getId());
            }
            commentMapper.updateLikes(comment);
            return comment.getLikes();
        }
    }

    /**
     * @Description: 根据评论的id以及type属性的值来辨别是给父评论进行点踩还是给子评论进行点踩
     * @Param: [id, type]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/21
     */
    @Override
    public Integer updateDislikes(Integer id, Integer type) {
        if (type == null) {
            logger.info("给父评论点踩成功");
            ArticleComment articleComment = articleCommentMapper.getArticleCommentById(id);
            articleComment.setDislikes(articleComment.getDislikes() + UrlPath.number);
            articleCommentMapper.updateDislikes(articleComment);
            return articleComment.getDislikes() + UrlPath.number;
        } else {
            logger.info("给子评论点踩成功");
            Comment comment = commentMapper.getConmmentById(id);
            comment.setDislikes(comment.getDislikes() + UrlPath.number);
            commentMapper.updateDislikes(comment);
            return comment.getDislikes();
        }
    }
}
