package com.cb.web.zuitoutiao.dao;

import com.cb.web.zuitoutiao.entity.UserCommentLikes;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

/**
 * @Description: 实现对表user_comment_likes的操作
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-09-27 14:01
 **/
@Repository("UserCommentLikesMapper")
public interface UserCommentLikesMapper {

    @Insert({"insert into user_comment_likes(user_id,comment_id,type) values (#{userId,jdbcType=INTEGER},#{commentId,jdbcType=INTEGER},#{type,jdbcType=VARCHAR})"})
    void insertUserCommentLikes(@Param("userId") Integer userId, @Param("commentId") Integer commentId,@Param("type") String type);

    @Delete({"delete from user_comment_likes where id = #{id,jdbcType=INTEGER}"})
    int deleteUserCommentLikes(Integer id);

    @Select({"select * from user_comment_likes where user_id = #{userId,jdbcType=INTEGER} and comment_id = #{commentId,jdbcType=INTEGER} and type = #{type,jdbcType=VARCHAR}"})
    @Results({@Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "comment_id", property = "commentId", jdbcType = JdbcType.INTEGER),
            @Result(column = "type", property = "type", jdbcType = JdbcType.VARCHAR)})
    UserCommentLikes getUserCommentLikes(@Param("userId") Integer userId, @Param("commentId") Integer commentId,@Param("type") String type);
}
