package com.cb.web.zuitoutiao.entity;

import lombok.Data;

/**
 * @Description:
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-09-04 15:56
 **/
@Data
public class User {
    private Integer id;

    private String accountName;

    private String password;

    private String userName;

    private Integer age;

    private String gender;

    private String tel;

    private String email;

    private String type;

    private String image;

    private Double totalTimes;
}
