package com.cb.web.zuitoutiao.utils;


import com.cb.web.zuitoutiao.entity.UserRead;

import java.util.List;

/**
 * @Description: 对用户的兴趣进行排序，根据score由大到小排序
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-08-27 15:33
 **/
public class SortUtil {
    public static List<UserRead> sortUserRead(List<UserRead> userReadList){
        for(int i=0;i<userReadList.size()-1;i++){
            for (int j=0;j<userReadList.size()-1-i;j++){
                if(userReadList.get(j).getScore()<userReadList.get(j+1).getScore()){
                    UserRead userRead = userReadList.get(j);
                    userReadList.set(j,userReadList.get(j+1)) ;
                    userReadList.set(j+1,userRead) ;
                }
            }
        }
        return userReadList;
    }
}
