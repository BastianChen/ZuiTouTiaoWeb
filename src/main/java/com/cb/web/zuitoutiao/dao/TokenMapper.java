package com.cb.web.zuitoutiao.dao;

import com.cb.web.zuitoutiao.entity.Token;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-08-30 10:46
 **/
@Repository("TokenMapper")
public interface TokenMapper {
    /**
     * @Description: 通过用户名和UUID值查询自动登录记录
     * @Param: [accountName, series]
     * @return: com.cb.web.entity.Token
     * @Author: Chen Ben
     * @Date: 2018/8/31
     */
    @Select({"select * from token where account_name = #{accountName,jdbcType=VARCHAR} and series = #{series,jdbcType=VARCHAR}"})
    @Results({@Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER),
            @Result(column = "account_name", property = "accountName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "series", property = "series", jdbcType = JdbcType.VARCHAR),
            @Result(column = "token", property = "token", jdbcType = JdbcType.VARCHAR),
            @Result(column = "valid_time", property = "validTime", jdbcType = JdbcType.TIMESTAMP),})
    Token selectByUsernameAndSeries(@Param("accountName") String accountName, @Param("series") String series);

    /**
     * @Description: 通过用户名查询自动登录记录
     * @Param: [accountName]
     * @return: com.cb.web.entity.Token
     * @Author: Chen Ben
     * @Date: 2018/8/31
     */
    @Select({"select * from token where account_name = #{accountName,jdbcType=VARCHAR}"})
    @Results({@Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER),
            @Result(column = "account_name", property = "accountName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "series", property = "series", jdbcType = JdbcType.VARCHAR),
            @Result(column = "token", property = "token", jdbcType = JdbcType.VARCHAR),
            @Result(column = "valid_time", property = "validTime", jdbcType = JdbcType.TIMESTAMP),})
    Token selectByAccountName(@Param("accountName") String accountName);


    /**
     * @Description: 添加token数据
     * @Param: [token]
     * @return: int
     * @Author: Chen Ben
     * @Date: 2018/8/31
     */
    @Insert({"insert into token (account_name,series,token,valid_time)",
            "values (#{accountName,jdbcType=VARCHAR},#{series,jdbcType=VARCHAR},#{token,jdbcType=VARCHAR}," +
                    "#{validTime,jdbcType=TIMESTAMP})"})
    int insertSelective(Token token);

    /**
     * @Description: 更新token数据
     * @Param: [token]
     * @return: int
     * @Author: Chen Ben
     * @Date: 2018/8/31
     */
    @Update({"update token set series = #{series,jdbcType=VARCHAR},token = #{token,jdbcType=VARCHAR}" +
            " where id = #{id,jdbcType=INTEGER}"})
    int updateByPrimaryKeySelective(Token token);

    /**
     * @Description: 根据id删除token数据
     * @Param: [id]
     * @return: int
     * @Author: Chen Ben
     * @Date: 2018/8/31
     */
    @Delete({"delete from token", "where id = #{id,jdbcType=INTEGER}"})
    int deleteByPrimaryKey(Integer id);
}
