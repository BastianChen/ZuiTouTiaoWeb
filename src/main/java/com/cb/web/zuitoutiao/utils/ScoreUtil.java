package com.cb.web.zuitoutiao.utils;


import com.cb.web.zuitoutiao.entity.UserRead;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 用来计算用户对各种类型资讯的喜欢程度
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-08-27 11:14
 **/
public class ScoreUtil {
    public static List<UserRead> calculation(List<UserRead> userReadList, Integer userId, String type, String typeName){
        List<UserRead> userReads = new ArrayList<>();
        for(UserRead userRead:userReadList){
            if (type.equals("1")) {
                if(userRead.getUserId().equals(userId)){
                    Double score=0.0;
                    if(userRead.getLikes()+userRead.getDislikes()==0.0){
                        score=0.8*(userRead.getReadTimes()/userRead.getTotalTimes());
                        userRead.setScore(score);
                        userReads.add(userRead);
                    }else{
                        score = 0.8*(userRead.getReadTimes()/userRead.getTotalTimes())+
                                0.2*(userRead.getLikes()/(userRead.getLikes()+userRead.getDislikes()));
                        userRead.setScore(score);
                        userReads.add(userRead);
                    }
                }
            }else{
                if(userRead.getUserId().equals(userId)&&userRead.getTypeName().equals(typeName)){
                    Double score=0.0;
                    if(userRead.getLikes()+userRead.getDislikes()==0.0){
                        score=0.8*(userRead.getReadTimes()/userRead.getTotalTimes());
                        userRead.setScore(score);
                        userReads.add(userRead);
                    }else{
                        score = 0.8*(userRead.getReadTimes()/userRead.getTotalTimes())+
                                0.2*(userRead.getLikes()/(userRead.getLikes()+userRead.getDislikes()));
                        userRead.setScore(score);
                        userReads.add(userRead);
                    }
                }
            }
        }
        return userReads;
    }
}
