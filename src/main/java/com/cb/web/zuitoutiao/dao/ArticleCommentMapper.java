package com.cb.web.zuitoutiao.dao;

import com.cb.web.zuitoutiao.entity.ArticleComment;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ArticleCommentMapper")
public interface ArticleCommentMapper {
    /** 
    * @Description: 添加评论
    * @Param: [articleComment] 
    * @return: void 
    * @Author: Chen Ben 
    * @Date: 2018/8/20 
    */ 
    @Insert({"insert into article_comment (article_id,user_id,content,create_date,likes,dislikes,user_name)",
            "values (#{articleId,jdbcType=INTEGER},#{userId,jdbcType=INTEGER},#{content,jdbcType=VARCHAR}," +
                    "#{createDate,jdbcType=VARCHAR},#{likes,jdbcType=INTEGER},#{dislikes,jdbcType=INTEGER}," +
                    "#{userName,jdbcType=VARCHAR})"})
    void addComment(ArticleComment articleComment);

    /** 
    * @Description: 根据用户评论时间搜索出评论 
    * @Param: [date] 
    * @return: com.cb.web.entity.ArticleComment 
    * @Author: Chen Ben 
    * @Date: 2018/8/20 
    */ 
    @Select({ "select id,article_id,user_id,content,create_date,likes,dislikes,user_name " +
            "from article_comment where create_date=#{createDate,jdbcType=VARCHAR}" })
    @Results({ @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER),
            @Result(column = "article_id", property = "articleId", jdbcType = JdbcType.INTEGER),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_date", property = "createDate", jdbcType = JdbcType.VARCHAR),
            @Result(column = "likes", property = "likes", jdbcType = JdbcType.INTEGER),
            @Result(column = "dislikes", property = "dislikes", jdbcType = JdbcType.INTEGER),
            @Result(column = "user_name", property = "userName", jdbcType = JdbcType.VARCHAR),})
    ArticleComment getCommentByDate(String createDate);

    /**
    * @Description: 根据资讯id获取资讯的所有父评论
    * @Param: [id]
    * @return: com.cb.web.entity.ArticleComment
    * @Author: Chen Ben
    * @Date: 2018/8/20
    */
    @Select({ "select id,article_id,user_id,content,create_date,likes,dislikes,user_name " +
            "from article_comment where article_id=#{articleId,jdbcType=VARCHAR}" })
    @Results({ @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER),
            @Result(column = "article_id", property = "articleId", jdbcType = JdbcType.INTEGER),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_date", property = "createDate", jdbcType = JdbcType.VARCHAR),
            @Result(column = "likes", property = "likes", jdbcType = JdbcType.INTEGER),
            @Result(column = "dislikes", property = "dislikes", jdbcType = JdbcType.INTEGER),
            @Result(column = "user_name", property = "userName", jdbcType = JdbcType.VARCHAR),})
    List<ArticleComment> getArticleCommentListById(Integer id);

    /**
    * @Description: 根据父评论的id获取相应的父评论
    * @Param: [id]
    * @return: com.cb.web.entity.ArticleComment
    * @Author: Chen Ben
    * @Date: 2018/8/21
    */
    @Select({ "select id,article_id,user_id,content,create_date,likes,dislikes,user_name " +
            "from article_comment where id=#{id,jdbcType=VARCHAR} order by id asc" })
    @Results({ @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER),
            @Result(column = "article_id", property = "articleId", jdbcType = JdbcType.INTEGER),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_date", property = "createDate", jdbcType = JdbcType.VARCHAR),
            @Result(column = "likes", property = "likes", jdbcType = JdbcType.INTEGER),
            @Result(column = "dislikes", property = "dislikes", jdbcType = JdbcType.INTEGER),
            @Result(column = "user_name", property = "userName", jdbcType = JdbcType.VARCHAR),})
    ArticleComment getArticleCommentById(Integer id);

    /**
    * @Description: 根据父评论id来给父评论进行点赞
    * @Param: [id]
    * @return: void
    * @Author: Chen Ben
    * @Date: 2018/8/21
    */
    @Update({ "update article_comment set likes = #{likes,jdbcType=INTEGER} where id=#{id,jdbcType=INTEGER}" })
    void updateLikes(ArticleComment articleComment);

    /**
     * @Description: 根据父评论id来给父评论进行点踩
     * @Param: [id]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/21
     */
    @Update({ "update article_comment set dislikes = #{dislikes,jdbcType=INTEGER} where id=#{id,jdbcType=INTEGER}" })
    void updateDislikes(ArticleComment articleComment);
}