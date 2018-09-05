package com.cb.web.zuitoutiao.dao;

import net.sf.json.JSONObject;

/**
 * @Description: 根据type来选择搜索的资讯类型
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-08-21 14:34
 **/
public class ArticleProvider {
    /**
     * @Description: 登录用户根据资讯类型获取资讯
     * @Param: [inputParams]
     * @return: java.lang.String
     * @Author: Chen Ben
     * @Date: 2018/8/29
     */
    public String getArticleByType(JSONObject inputParams) {
        String sql = " select distinct id,title,url,keywords,type,image_url,fangwenliang,time,likes,dislikes " +
                "from article";
        String type = inputParams.optString("type");
        Integer start = inputParams.getInt("start");
        if (type.equals("all")) {
            sql = sql + " order by id desc limit " + start + ",20 ";
        } else {
            sql = sql + " where type='" + type + "' order by id desc limit " + start + ",20 ";
        }
        return sql;
    }

    /**
     * @Description: 获取推荐资讯,type=0为未登录,其他值时为已登录且对应type的值
     * @Param: [inputParams]
     * @return: java.lang.String
     * @Author: Chen Ben
     * @Date: 2018/8/29
     */
    public String queryArticle(JSONObject inputParams) {
        String sql = " select distinct id,title,url,keywords,type,image_url,fangwenliang,time,likes,dislikes " +
                "from article";
        String type = inputParams.optString("type");
        Integer start = inputParams.getInt("start");
        if (type.equals("0")) {
            sql = sql + " order by fangwenliang desc,id desc limit " + start + ",20 ";
        } else {
            sql = sql + " where type='" + type + "' order by fangwenliang desc,id desc limit " + start + ",20 ";
        }
        return sql;
    }
}
