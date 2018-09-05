package com.cb.web.zuitoutiao.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;

public interface ArticleService {

    void insertArticle(Integer typeNumber) throws IOException;

    Integer getArticleSize();

    JSONArray getArticleByType(Integer typeNumber, Integer start, Integer userId);

    JSONObject getArticleById(Integer id) throws IOException;

    void updateFangwenliang(Integer id);

    void updateLikes(Integer articleId, Integer userId) throws IOException;

    void updateDislikes(Integer articleId, Integer userId);

    JSONArray searchArticle(String search, Integer start);
}
