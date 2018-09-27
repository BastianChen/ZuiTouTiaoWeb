package com.cb.web.zuitoutiao.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 记录用户点赞了哪些资讯
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-09-27 11:24
 **/

@Data
public class UserArticleLikes implements Serializable {
    private Integer userId;
    private Integer articleId;
    private static final long serialVersionUID = 1L;
}
