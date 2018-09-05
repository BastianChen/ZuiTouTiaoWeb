package com.cb.web.zuitoutiao.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ArticleDTO implements Serializable {
    private Integer id;

    private String title;

    private String url;

    private String content;

    private String timeSource;

    private String authorUrl;

    private String keywords;

    private String type;

    private String imageUrl;

    private Integer fangwenliang;

    private String time;

    private Integer likes;

    private Integer dislikes;

    private List<String> imageUrlList;

    private static final long serialVersionUID = 1L;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
