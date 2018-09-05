package com.cb.web.zuitoutiao.dao;

import com.cb.web.zuitoutiao.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-09-04 16:29
 **/
@Repository("UserMapper")
public interface UserMapper {
    /**
     * @Description: 向数据库中插入用户数据
     * @Param: [user]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/20
     */
    @Insert({"insert into user (account_name,password,user_name,age,gender,tel,email,type,image,total_times)",
            "values (#{accountName,jdbcType=VARCHAR},#{password,jdbcType=VARCHAR},#{userName,jdbcType=VARCHAR}," +
                    "#{age,jdbcType=VARCHAR},#{gender,jdbcType=VARCHAR},#{tel,jdbcType=VARCHAR}," +
                    "#{email,jdbcType=VARCHAR},#{type,jdbcType=VARCHAR},#{image,jdbcType=VARCHAR}," +
                    "#{totalTimes,jdbcType=VARCHAR})"})
    void regist(User user);

    /**
     * @Description: 根据登录名获取用户信息
     * @Param: [user]
     * @return: com.cb.web.entity.User
     * @Author: Chen Ben
     * @Date: 2018/8/20
     */
    @Select({"select * from user where account_name = #{accountName,jdbcType=VARCHAR}"})
    @Results({@Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER),
            @Result(column = "account_name", property = "accountName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
            @Result(column = "user_name", property = "userName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "age", property = "age", jdbcType = JdbcType.VARCHAR),
            @Result(column = "gender", property = "gender", jdbcType = JdbcType.VARCHAR),
            @Result(column = "tel", property = "tel", jdbcType = JdbcType.VARCHAR),
            @Result(column = "email", property = "email", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type", property = "type", jdbcType = JdbcType.VARCHAR),
            @Result(column = "image", property = "image", jdbcType = JdbcType.VARCHAR),
            @Result(column = "total_times", property = "totalTimes", jdbcType = JdbcType.VARCHAR),})
    User selectByAccountName(@Param("accountName") String accountName);

    /**
     * @Description: 根据用户账号和密码获取用户信息
     * @Param: [accountName, password]
     * @return: com.cb.web.entity.User
     * @Author: Chen Ben
     * @Date: 2018/8/20
     */
    @Select({"select * from user where account_name = #{accountName,jdbcType=VARCHAR} and password = #{password,jdbcType=VARCHAR}"})
    @Results({@Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER),
            @Result(column = "account_name", property = "accountName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
            @Result(column = "user_name", property = "userName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "age", property = "age", jdbcType = JdbcType.VARCHAR),
            @Result(column = "gender", property = "gender", jdbcType = JdbcType.VARCHAR),
            @Result(column = "tel", property = "tel", jdbcType = JdbcType.VARCHAR),
            @Result(column = "email", property = "email", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type", property = "type", jdbcType = JdbcType.VARCHAR),
            @Result(column = "image", property = "image", jdbcType = JdbcType.VARCHAR),
            @Result(column = "total_times", property = "totalTimes", jdbcType = JdbcType.VARCHAR),})
    User getUser(@Param("accountName") String accountName, @Param("password") String password);

    /**
     * @Description: 根据手机验证码登陆
     * @Param: [accountName, password]
     * @return: com.cb.web.entity.User
     * @Author: Chen Ben
     * @Date: 2018/8/20
     */
    @Select({"select * from user where tel = #{tel,jdbcType=VARCHAR}"})
    @Results({@Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER),
            @Result(column = "account_name", property = "accountName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
            @Result(column = "user_name", property = "userName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "age", property = "age", jdbcType = JdbcType.VARCHAR),
            @Result(column = "gender", property = "gender", jdbcType = JdbcType.VARCHAR),
            @Result(column = "tel", property = "tel", jdbcType = JdbcType.VARCHAR),
            @Result(column = "email", property = "email", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type", property = "type", jdbcType = JdbcType.VARCHAR),
            @Result(column = "image", property = "image", jdbcType = JdbcType.VARCHAR),
            @Result(column = "total_times", property = "totalTimes", jdbcType = JdbcType.VARCHAR),})
    User getUserByTel(String tel);

    /**
     * @Description: 根据用户id和image的url来更改用户头像
     * @Param: [id, image]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/24
     */
    @Update({"update user set image = #{image,jdbcType=VARCHAR} where id=#{id,jdbcType=INTEGER}"})
    void updateImage(@Param("id") Integer id, @Param("image") String image);

    /**
     * @Description: 根据用户id获取阅读总数
     * @Param: [userId]
     * @return: com.cb.web.entity.User
     * @Author: Chen Ben
     * @Date: 2018/8/24
     */
    @Select({"select id,total_times from user where id = #{userId,jdbcType=VARCHAR}"})
    @Results({@Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER),
            @Result(column = "total_times", property = "totalTimes", jdbcType = JdbcType.VARCHAR),})
    User getUserById(Integer userId);

    /**
     * @Description: 根据用户id更新用户的阅读总数
     * @Param: [id, image]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/24
     */
    @Update({"update user set total_times = #{totalTimes,jdbcType=VARCHAR} where id=#{id,jdbcType=INTEGER}"})
    void updateTotalTimes(User user);

    /**
     * @Description: 根据用户id更改用户个人信息
     * @Param: [user]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/9/3
     */
    @Update({"update user set user_name = #{userName,jdbcType=VARCHAR},age = #{age,jdbcType=INTEGER}," +
            "gender = #{gender,jdbcType=VARCHAR} where id=#{id,jdbcType=INTEGER}"})
    void updateUser(User user);

    /**
     * @Description: 根据id修改用户密码
     * @Param: [user]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/9/3
     */
    @Update({"update user set password = #{password,jdbcType=VARCHAR} where id=#{id,jdbcType=INTEGER}"})
    void updatePassword(User user);
}
