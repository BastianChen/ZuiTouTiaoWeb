package com.cb.web.zuitoutiao.dao;

import com.cb.web.zuitoutiao.entity.HobbyModel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Repository("HobbyModelMapper")
public interface HobbyModelMapper {
    /**
     * @Description: 生成用户兴趣模型
     * @Param: [userRead]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/27
     */
    @Insert({"insert into hobby_model (user_id,hobby1_name,hobby1_rate,hobby2_name,hobby2_rate,hobby3_name,hobby3_rate,hobby4_name," +
            "hobby4_rate,hobby5_name,hobby5_rate,hobby6_name,hobby6_rate)",
            "values (#{userId,jdbcType=INTEGER},#{hobby1Name,jdbcType=VARCHAR},#{hobby1Rate,jdbcType=VARCHAR}," +
                    "#{hobby2Name,jdbcType=VARCHAR},#{hobby2Rate,jdbcType=DECIMAL},#{hobby3Name,jdbcType=VARCHAR}," +
                    "#{hobby3Rate,jdbcType=DECIMAL},#{hobby4Name,jdbcType=VARCHAR},#{hobby4Rate,jdbcType=DECIMAL}," +
                    "#{hobby5Name,jdbcType=VARCHAR},#{hobby5Rate,jdbcType=DECIMAL},#{hobby6Name,jdbcType=VARCHAR}," +
                    "#{hobby6Rate,jdbcType=DECIMAL})"})
    void insertHobbyModel(HobbyModel hobbyModel);

    /**
    * @Description: 根据用户id搜索出用户的兴趣模型
    * @Param: [userId]
    * @return: com.cb.web.entity.HobbyModel
    * @Author: Chen Ben
    * @Date: 2018/8/27
    */
    @Select({"select * from hobby_model where user_id=#{userId,jdbcType=INTEGER}"})
    @Results({@Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),})
    HobbyModel getHobbyModel(Integer userId);

    /** 
    * @Description: 更新用户的兴趣模型 
    * @Param: [hobbyModel] 
    * @return: void 
    * @Author: Chen Ben 
    * @Date: 2018/8/27 
    */ 
    @Update({"update hobby_model set hobby1_name = #{hobby1Name,jdbcType=DECIMAL},hobby1_rate = #{hobby1Rate,jdbcType=DECIMAL}," +
            "hobby1_name = #{hobby1Name,jdbcType=VARCHAR},hobby1_rate = #{hobby1Rate,jdbcType=DECIMAL}," +
            "hobby2_name = #{hobby2Name,jdbcType=VARCHAR},hobby2_rate = #{hobby2Rate,jdbcType=DECIMAL}," +
            "hobby3_name = #{hobby3Name,jdbcType=VARCHAR},hobby3_rate = #{hobby3Rate,jdbcType=DECIMAL}," +
            "hobby4_name = #{hobby4Name,jdbcType=VARCHAR},hobby4_rate = #{hobby4Rate,jdbcType=DECIMAL}," +
            "hobby5_name = #{hobby5Name,jdbcType=VARCHAR},hobby5_rate = #{hobby5Rate,jdbcType=DECIMAL}," +
            "hobby6_name = #{hobby6Name,jdbcType=VARCHAR},hobby6_rate = #{hobby6Rate,jdbcType=DECIMAL}" +
            " where user_id=#{userId,jdbcType=INTEGER}"})
    void updateHobbyModel(HobbyModel hobbyModel);
}