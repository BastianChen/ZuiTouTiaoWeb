package com.cb.web.zuitoutiao.entity;

import java.io.Serializable;

public class HobbyModel implements Serializable {
    private Integer userId;

    private String hobby1Name;

    private Double hobby1Rate;

    private String hobby2Name;

    private Double hobby2Rate;

    private String hobby3Name;

    private Double hobby3Rate;

    private String hobby4Name;

    private Double hobby4Rate;

    private String hobby5Name;

    private Double hobby5Rate;

    private String hobby6Name;

    private Double hobby6Rate;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getHobby1Name() {
        return hobby1Name;
    }

    public void setHobby1Name(String hobby1Name) {
        this.hobby1Name = hobby1Name == null ? null : hobby1Name.trim();
    }

    public Double getHobby1Rate() {
        return hobby1Rate;
    }

    public void setHobby1Rate(Double hobby1Rate) {
        this.hobby1Rate = hobby1Rate;
    }

    public String getHobby2Name() {
        return hobby2Name;
    }

    public void setHobby2Name(String hobby2Name) {
        this.hobby2Name = hobby2Name == null ? null : hobby2Name.trim();
    }

    public Double getHobby2Rate() {
        return hobby2Rate;
    }

    public void setHobby2Rate(Double hobby2Rate) {
        this.hobby2Rate = hobby2Rate;
    }

    public String getHobby3Name() {
        return hobby3Name;
    }

    public void setHobby3Name(String hobby3Name) {
        this.hobby3Name = hobby3Name == null ? null : hobby3Name.trim();
    }

    public Double getHobby3Rate() {
        return hobby3Rate;
    }

    public void setHobby3Rate(Double hobby3Rate) {
        this.hobby3Rate = hobby3Rate;
    }

    public String getHobby4Name() {
        return hobby4Name;
    }

    public void setHobby4Name(String hobby4Name) {
        this.hobby4Name = hobby4Name == null ? null : hobby4Name.trim();
    }

    public Double getHobby4Rate() {
        return hobby4Rate;
    }

    public void setHobby4Rate(Double hobby4Rate) {
        this.hobby4Rate = hobby4Rate;
    }

    public String getHobby5Name() {
        return hobby5Name;
    }

    public void setHobby5Name(String hobby5Name) {
        this.hobby5Name = hobby5Name == null ? null : hobby5Name.trim();
    }

    public Double getHobby5Rate() {
        return hobby5Rate;
    }

    public void setHobby5Rate(Double hobby5Rate) {
        this.hobby5Rate = hobby5Rate;
    }

    public String getHobby6Name() {
        return hobby6Name;
    }

    public void setHobby6Name(String hobby6Name) {
        this.hobby6Name = hobby6Name == null ? null : hobby6Name.trim();
    }

    public Double getHobby6Rate() {
        return hobby6Rate;
    }

    public void setHobby6Rate(Double hobby6Rate) {
        this.hobby6Rate = hobby6Rate;
    }
}