package com.cb.web.zuitoutiao.dao;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Repository("TypeMapper")
public interface TypeMapper {
    /** 
    * @Description: 根据资讯类型返回资讯类型的id 
    * @Param: [type] 
    * @return: java.lang.Integer 
    * @Author: Chen Ben 
    * @Date: 2018/8/28 
    */ 
    @Select({"select id from type where type=#{type,jdbcType=VARCHAR}"})
    @Results({@Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER),})
    Integer getTypeId(String type);
}