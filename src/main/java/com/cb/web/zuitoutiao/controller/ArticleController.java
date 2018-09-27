package com.cb.web.zuitoutiao.controller;

import com.cb.web.zuitoutiao.service.ArticleService;
import com.cb.web.zuitoutiao.service.UserArticleService;
import com.cb.web.zuitoutiao.service.UserReadService;
import com.cb.web.zuitoutiao.service.UserService;
import com.cb.web.zuitoutiao.utils.UrlPath;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author YFZX-CB-1784 ChenBen
 * @create 2018-08-16 11:14
 **/
@CrossOrigin(origins = "http://localhost:8081")
@Controller
@Api(value = "Article",description = "资讯接口")
@RequestMapping(value = "/Article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserArticleService userArticleService;
    @Autowired
    private UserReadService userReadService;
    @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(ArticleController.class);

    /**
     * @Description: 爬取资讯
     * @Param: []
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/17
     */
    @Scheduled(cron="0 0/30 * * * ?")   //每半小时执行一次
    @PostConstruct  //项目启动时执行
    public void insertArticle() throws IOException {
        logger.info("爬取资讯开始");
        for (int i = 0; i < UrlPath.paths.length; i++) {
            articleService.insertArticle(i);
        }
    }

    /**
     * @Description: 从数据库中获取资讯总数
     * @Param: []
     * @return: net.sf.json.JSONArray
     * @Author: Chen Ben
     * @Date: 2018/8/17
     */
    @RequestMapping(value = "/getArticleCount", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    @ApiOperation(value="获取资讯总数接口", notes="获取资讯总数")
    public Map<String, Object> getArticleCount() {
        logger.info("搜索资讯开始");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Integer size = articleService.getArticleSize();
        resultMap.put("size", size);
        return resultMap;
    }

    /**
     * @Description: 根据资讯类型从数据库中获取资讯数据
     * @Param: []
     * @return: net.sf.json.JSONArray
     * @Author: Chen Ben
     * @Date: 2018/8/17
     */
    @RequestMapping(value = "/getArticleByType", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    @ApiOperation(value="根据资讯类型搜索资讯接口", notes="根据资讯类型从数据库中获取资讯数据")
    public Map<String, Object> getArticleByType(@ApiParam(required=true, name="typeNumber", value="资讯类型编号（见UrlPath里的types数组）")
                                                @RequestParam(value = "typeNumber", required = true) Integer typeNumber,
                                                @ApiParam(required=true, name="start", value="start")
                                                @RequestParam(value = "start", required = true) Integer start,
                                                @ApiParam(required=false, name="userId", value="用户id")
                                                @RequestParam(value = "userId", required = false) Integer userId) {
        logger.info("根据类型搜索资讯开始");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        JSONArray articleJSONArray = articleService.getArticleByType(typeNumber, start, userId);
        resultMap.put("articleJSONArray", articleJSONArray);
        return resultMap;
    }

    /**
     * @Description: 点击资讯列表获取资讯详细信息以及增加资讯访问量
     * @Param: [id]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @Author: Chen Ben
     * @Date: 2018/8/19
     */
    @RequestMapping(value = "/getArticleContent", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    @ApiOperation(value="查看资讯详情接口", notes="点击资讯列表获取资讯详细信息以及增加资讯访问量")
    public Map<String, Object> getArticleContent(@ApiParam(required=false, name="userId", value="用户id")
                                                 @RequestParam(value = "userId", required = false) Integer userId,
                                                 @ApiParam(required=true, name="articleId", value="资讯id")
                                                 @RequestParam(value = "articleId", required = true) Integer articleId) throws IOException {
        logger.info("判断用户是否登陆");
        if (userId == null) {
            logger.info("用户没有登陆");
        } else {
            logger.info("用户已登陆并添加阅读记录");
            userArticleService.insertUserArticle(userId, articleId);
            userService.updateTotalTimes(userId);
            userReadService.insertUserRead(userId, articleId);
        }
        logger.info("增加资讯访问量");
        articleService.updateFangwenliang(articleId);
        logger.info("获取资讯详细信息");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        JSONObject jsonObject = null;
        try {
            jsonObject = articleService.getArticleById(articleId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultMap.put("jsonObject", jsonObject);
        return resultMap;
    }

    /**
     * @Description: 点赞
     * @Param: [id]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/20
     */
    @RequestMapping(value = "/updateLikes", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    @ApiOperation(value="点赞接口", notes="点赞")
    public Map<String, Object> updateLikes(@ApiParam(required=true, name="articleId", value="资讯id")
                            @RequestParam(value = "articleId", required = true) Integer articleId,
                            @ApiParam(required=false, name="userId", value="用户id")
                            @RequestParam(value = "userId", required = false) Integer userId) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        Integer likes = articleService.updateLikes(articleId, userId);
        resultMap.put("likes",likes);
        return resultMap;
    }

    /**
     * @Description: 点踩
     * @Param: [id]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/20
     */
    @RequestMapping(value = "/updateDislikes", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ApiOperation(value="点踩接口", notes="点踩")
    @ResponseBody
    public Map<String, Object> updateDislikes(@ApiParam(required=true, name="articleId", value="资讯id")
                               @RequestParam(value = "articleId", required = true) Integer articleId,
                               @ApiParam(required=false, name="userId", value="用户id")
                               @RequestParam(value = "userId", required = false) Integer userId) {
        logger.info("点踩成功");
        Map<String, Object> resultMap = new HashMap<>();
        Integer dislikes = articleService.updateDislikes(articleId, userId);
        resultMap.put("dislikes",dislikes);
        return resultMap;
    }

    /**
     * @Description: 模糊搜索资讯
     * @Param: [search, start]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @Author: Chen Ben
     * @Date: 2018/8/29
     */
    @RequestMapping(value = "/searchArticle", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    @ApiOperation(value="模糊搜索资讯接口", notes="输入关键字模糊搜索资讯")
    public Map<String, Object> searchArticle(@ApiParam(required=true, name="search", value="搜索的关键字")
                                             @RequestParam(value = "search", required = true) String search,
                                             @ApiParam(required=true, name="start", value="start")
                                             @RequestParam(value = "start", required = true) Integer start) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        JSONArray jsonArray = articleService.searchArticle(search,start);
        resultMap.put("articleJSONArray",jsonArray);
        return resultMap;
    }
}
