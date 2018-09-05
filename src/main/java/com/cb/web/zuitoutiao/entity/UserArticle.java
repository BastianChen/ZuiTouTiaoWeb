package com.cb.web.zuitoutiao.entity;

import java.io.Serializable;

public class UserArticle implements Serializable {
    private Integer userId;
    private Integer articleId;
    private String readDate;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getReadDate() {
        return readDate;
    }

    public void setReadDate(String readDate) {
        this.readDate = readDate == null ? null : readDate.trim();
    }
}