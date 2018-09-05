package com.cb.web.zuitoutiao.utils;

/**
 * @author YFZX-CB-1784 ChenBen
 * @create 2018-08-17 17:13
 **/
public class UrlPath {
    public static Integer number = 1;
    public static String[] paths = {"http://tech.163.com/special/00097UHL/tech_datalist.js?callback=data_callback",
            "http://temp.163.com/special/00804KVA/cm_guoji.js?callback=data_callback",
            "http://temp.163.com/special/00804KVA/cm_guonei.js?callback=data_callback",
            "http://temp.163.com/special/00804KVA/cm_war.js?callback=data_callback",
            "http://money.163.com/special/002557S5/newsdata_idx_index.js?callback=data_callback",
    "http://lady.163.com/special/00264OOD/data_nd_art.js?callback=data_callback",
    "http://ent.163.com/special/000380VU/newsdata_index.js?callback=data_callback",
    "http://sports.163.com/special/000587PR/newsdata_n_allsports.js?callback=data_callback"};

    public static String[] headers = {"http://tech.163.com/","http://news.163.com/world/","http://news.163.com/domestic/",
    "http://war.163.com/","http://money.163.com/"
            ,"http://lady.163.com/","http://ent.163.com/","http://sports.163.com/"};

    public static String[] types = {"科技","国际","国内","军事","财经"
            ,"时尚","娱乐","体育","推荐"};
    //"http://money.163.com/special/002557S5/newsdata_idx_index.js?callback=data_callback",
    //,"http://money.163.com/"
    //,"财经"
    //前端还需加个所有
}
