package com.cb.web.zuitoutiao.dao;

import com.cb.web.zuitoutiao.entity.UserRead;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 负责dao层的操作
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-08-24 11:22
 **/
@Repository("UserReadMapper")
public interface UserReadMapper {
    /**
     * @Description: 根据用户id和资讯类型搜索出相应的资讯
     * @Param: [userId, typeName]
     * @return: com.cb.web.entity.UserRead
     * @Author: Chen Ben
     * @Date: 2018/8/24
     */
    @Select({"select id,user_id,type_name,read_times,total_times,likes,dislikes,score" +
            " from user_read where user_id=#{userId,jdbcType=INTEGER}" +
            " and type_name=#{typeName,jdbcType=VARCHAR}"})
    @Results({@Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "type_name", property = "typeName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "read_times", property = "readTimes", jdbcType = JdbcType.DECIMAL),
            @Result(column = "total_times", property = "totalTimes", jdbcType = JdbcType.DECIMAL),
            @Result(column = "likes", property = "likes", jdbcType = JdbcType.DECIMAL),
            @Result(column = "dislikes", property = "dislikes", jdbcType = JdbcType.DECIMAL),
            @Result(column = "score", property = "score", jdbcType = JdbcType.DECIMAL),})
    UserRead queryUserRead(@Param("userId") Integer userId, @Param("typeName") String typeName);

    /**
     * @Description: 添加记录
     * @Param: [userRead]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/24
     */
    @Insert({"insert into user_read (user_id,type_name,read_times,total_times,likes,dislikes,score)",
            "values (#{userId,jdbcType=INTEGER},#{typeName,jdbcType=VARCHAR},#{readTimes,jdbcType=DECIMAL}," +
                    "#{totalTimes,jdbcType=DECIMAL},#{likes,jdbcType=DECIMAL},#{dislikes,jdbcType=DECIMAL}," +
                    "#{score,jdbcType=DECIMAL})"})
    void insertUserReaed(UserRead userRead);

    /**
     * @Description: 更新记录
     * @Param: [article]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/24
     */
    @UpdateProvider(type = UserReadProvider.class, method = "updateUserRead")
    void updateUserRead(JSONObject inputParams);

    /**
    * @Description: 更新总阅读数
    * @Param: [userId, totalTimes]
    * @return: void
    * @Author: Chen Ben
    * @Date: 2018/8/27
    */
    @Update({"update user_read set total_times = #{totalTimes,jdbcType=DECIMAL} where user_id=#{userId,jdbcType=INTEGER}"})
    void updateTotalTimes(@Param("userId") Integer userId, @Param("totalTimes") Double totalTimes);

    /** 
    * @Description: 根据用户id获取所有的数据
    * @Param: [] 
    * @return: java.util.List<com.cb.web.entity.UserRead> 
    * @Author: Chen Ben 
    * @Date: 2018/8/27 
    */ 
    @Select({"select user_id,type_name,read_times,total_times,likes,dislikes,score from user_read where user_id=#{userId,jdbcType=INTEGER}"})
    @Results({@Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "type_name", property = "typeName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "read_times", property = "readTimes", jdbcType = JdbcType.DECIMAL),
            @Result(column = "total_times", property = "totalTimes", jdbcType = JdbcType.DECIMAL),
            @Result(column = "likes", property = "likes", jdbcType = JdbcType.DECIMAL),
            @Result(column = "dislikes", property = "dislikes", jdbcType = JdbcType.DECIMAL),
            @Result(column = "score", property = "score", jdbcType = JdbcType.DECIMAL),})
    List<UserRead> queryUserReadList(Integer userId);
}
