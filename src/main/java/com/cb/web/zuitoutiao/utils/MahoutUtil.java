package com.cb.web.zuitoutiao.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 基于用户的协同过滤算法
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-08-29 11:39
 **/
public class MahoutUtil {
    //用户邻居数量
    final static int NEIGHBORHOOD_NUM = 4;
    //推荐结果个数
    final static int RECOMMENDER_NUM = 4;
    public static Map<String, Object> UserCF() throws IOException, TasteException {
        //数据集，其中第一列表示用户id；第二列表示资讯类型id；第三列表示用户喜欢程度所占的百分比
        File f = new File(new File("").getCanonicalPath() + "/hobby/hobby.txt");
        //基于文件的model，通过文件形式来读入,且此类型所需要读入的数据的格式要求很低，只需要满足每一行是用户id，物品id，用户偏好，且之间用tab或者是逗号隔开即可
        DataModel model = new FileDataModel(f);
        //计算欧式距离，欧式距离来定义相似性，用s=1/(1+d)来表示，范围在[0,1]之间，值越大，表明d越小，距离越近，则表示相似性越大
        UserSimilarity user = new EuclideanDistanceSimilarity(model);
        //指定用户邻居数量
        NearestNUserNeighborhood neighbor = new NearestNUserNeighborhood(NEIGHBORHOOD_NUM, user, model);
        //构建基于用户的推荐系统
        Recommender r = new GenericUserBasedRecommender(model, neighbor, user);
        //得到所有用户的id集合
        LongPrimitiveIterator iter = model.getUserIDs();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        JSONArray jsonArray = new JSONArray();
        while (iter.hasNext()) {
            JSONObject jsonObject = new JSONObject();
            long userId = iter.nextLong();
            //获取推荐结果
            List<RecommendedItem> list = r.recommend(userId, RECOMMENDER_NUM);
            jsonObject.put("userId",userId);
            //遍历推荐结果
            List<Integer> typeList = new ArrayList<>();
            for (RecommendedItem ritem : list) {
                typeList.add((int) ritem.getItemID());
            }
            jsonObject.put("typeId",typeList);
            jsonArray.add(jsonObject);
        }
        resultMap.put("UserCF",jsonArray);
        return resultMap;
    }
}
