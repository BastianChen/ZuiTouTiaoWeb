package com.cb.web.zuitoutiao.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ArticleComment implements Serializable {
    private Integer id;
    @ApiModelProperty(value="评论的资讯id",name="articleId",example="1")
    private Integer articleId;
    @ApiModelProperty(value="给予评论的用户id",name="userId",example="1")
    private Integer userId;
    @ApiModelProperty(value="评论的内容",name="content",example="内容")
    private String content;

    private String createDate;
    @ApiModelProperty(value="该评论的点赞数",name="likes",example="0")
    private Integer likes;
    @ApiModelProperty(value="该评论的点踩数",name="dislikes",example="0")
    private Integer dislikes;
    @ApiModelProperty(value="给予评论的用户名",name="userName",example="ben")
    private String userName;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate == null ? null : createDate.trim();
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getDislikes() {
        return dislikes;
    }

    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}