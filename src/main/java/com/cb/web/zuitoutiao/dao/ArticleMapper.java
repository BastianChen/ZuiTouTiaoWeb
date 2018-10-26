package com.cb.web.zuitoutiao.dao;

import com.cb.web.zuitoutiao.entity.Article;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ArticleMapper")
public interface ArticleMapper {
    /**
     * @Description: 新增爬取的资讯
     * @Param: [article]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/16
     */
    @Insert({"insert into article (title,url,content,time_source,author_url,keywords,type,image_url,fangwenliang,time,likes,dislikes)",
            "values (#{title,jdbcType=VARCHAR},#{url,jdbcType=VARCHAR},#{content,jdbcType=VARCHAR}," +
                    "#{timeSource,jdbcType=VARCHAR},#{authorUrl,jdbcType=VARCHAR},#{keywords,jdbcType=VARCHAR}," +
                    "#{type,jdbcType=VARCHAR},#{imageUrl,jdbcType=VARCHAR},#{fangwenliang,jdbcType=VARCHAR}," +
                    "#{time,jdbcType=VARCHAR},#{likes,jdbcType=VARCHAR},#{dislikes,jdbcType=VARCHAR})"})
    void insertArticle(Article article);

    /**
     * @Description: 从数据库中搜索出所有资讯
     * @Param: []
     * @return: java.util.List<com.cb.web.entity.Article>
     * @Author: Chen Ben
     * @Date: 2018/8/16
     */
    @Select({"select distinct id,title,url,keywords,type,image_url,fangwenliang,time,likes,dislikes " +
            "from article order by id desc"})
    @Results({@Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "url", property = "url", jdbcType = JdbcType.VARCHAR),
            @Result(column = "keywords", property = "keywords", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type", property = "type", jdbcType = JdbcType.VARCHAR),
            @Result(column = "image_url", property = "imageUrl", jdbcType = JdbcType.VARCHAR),
            @Result(column = "fangwenliang", property = "fangwenliang", jdbcType = JdbcType.VARCHAR),
            @Result(column = "time", property = "time", jdbcType = JdbcType.VARCHAR),
            @Result(column = "likes", property = "likes", jdbcType = JdbcType.VARCHAR),
            @Result(column = "dislikes", property = "dislikes", jdbcType = JdbcType.VARCHAR),})
    List<Article> getArticle();

    /**
     * @Description: 根据资讯类型搜索出相应的部分资讯
     * @Param: [type]
     * @return: java.util.List<com.cb.web.entity.Article>
     * @Author: Chen Ben
     * @Date: 2018/8/17
     */
    @SelectProvider(type = ArticleProvider.class, method = "getArticleByType")
    @Results({@Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "url", property = "url", jdbcType = JdbcType.VARCHAR),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "keywords", property = "keywords", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type", property = "type", jdbcType = JdbcType.VARCHAR),
            @Result(column = "image_url", property = "imageUrl", jdbcType = JdbcType.VARCHAR),
            @Result(column = "fangwenliang", property = "fangwenliang", jdbcType = JdbcType.VARCHAR),
            @Result(column = "time", property = "time", jdbcType = JdbcType.VARCHAR),
            @Result(column = "likes", property = "likes", jdbcType = JdbcType.VARCHAR),
            @Result(column = "dislikes", property = "dislikes", jdbcType = JdbcType.VARCHAR),})
    List<Article> getArticleByType(JSONObject inputParams);

    /**
     * @Description: 根据资讯id获取资讯详细信息
     * @Param: [id]
     * @return: com.cb.web.entity.Article
     * @Author: Chen Ben
     * @Date: 2018/8/19
     */
    @Select({"select id,title,url,content,time_source,author_url,keywords,type,fangwenliang,likes,dislikes " +
            "from article where id=#{id,jdbcType=INTEGER}"})
    @Results({@Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "url", property = "url", jdbcType = JdbcType.VARCHAR),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "time_source", property = "timeSource", jdbcType = JdbcType.VARCHAR),
            @Result(column = "author_url", property = "authorUrl", jdbcType = JdbcType.VARCHAR),
            @Result(column = "keywords", property = "keywords", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type", property = "type", jdbcType = JdbcType.VARCHAR),
            @Result(column = "fangwenliang", property = "fangwenliang", jdbcType = JdbcType.INTEGER),
            @Result(column = "likes", property = "likes", jdbcType = JdbcType.INTEGER),
            @Result(column = "dislikes", property = "dislikes", jdbcType = JdbcType.INTEGER),})
    Article getArticleById(Integer id);

    /**
     * @Description: 增加资讯的访问量
     * @Param: [article]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/20
     */
    @Update({"update article set fangwenliang = #{fangwenliang,jdbcType=INTEGER} where id=#{id,jdbcType=INTEGER}"})
    void updateFangwenliang(Article article);

    /**
     * @Description: 增加点赞数
     * @Param: [article]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/20
     */
    @Update({"update article set likes = #{likes,jdbcType=INTEGER} where id=#{id,jdbcType=INTEGER}"})
    void updateLikes(Article article);

    /**
     * @Description: 增加点踩数
     * @Param: [article]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/20
     */
    @Update({"update article set dislikes = #{dislikes,jdbcType=INTEGER} where id=#{id,jdbcType=INTEGER}"})
    void updateDislikes(Article article);

    /**
     * @Description: 根据访问量降序搜索出资讯
     * @Param: []
     * @return: java.util.List<com.cb.web.entity.Article>
     * @Author: Chen Ben
     * @Date: 2018/8/29
     */
    @SelectProvider(type = ArticleProvider.class, method = "queryArticle")
    @Results({@Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "url", property = "url", jdbcType = JdbcType.VARCHAR),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "keywords", property = "keywords", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type", property = "type", jdbcType = JdbcType.VARCHAR),
            @Result(column = "image_url", property = "imageUrl", jdbcType = JdbcType.VARCHAR),
            @Result(column = "fangwenliang", property = "fangwenliang", jdbcType = JdbcType.VARCHAR),
            @Result(column = "time", property = "time", jdbcType = JdbcType.VARCHAR),
            @Result(column = "likes", property = "likes", jdbcType = JdbcType.VARCHAR),
            @Result(column = "dislikes", property = "dislikes", jdbcType = JdbcType.VARCHAR),})
    List<Article> queryArticle(JSONObject inputParams);

    /**
    * @Description: 模糊搜索资讯
    * @Param: [search, start]
    * @return: java.util.List<com.cb.web.entity.Article>
    * @Author: Chen Ben
    * @Date: 2018/8/29
    */
//    @Select({"select distinct id,title,url,keywords,type,image_url,fangwenliang,time,likes,dislikes " +
//            "from article where title like CONCAT('%',#{search,jdbcType=VARCHAR},'%') " +
//            "or content like CONCAT('%',#{search,jdbcType=VARCHAR},'%') or keywords like CONCAT('%',#{search,jdbcType=VARCHAR},'%')" +
//            " order by id desc limit #{start,jdbcType=INTEGER},20"})
//    @Results({@Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER),
//            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
//            @Result(column = "url", property = "url", jdbcType = JdbcType.VARCHAR),
//            @Result(column = "keywords", property = "keywords", jdbcType = JdbcType.VARCHAR),
//            @Result(column = "type", property = "type", jdbcType = JdbcType.VARCHAR),
//            @Result(column = "image_url", property = "imageUrl", jdbcType = JdbcType.VARCHAR),
//            @Result(column = "fangwenliang", property = "fangwenliang", jdbcType = JdbcType.VARCHAR),
//            @Result(column = "time", property = "time", jdbcType = JdbcType.VARCHAR),
//            @Result(column = "likes", property = "likes", jdbcType = JdbcType.VARCHAR),
//            @Result(column = "dislikes", property = "dislikes", jdbcType = JdbcType.VARCHAR),})
//    List<Article> searchArticle(@Param("search") String search, @Param("start") Integer start);

    @Select({"select distinct id,title,url,keywords,type,image_url,fangwenliang,time,likes,dislikes " +
            "from article where title like CONCAT('%',#{search,jdbcType=VARCHAR},'%') " +
            " order by id desc limit #{start,jdbcType=INTEGER},20"})
    @Results({@Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "url", property = "url", jdbcType = JdbcType.VARCHAR),
            @Result(column = "keywords", property = "keywords", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type", property = "type", jdbcType = JdbcType.VARCHAR),
            @Result(column = "image_url", property = "imageUrl", jdbcType = JdbcType.VARCHAR),
            @Result(column = "fangwenliang", property = "fangwenliang", jdbcType = JdbcType.VARCHAR),
            @Result(column = "time", property = "time", jdbcType = JdbcType.VARCHAR),
            @Result(column = "likes", property = "likes", jdbcType = JdbcType.VARCHAR),
            @Result(column = "dislikes", property = "dislikes", jdbcType = JdbcType.VARCHAR),})
    List<Article> searchArticle(@Param("search") String search, @Param("start") Integer start);
}