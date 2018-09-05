package com.cb.web.zuitoutiao.utils;

import java.util.Random;

/**
 * @Description:
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-08-21 16:46
 **/
public class RandUtil {
    public static String getRandNum() {
        String randNum = new Random().nextInt(1000000)+"";
        System.out.println("生成"+randNum);
        if (randNum.length()!=6) {   //如果生成的不是6位数随机数则返回该方法继续生成
            return getRandNum();
        }
        return randNum;
    }
}
