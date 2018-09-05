package com.cb.web.zuitoutiao.dao;

import com.cb.web.zuitoutiao.entity.UserArticle;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Repository("UserArticleMapper")
public interface UserArticleMapper {
    /**
    * @Description: 添加用户的阅读记录
    * @Param: [user]
    * @return: void
    * @Author: Chen Ben
    * @Date: 2018/8/22
    */
    @Insert({ "insert into user_article (user_id,article_id,read_date)",
            "values (#{userId,jdbcType=INTEGER},#{articleId,jdbcType=INTEGER},#{readDate,jdbcType=VARCHAR})" })
    void insertUserArticle(UserArticle userArticle);

    /**
    * @Description: 根据用户id和资讯id搜索相应的资讯
    * @Param: [userId, articleId]
    * @return: com.cb.web.entity.UserArticle
    * @Author: Chen Ben
    * @Date: 2018/8/22
    */
    @Select({"select * from user_article where user_id = #{userId,jdbcType=VARCHAR} and article_id = #{articleId,jdbcType=VARCHAR}"})
    @Results({ @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "article_id", property = "articleId", jdbcType = JdbcType.INTEGER),
            @Result(column = "read_date", property = "readDate", jdbcType = JdbcType.VARCHAR),})
    UserArticle getUserArticle(@Param("userId") Integer userId, @Param("articleId") Integer articleId);

    /** 
    * @Description: 更新用户的阅读时间
    * @Param: [userArticle] 
    * @return: void 
    * @Author: Chen Ben 
    * @Date: 2018/8/22 
    */ 
    @Update({ "update user_article set read_date = #{readDate,jdbcType=INTEGER} where user_id = #{userId,jdbcType=VARCHAR} and article_id = #{articleId,jdbcType=VARCHAR}" })
    void updateUserArticle(UserArticle userArticle);
}