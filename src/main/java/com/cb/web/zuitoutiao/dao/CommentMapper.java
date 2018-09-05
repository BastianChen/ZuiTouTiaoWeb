package com.cb.web.zuitoutiao.dao;

import com.cb.web.zuitoutiao.entity.Comment;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("CommentMapper")
public interface CommentMapper {
    /**
     * @Description: 添加子评论
     * @Param: [comment]
     * @return: com.cb.web.entity.Comment
     * @Author: Chen Ben
     * @Date: 2018/8/20
     */
    @Insert({"insert into comment (comment_id,user_id,follower_id,content,likes,dislikes,user_name,follower_name,type,create_date)",
            "values (#{commentId,jdbcType=INTEGER},#{userId,jdbcType=INTEGER},#{followerId,jdbcType=INTEGER}," +
                    "#{content,jdbcType=VARCHAR},#{likes,jdbcType=INTEGER},#{dislikes,jdbcType=INTEGER}," +
                    "#{userName,jdbcType=VARCHAR},#{followerName,jdbcType=VARCHAR},#{type,jdbcType=VARCHAR}" +
                    ",#{createDate,jdbcType=VARCHAR})"})
    void addSubreview(Comment comment);

    /**
    * @Description: 根据父评论的id获取其所有子评论
    * @Param: [id]
    * @return: java.util.List<com.cb.web.entity.Comment>
    * @Author: Chen Ben
    * @Date: 2018/8/21
    */
    @Select({ "select id,comment_id,user_id,follower_id,content,likes,dislikes,user_name,follower_name,type,create_date " +
            "from comment where comment_id=#{commentId,jdbcType=VARCHAR} order by id asc" })
    @Results({ @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER),
            @Result(column = "comment_id", property = "commentId", jdbcType = JdbcType.INTEGER),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "follower_id", property = "followerId", jdbcType = JdbcType.INTEGER),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "likes", property = "likes", jdbcType = JdbcType.INTEGER),
            @Result(column = "dislikes", property = "dislikes", jdbcType = JdbcType.INTEGER),
            @Result(column = "user_name", property = "userName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "follower_name", property = "followerName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type", property = "type", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_date", property = "createDate", jdbcType = JdbcType.VARCHAR),})
    List<Comment> getCommentListById(Integer id);

    /**
    * @Description: 根据子评论的id获取相应的子评论
    * @Param: [id]
    * @return: com.cb.web.entity.Comment
    * @Author: Chen Ben
    * @Date: 2018/8/21
    */
    @Select({ "select id,comment_id,user_id,follower_id,content,likes,dislikes,user_name,follower_name,type,create_date " +
            "from comment where id=#{id,jdbcType=VARCHAR} order by id asc" })
    @Results({ @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER),
            @Result(column = "comment_id", property = "commentId", jdbcType = JdbcType.INTEGER),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "follower_id", property = "followerId", jdbcType = JdbcType.INTEGER),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "likes", property = "likes", jdbcType = JdbcType.INTEGER),
            @Result(column = "dislikes", property = "dislikes", jdbcType = JdbcType.INTEGER),
            @Result(column = "user_name", property = "userName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "follower_name", property = "followerName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type", property = "type", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_date", property = "createDate", jdbcType = JdbcType.VARCHAR),})
    Comment getConmmentById(Integer id);

    /**
     * @Description: 根据子评论id来给子评论进行点赞
     * @Param: [id]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/21
     */
    @Update({ "update comment set likes = #{likes,jdbcType=INTEGER} where id=#{id,jdbcType=INTEGER}" })
    void updateLikes(Comment comment);

    /**
     * @Description: 根据子评论id来给子评论进行点踩
     * @Param: [id]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/21
     */
    @Update({ "update comment set dislikes = #{dislikes,jdbcType=INTEGER} where id=#{id,jdbcType=INTEGER}" })
    void updateDislikes(Comment comment);
}