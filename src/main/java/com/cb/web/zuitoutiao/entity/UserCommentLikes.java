package com.cb.web.zuitoutiao.entity;

import lombok.Data;

/**
 * @Description: 记录用户点赞了哪些评论
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-09-27 14:00
 **/
@Data
public class UserCommentLikes {
    private Integer id;
    private Integer userId;
    private Integer commentId;
    private String type;
    private static final long serialVersionUID = 1L;
}
