package com.cb.web.zuitoutiao.service.impl;

import com.cb.web.zuitoutiao.dao.*;
import com.cb.web.zuitoutiao.dto.ArticleDTO;
import com.cb.web.zuitoutiao.dto.HobbyModelDTO;
import com.cb.web.zuitoutiao.entity.Article;
import com.cb.web.zuitoutiao.entity.HobbyModel;
import com.cb.web.zuitoutiao.entity.UserArticleLikes;
import com.cb.web.zuitoutiao.entity.UserRead;
import com.cb.web.zuitoutiao.service.ArticleService;
import com.cb.web.zuitoutiao.utils.MahoutUtil;
import com.cb.web.zuitoutiao.utils.ScoreUtil;
import com.cb.web.zuitoutiao.utils.SortUtil;
import com.cb.web.zuitoutiao.utils.UrlPath;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.mahout.cf.taste.common.TasteException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("ArticleService")
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserReadMapper userReadMapper;
    @Autowired
    private HobbyModelMapper hobbyModelMapper;
    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private UserArticleLikesMapper userArticleLikesMapper;
    private Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    /**
     * @Description: 该方法用来爬取网易新闻中的资讯
     * @Param: []
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/17
     */
    @Override
    public void insertArticle(Integer typeNumber) throws IOException {
        String url = UrlPath.paths[typeNumber];
        JSONArray jsonArray = new JSONArray();
        int number = 0;
        List<Article> articleList = articleMapper.getArticle();
        Article article = new Article();
        Connection con = Jsoup.connect(url).ignoreContentType(true);
        con.header("Referer", UrlPath.headers[typeNumber]);
        con.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
        byte[] doc = con.execute().bodyAsBytes();
        String js = new String(doc, "GBK").replace("data_callback(", "");
        String s = js.substring(0, js.length() - 1);
        JSONArray jsonArray1 = JSONArray.fromObject(s);
        //避免爬取重复资讯
        for (int i = 0; i < 20; i++) {
            JSONObject jsonObject = new JSONObject();
            for (Article articles : articleList) {
                if (jsonArray1.getJSONObject(i).optString("docurl").equals(articles.getUrl())) {
                    number = 1;
                    break;
                } else {
                    number = 0;
                }
            }
            if (number == 0) {
                jsonObject.put("title", jsonArray1.getJSONObject(i).optString("title"));
                jsonObject.put("articleUrl", jsonArray1.getJSONObject(i).optString("docurl"));
                jsonObject.put("time", jsonArray1.getJSONObject(i).optString("time"));
                jsonObject.put("image_url", jsonArray1.getJSONObject(i).optString("imgurl"));
                jsonObject.put("type", UrlPath.types[typeNumber]);
                JSONArray keywords = jsonArray1.getJSONObject(i).optJSONArray("keywords");
                if (keywords != null && keywords.size() > 0) {
                    for (int j = 0; j < keywords.size(); j++) {
                        if (j == 0) {
                            jsonObject.put("keywords", keywords.getJSONObject(j).optString("keyname"));
                        } else {
                            String keyname = jsonObject.optString("keywords");
                            jsonObject.put("keywords", keyname + ";" + keywords.getJSONObject(j).optString("keyname"));
                        }
                    }
                } else {
                    jsonObject.put("keywords", "");
                }
                jsonArray.add(jsonObject);
            } else {
                continue;
            }
        }

        if (jsonArray.size() != 0) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                try {
                    Document doc1 = Jsoup.connect((String) jsonObject.get("articleUrl")).get();
                    Elements content_main = doc1.getElementsByClass("post_content_main");
                    String time_source = content_main.first().getElementsByClass("post_time_source").first().text();

                    jsonObject.put("time_source", time_source);
                    System.out.println(time_source);

                    Elements author_url = content_main.first().getElementsByClass("post_time_source").select("a");
                    String authorUrl = author_url.attr("href");
                    jsonObject.put("authorUrl", authorUrl);

                    Elements contents = content_main.first().getElementById("endText").select("p");
                    StringBuffer buffer = new StringBuffer();
                    for (Element e : contents) {
                        if (e.className().equals("f_center")) {
                            buffer.append("$%");
                        } else {
                            buffer.append(e.text() + "\n");
                        }
                    }
                    String content = buffer.toString();
                    jsonObject.put("content", content);
                } catch (NullPointerException npx) {
                    logger.error("这可能是一条广告、图片集或者网站html结构跟其他资讯的网站结构不同");
                    npx.printStackTrace();
                    jsonArray.remove(i);
                    i--;
                }
            }
            //将数据持久化
            if (jsonArray.size() != 0) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    article.setTitle(jsonObject.optString("title"));
                    article.setUrl(jsonObject.optString("articleUrl"));
                    article.setTime(jsonObject.optString("time"));
                    article.setImageUrl(jsonObject.optString("image_url"));
                    article.setType(jsonObject.optString("type"));
                    article.setKeywords(jsonObject.optString("keywords"));
                    article.setTimeSource(jsonObject.optString("time_source"));
                    article.setAuthorUrl(jsonObject.optString("authorUrl"));
                    article.setContent(jsonObject.optString("content"));
                    article.setFangwenliang(0);
                    article.setLikes(0);
                    article.setDislikes(0);
                    articleMapper.insertArticle(article);
                }
            }
        }
        System.out.println(jsonArray);
    }

    /**
     * @Description: 从数据库中获取资讯总数
     * @Param: []
     * @return: java.lang.String
     * @Author: Chen Ben
     * @Date: 2018/8/21
     */
    @Override
    public Integer getArticleSize() {
        Integer size = articleMapper.getArticle().size();
        return size;
    }

    /**
     * @Description: 从数据库中搜索资讯
     * @Param: []
     * @return: net.sf.json.JSONArray
     * @Author: Chen Ben
     * @Date: 2018/8/17
     */
    @Override
    public JSONArray getArticleByType(Integer typeNumber, Integer start, Integer userId) {
        if (typeNumber != UrlPath.types.length) {
            if (typeNumber == 8) {
                if (userId == null) {
                    JSONArray jsonArray = getJSONArray(start);
                    return jsonArray;
                } else {
                    Map<String, Object> resultMap = new HashMap<String, Object>();
                    try {
                        resultMap = MahoutUtil.UserCF();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (TasteException e) {
                        e.printStackTrace();
                    }
                    Boolean isHobbyModel = false;
                    JSONArray jsonArray = (JSONArray) resultMap.get("UserCF");
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        //判断用户兴趣模型是否已经生成
                        if (userId == jsonObject.getInt("userId")) {
                            isHobbyModel = true;
                            List<Integer> typeList = jsonObject.getJSONArray("typeId");
                            if (typeList.size() != 0) {
                                List<Article> articleListAll = new ArrayList<>();
                                for (int j = 0; j < typeList.size(); j++) {
                                    JSONObject inputParams = new JSONObject();
                                    inputParams.put("type", UrlPath.types[typeList.get(j) - 1]);
                                    inputParams.put("start", start);
                                    List<Article> articleList = articleMapper.queryArticle(inputParams);
                                    articleListAll.addAll(articleList);
                                }
                                JSONArray jsonArray2 = JSONArray.fromObject(articleListAll);
                                return jsonArray2;
                            } else {
                                JSONArray jsonArray1 = getJSONArray(start);
                                return jsonArray1;
                            }
                        }
                    }
                    if (isHobbyModel == false) {
                        JSONArray jsonArray1 = getJSONArray(start);
                        return jsonArray1;
                    }
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", UrlPath.types[typeNumber]);
                jsonObject.put("start", start);
                List<Article> articleList = articleMapper.getArticleByType(jsonObject);
                JSONArray jsonArray = JSONArray.fromObject(articleList);
                return jsonArray;
            } else {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", UrlPath.types[typeNumber]);
                jsonObject.put("start", start);
                List<Article> articleList = articleMapper.getArticleByType(jsonObject);
                JSONArray jsonArray = JSONArray.fromObject(articleList);
                return jsonArray;
            }
        } else {
            //搜索出所有类型资讯中最新的20条
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "all");
            jsonObject.put("start", start);
            List<Article> articleList = articleMapper.getArticleByType(jsonObject);
            JSONArray jsonArray = JSONArray.fromObject(articleList);
            return jsonArray;
        }

    }

    /**
     * @Description: 根据访问量和id搜索出20条不同类型的资讯
     * @Param: [start]
     * @return: net.sf.json.JSONArray
     * @Author: Chen Ben
     * @Date: 2018/9/4
     */
    public JSONArray getJSONArray(Integer start) {
        JSONObject inputParams = new JSONObject();
        inputParams.put("type", "0");
        inputParams.put("start", start);
        List<Article> articleList = articleMapper.queryArticle(inputParams);
        JSONArray jsonArray = JSONArray.fromObject(articleList);
        return jsonArray;
    }

    /**
     * @return net.sf.json.JSONArray
     * @Description: 获取资讯详细信息
     * @Param [id]
     * @Author: Chen Ben
     * @Date 2018/8/19
     */
    @Override
    public JSONObject getArticleById(Integer id) throws IOException {
        Article article = articleMapper.getArticleById(id);
        ArticleDTO articleDTO = new ArticleDTO();
        List<String> imageUrlList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        BeanUtils.copyProperties(article, articleDTO);
        String url = articleDTO.getUrl();
        Document doc = Jsoup.connect(url).get();
        Elements content_main = doc.getElementsByClass("post_content_main");
        Elements contents = content_main.first().getElementById("endText").select("p");
        contents = contents.select("[src]");
        for (Element e : contents) {
            if (e.tagName().equals("img")) {
                imageUrlList.add(e.attr("abs:src"));
                System.out.println(e.attr("abs:src"));
            }
        }
        articleDTO.setImageUrlList(imageUrlList);
        jsonObject.put("ArticleDTO", articleDTO);
        return jsonObject;
    }

    /**
     * @Description: 增加资讯访问量
     * @Param: []
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/20
     */
    @Override
    public void updateFangwenliang(Integer id) {
        Article article = articleMapper.getArticleById(id);
        article.setFangwenliang(article.getFangwenliang() + UrlPath.number);
        articleMapper.updateFangwenliang(article);
    }

    /**
     * @Description: 增加或撤销点赞数
     * @Param: [id]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/20
     */
    @Override
    public Integer updateLikes(Integer articleId, Integer userId) {
        boolean isLike;
        UserArticleLikes userArticleLikes = userArticleLikesMapper.getUserArticleLikes(userId,articleId,"1");
        Article article = articleMapper.getArticleById(articleId);
        if(userArticleLikes == null){
            logger.info("点赞成功");
            article.setLikes(article.getLikes() + UrlPath.number);
            articleMapper.updateLikes(article);
            userArticleLikesMapper.insertUserArticleLikes(userId,articleId,"1");
            isLike=false;
        }else {
            logger.info("撤销点赞成功");
            article.setLikes(article.getLikes() - UrlPath.number);
            articleMapper.updateLikes(article);
            userArticleLikesMapper.deleteUserArticleLikes(userId,articleId,"1");
            isLike=true;
        }
        if (userId != null) {
            UserRead userRead = userReadMapper.queryUserRead(userId, article.getType());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", 2);
            jsonObject.put("userId", userId);
            jsonObject.put("typeName", userRead.getTypeName());
            if(isLike==false){
                jsonObject.put("likes", userRead.getLikes() + 1);
            }else {
                jsonObject.put("likes", userRead.getLikes() - 1);
            }
            userReadMapper.updateUserRead(jsonObject);
            if (userRead.getTotalTimes() >= 20) {
                List<UserRead> userReadList = userReadMapper.queryUserReadList(userId);
                List<UserRead> userReads = new ArrayList<>();
                userReads = ScoreUtil.calculation(userReadList, userId, "2", userRead.getTypeName());
                jsonObject.put("type", 4);
                jsonObject.put("userId", userId);
                jsonObject.put("typeName", userRead.getTypeName());
                jsonObject.put("score", userReads.get(0).getScore());
                userReadMapper.updateUserRead(jsonObject);
                logger.info("更新或生成兴趣模型");
                List<UserRead> queryUserReadList = userReadMapper.queryUserReadList(userId);
                queryUserReadList = SortUtil.sortUserRead(queryUserReadList);
                HobbyModel hobbyModel = hobbyModelMapper.getHobbyModel(userId);
                hobbyModel = UserReadServiceImpl.updateHobbyModel(hobbyModel, userId, queryUserReadList);
                hobbyModelMapper.updateHobbyModel(hobbyModel);
                try {
                    update(hobbyModel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return article.getLikes();
    }

    /**
     * @Description: 增加点踩数
     * @Param: [id]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/20
     */
    @Override
    public Integer updateDislikes(Integer articleId, Integer userId) {
        boolean isDislike;
        UserArticleLikes userArticleLikes = userArticleLikesMapper.getUserArticleLikes(userId,articleId,"2");
        Article article = articleMapper.getArticleById(articleId);
        if(userArticleLikes == null){
            logger.info("点踩成功");
            article.setDislikes(article.getDislikes() + UrlPath.number);
            articleMapper.updateDislikes(article);
            userArticleLikesMapper.insertUserArticleLikes(userId,articleId,"2");
            isDislike=false;
        }else {
            logger.info("撤销点踩成功");
            article.setDislikes(article.getDislikes() - UrlPath.number);
            articleMapper.updateDislikes(article);
            userArticleLikesMapper.deleteUserArticleLikes(userId,articleId,"2");
            isDislike=true;
        }
        if (userId != null) {
            UserRead userRead = userReadMapper.queryUserRead(userId, article.getType());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", 3);
            jsonObject.put("userId", userId);
            jsonObject.put("typeName", userRead.getTypeName());
            if(isDislike==false){
                jsonObject.put("dislikes", userRead.getDislikes() + 1);
            }else {
                jsonObject.put("dislikes", userRead.getDislikes() - 1);
            }
            userReadMapper.updateUserRead(jsonObject);
            if (userRead.getTotalTimes() >= 20) {
                List<UserRead> userReadList = userReadMapper.queryUserReadList(userId);
                List<UserRead> userReads;
                userReads = ScoreUtil.calculation(userReadList, userId, "2", userRead.getTypeName());
                jsonObject.put("type", 4);
                jsonObject.put("userId", userId);
                jsonObject.put("typeName", userRead.getTypeName());
                jsonObject.put("score", userReads.get(0).getScore());
                userReadMapper.updateUserRead(jsonObject);
                logger.info("更新或生成兴趣模型");
                List<UserRead> queryUserReadList = userReadMapper.queryUserReadList(userId);
                queryUserReadList = SortUtil.sortUserRead(queryUserReadList);
                HobbyModel hobbyModel = hobbyModelMapper.getHobbyModel(userId);
                hobbyModel = UserReadServiceImpl.updateHobbyModel(hobbyModel, userId, queryUserReadList);
                hobbyModelMapper.updateHobbyModel(hobbyModel);
                try {
                    update(hobbyModel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return article.getDislikes();
    }

    /**
     * @Description: 模糊搜索资讯
     * @Param: [search, start]
     * @return: net.sf.json.JSONArray
     * @Author: Chen Ben
     * @Date: 2018/8/29
     */
    @Override
    public JSONArray searchArticle(String search, Integer start) {
        List<Article> articleList = articleMapper.searchArticle(search, start);
        JSONArray jsonArray = JSONArray.fromObject(articleList);
        return jsonArray;
    }

    /**
     * @Description: 更新数据：1、先将用户的数据都重写格式2、再根据重写后的格式更改数据
     * @Param: [hobbyModel]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/29
     */
    public void update(HobbyModel hobbyModel) throws IOException {
        List<HobbyModelDTO> hobbyModelDTOList = UserReadServiceImpl.getHobbyModelDTOList(hobbyModel);
        List<String> txt = Files.readAllLines(Paths.get(new File("").getCanonicalPath() + "/hobby/hobby.txt"));
        List<String> replaced = new ArrayList<>();
        String[] str = {hobbyModelDTOList.get(0).getUserId() + ",1,", hobbyModelDTOList.get(0).getUserId() + ",2,",
                hobbyModelDTOList.get(0).getUserId() + ",3,", hobbyModelDTOList.get(0).getUserId() + ",4,",
                hobbyModelDTOList.get(0).getUserId() + ",5,", hobbyModelDTOList.get(0).getUserId() + ",6,",
                hobbyModelDTOList.get(0).getUserId() + ",7,", hobbyModelDTOList.get(0).getUserId() + ",8,"};
        UserReadServiceImpl.updateTxt(txt, str, replaced, hobbyModelDTOList);
        Files.write(Paths.get(new File("").getCanonicalPath() + "/hobby/hobby.txt"), replaced);
        List<String> txt1 = Files.readAllLines(Paths.get(new File("").getCanonicalPath() + "/hobby/hobby.txt"));
        List<String> replace = new ArrayList<>();
        Double rate = 0.0;
        Integer id = 1, j = 0;
        for (String line : txt1) {
            if (line.contains("科技") || line.contains("国际") || line.contains("国内") || line.contains("军事") ||
                    line.contains("财经") || line.contains("时尚") || line.contains("娱乐") || line.contains("体育")) {
                id = typeMapper.getTypeId(hobbyModelDTOList.get(j).getHobbyName());
                rate = hobbyModelDTOList.get(j).getHobbyRate();
                replace.add(hobbyModelDTOList.get(0).getUserId() + "," + id + "," + rate);
                j++;
            } else {
                replace.add(line);
            }
        }
        Files.write(Paths.get(new File("").getCanonicalPath() + "/hobby/hobby.txt"), replace);
    }
}
