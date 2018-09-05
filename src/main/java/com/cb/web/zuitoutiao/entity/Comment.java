package com.cb.web.zuitoutiao.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class Comment implements Serializable {
    private Integer id;
    @ApiModelProperty(value="父评论的id",name="commentId",example="1")
    private Integer commentId;
    @ApiModelProperty(value="发表父评论的用户id",name="userId",example="1")
    private Integer userId;
    @ApiModelProperty(value="子评论用户的id",name="followerId",example="3")
    private Integer followerId;
    @ApiModelProperty(value="评论的内容",name="content",example="内容")
    private String content;
    @ApiModelProperty(value="该评论的点赞数",name="likes",example="0")
    private Integer likes;
    @ApiModelProperty(value="该评论的点踩数",name="dislikes",example="0")
    private Integer dislikes;
    @ApiModelProperty(value="发表父评论的用户名",name="userName",example="ben")
    private String userName;
    @ApiModelProperty(value="发表子评论的用户名",name="userName",example="陈奔")
    private String followerName;
    @ApiModelProperty(value="子评论的类型（1：该子评论为普通的子评论；2：该子评论为子评论中的回复评论）",name="type",example="1")
    private String type;

    private String createDate;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Integer followerId) {
        this.followerId = followerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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

    public String getFollowerName() {
        return followerName;
    }

    public void setFollowerName(String followerName) {
        this.followerName = followerName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}