package com.cb.web.zuitoutiao.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class HobbyModelDTO implements Serializable {
    private Integer userId;

    private String hobbyName;

    private Double hobbyRate;

    private static final long serialVersionUID = 1L;
}
