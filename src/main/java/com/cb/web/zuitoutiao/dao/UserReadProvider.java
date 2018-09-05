package com.cb.web.zuitoutiao.dao;

import net.sf.json.JSONObject;

/**
 * @Description: 根据更新类型分别来更新阅读数、点赞、点踩数或score
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-08-27 10:31
 **/
public class UserReadProvider {
    /**
     * @Description: type=1时更新阅读数;type=2时更新点赞数;type=3时更新点踩数;type=4时更新score
     * @Param: [inputParams]
     * @return: java.lang.String
     * @Author: Chen Ben
     * @Date: 2018/8/27
     */
    public String updateUserRead(JSONObject inputParams) {
        String sql = " update user_read ";
        Integer type = inputParams.getInt("type");
        String userId = inputParams.optString("userId");
        String typeName = inputParams.optString("typeName");
        if (type == 1) {
            String readTimes = inputParams.optString("read_times");
            sql = sql + "set read_times = " + readTimes + " ";
        } else if (type == 2) {
            String likes = inputParams.optString("likes");
            sql = sql + "set likes = " + likes + " ";
        } else if(type == 3){
            String dislikes = inputParams.optString("dislikes");
            sql = sql + "set dislikes = " + dislikes + " ";
        }else {
            Double score = inputParams.getDouble("score");
            sql = sql + "set score = " + score + " ";
        }
        sql = sql + "where user_id = " + userId + " and type_name = '" + typeName + "' ";
        return sql;
    }
}
