package com.cb.web.zuitoutiao.dao;

import com.cb.web.zuitoutiao.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
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
}
