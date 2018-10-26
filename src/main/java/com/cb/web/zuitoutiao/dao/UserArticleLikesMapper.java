package com.cb.web.zuitoutiao.dao;

import com.cb.web.zuitoutiao.entity.UserArticleLikes;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

/**
 * @Description: 实现对表user_article_likes的操作
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-09-27 11:25
 **/
@Repository("UserArticleLikesMapper")
public interface UserArticleLikesMapper {

    /**
     * @Description: 添加用户点赞记录
     * @Param: [userArticleLikes]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/9/27
     */
    @Insert({"insert into user_article_likes(user_id,article_id,type) values (#{userId,jdbcType=INTEGER},#{articleId,jdbcType=INTEGER},#{type,jdbcType=INTEGER})"})
    void insertUserArticleLikes(@Param("userId") Integer userId, @Param("articleId") Integer articleId,@Param("type") String type);

    /**
     * @Description: 删除用户点赞记录
     * @Param: [userArticleLikes]
     * @return: int
     * @Author: Chen Ben
     * @Date: 2018/9/27
     */
    @Delete({"delete from user_article_likes where user_id = #{userId,jdbcType=INTEGER} and article_id = #{articleId,jdbcType=INTEGER} and type = #{type,jdbcType=VARCHAR}"})
    int deleteUserArticleLikes(@Param("userId") Integer userId, @Param("articleId") Integer articleId,@Param("type") String type);

    /**
     * @Description: 查询用户点赞记录
     * @Param: [userId, articleId]
     * @return: com.cb.web.zuitoutiao.entity.UserArticle
     * @Author: Chen Ben
     * @Date: 2018/9/27
     */
    @Select({"select * from user_article_likes where user_id = #{userId,jdbcType=VARCHAR} and article_id = #{articleId,jdbcType=VARCHAR}" +
            " and type = #{type,jdbcType=VARCHAR}"})
    @Results({@Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "article_id", property = "articleId", jdbcType = JdbcType.INTEGER),})
    UserArticleLikes getUserArticleLikes(@Param("userId") Integer userId, @Param("articleId") Integer articleId,@Param("type") String type);
}
