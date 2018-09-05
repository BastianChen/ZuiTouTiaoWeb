package com.cb.web.zuitoutiao.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-08-30 10:48
 **/
@Data
public class Token {
    private Integer id;
    private String accountName;
    private String series;
    private String token;
    private Date validTime;
}
