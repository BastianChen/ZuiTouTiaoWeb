package com.cb.web.zuitoutiao.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 该类用于记录用户所阅读的每种类型资讯的次数
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-08-24 11:19
 **/
@Setter
@Getter
public class UserRead {
    private Integer id;
    private Integer userId;
    private String typeName;
    private Double readTimes;
    private Double totalTimes;
    private Double likes;
    private Double dislikes;
    private Double score;
}
