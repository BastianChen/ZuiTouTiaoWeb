package com.cb.web.zuitoutiao.service.impl;

import com.cb.web.zuitoutiao.dao.*;
import com.cb.web.zuitoutiao.dto.HobbyModelDTO;
import com.cb.web.zuitoutiao.entity.Article;
import com.cb.web.zuitoutiao.entity.HobbyModel;
import com.cb.web.zuitoutiao.entity.User;
import com.cb.web.zuitoutiao.entity.UserRead;
import com.cb.web.zuitoutiao.service.UserReadService;
import com.cb.web.zuitoutiao.utils.ScoreUtil;
import com.cb.web.zuitoutiao.utils.SortUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 负责用户阅读资讯方面的操作
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-08-24 13:43
 **/
@Service("UserReadService")
public class UserReadServiceImpl implements UserReadService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserReadMapper userReadMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HobbyModelMapper hobbyModelMapper;
    @Autowired
    private TypeMapper typeMapper;
    private Logger logger = LoggerFactory.getLogger(UserReadServiceImpl.class);

    /**
     * @Description: 添加用户阅读记录或更新阅读记录并生成用户兴趣模型
     * @Param: [userId, articleId]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/24
     */
    @Override
    public void insertUserRead(Integer userId, Integer articleId) throws IOException {
        Article article = articleMapper.getArticleById(articleId);
        UserRead userRead = userReadMapper.queryUserRead(userId, article.getType());
        if (userRead == null) {
            UserRead userRead1 = new UserRead();
            userRead1.setUserId(userId);
            userRead1.setTypeName(article.getType());
            userRead1.setReadTimes(1.0);
            User user = userMapper.getUserById(userId);
            userRead1.setTotalTimes(user.getTotalTimes());
            userRead1.setLikes(0.0);
            userRead1.setDislikes(0.0);
            userRead1.setScore(0.0);
            userReadMapper.insertUserReaed(userRead1);
            userReadMapper.updateTotalTimes(userId, user.getTotalTimes());
            JSONObject jsonObject = new JSONObject();
            updateHobbyModel(userRead1, userId, jsonObject);
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", 1);
            jsonObject.put("userId", userId);
            jsonObject.put("typeName", userRead.getTypeName());
            jsonObject.put("read_times", userRead.getReadTimes() + 1);
            userReadMapper.updateUserRead(jsonObject);
            userReadMapper.updateTotalTimes(userId, userRead.getTotalTimes() + 1);
            updateHobbyModel(userRead, userId, jsonObject);
        }
    }

    /**
     * @Description: 更新 hobbyModel中的数据
     * @Param: [userRead, userId, jsonObject]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/28
     */
    public void updateHobbyModel(UserRead userRead, Integer userId, JSONObject jsonObject) throws IOException {
        if (userRead.getTotalTimes() + 1 >= 20) {
            List<UserRead> userReadList = userReadMapper.queryUserReadList(userId);
            List<UserRead> userReads = new ArrayList<>();
            userReads = ScoreUtil.calculation(userReadList, userId, "1", userRead.getTypeName());
            for (UserRead userRead1 : userReads) {
                jsonObject.put("type", 4);
                jsonObject.put("userId", userId);
                jsonObject.put("typeName", userRead1.getTypeName());
                jsonObject.put("score", userRead1.getScore());
                userReadMapper.updateUserRead(jsonObject);
            }
            logger.info(" 更新或生成兴趣模型");
            List<UserRead> queryUserReadList = userReadMapper.queryUserReadList(userId);
            queryUserReadList = SortUtil.sortUserRead(queryUserReadList);
            HobbyModel hobbyModel;
            hobbyModel = hobbyModelMapper.getHobbyModel(userId);
            if (hobbyModel == null) {
                hobbyModel = new HobbyModel();
                hobbyModel = updateHobbyModel(hobbyModel, userId, queryUserReadList);
                hobbyModelMapper.insertHobbyModel(hobbyModel);
                write(hobbyModel);
            } else {
                hobbyModel = updateHobbyModel(hobbyModel, userId, queryUserReadList);
                hobbyModelMapper.updateHobbyModel(hobbyModel);
                update(hobbyModel);
            }
        }
    }

    /**
     * @Description: 将排序后的数据分配到对应的字段中
     * @Param: [hobbyModel, userId, queryUserReadList]
     * @return: com.cb.web.entity.HobbyModel
     * @Author: Chen Ben
     * @Date: 2018/8/27
     */
    public static HobbyModel updateHobbyModel(HobbyModel hobbyModel, Integer userId, List<UserRead> queryUserReadList) {
        hobbyModel.setUserId(userId);
        Double totalScore = 0.0;
        Double rate = 0.0;
        int size = 6;
        if (queryUserReadList.size() > size) {
            for (int i = 0; i < size; i++) {
                totalScore = totalScore + queryUserReadList.get(i).getScore();
            }
        } else {
            for (UserRead userRead1 : queryUserReadList) {
                totalScore = totalScore + userRead1.getScore();
            }
        }
        for (int i = 0; i < queryUserReadList.size(); i++) {
            rate = (queryUserReadList.get(i).getScore() / totalScore) * 100;
            if (i == 0) {
                hobbyModel.setHobby1Name(queryUserReadList.get(i).getTypeName());
                hobbyModel.setHobby1Rate(rate);
            } else if (i == 1) {
                hobbyModel.setHobby2Name(queryUserReadList.get(i).getTypeName());
                hobbyModel.setHobby2Rate(rate);
            } else if (i == 2) {
                hobbyModel.setHobby3Name(queryUserReadList.get(i).getTypeName());
                hobbyModel.setHobby3Rate(rate);
            } else if (i == 3) {
                hobbyModel.setHobby4Name(queryUserReadList.get(i).getTypeName());
                hobbyModel.setHobby4Rate(rate);
            } else if (i == 4) {
                hobbyModel.setHobby5Name(queryUserReadList.get(i).getTypeName());
                hobbyModel.setHobby5Rate(rate);
            } else if (i == 5) {
                hobbyModel.setHobby6Name(queryUserReadList.get(i).getTypeName());
                hobbyModel.setHobby6Rate(rate);
            }
        }
        return hobbyModel;
    }

    /**
     * @Description: 写入新的兴趣模型
     * @Param: [hobbyModel]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/28
     */
    public void write(HobbyModel hobbyModel) {
        FileWriter fw = null;
        try {
            //如果文件存在，则追加内容；如果文件不存在，则创建文件
            File f = new File(new File("").getCanonicalPath() + "/hobby/hobby.txt");
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(hobbyModel.getUserId() + "," + typeMapper.getTypeId(hobbyModel.getHobby1Name()) + "," + hobbyModel.getHobby1Rate());
        try {
            if (!hobbyModel.getHobby2Name().equals(null) && hobbyModel.getHobby2Name().length() > 0) {
                pw.println(hobbyModel.getUserId() + "," + typeMapper.getTypeId(hobbyModel.getHobby2Name()) + "," + hobbyModel.getHobby2Rate());
            }
            if (!hobbyModel.getHobby3Name().equals(null) && hobbyModel.getHobby3Name().length() > 0) {
                pw.println(hobbyModel.getUserId() + "," + typeMapper.getTypeId(hobbyModel.getHobby3Name()) + "," + hobbyModel.getHobby3Rate());
            }
            if (!hobbyModel.getHobby4Name().equals(null) && hobbyModel.getHobby4Name().length() > 0) {
                pw.println(hobbyModel.getUserId() + "," + typeMapper.getTypeId(hobbyModel.getHobby4Name()) + "," + hobbyModel.getHobby4Rate());
            }
            if (!hobbyModel.getHobby5Name().equals(null) && hobbyModel.getHobby5Name().length() > 0) {
                pw.println(hobbyModel.getUserId() + "," + typeMapper.getTypeId(hobbyModel.getHobby5Name()) + "," + hobbyModel.getHobby5Rate());
            }
            if (!hobbyModel.getHobby6Name().equals(null) && hobbyModel.getHobby6Name().length() > 0) {
                pw.println(hobbyModel.getUserId() + "," + typeMapper.getTypeId(hobbyModel.getHobby6Name()) + "," + hobbyModel.getHobby6Rate());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 更新数据：1、先将用户的数据都重写格式2、再根据重写后的格式更改数据
     * @Param: [hobbyModel]
     * @return: void
     * @Author: Chen Ben
     * @Date: 2018/8/28
     */
    public void update(HobbyModel hobbyModel) throws IOException {
        List<HobbyModelDTO> hobbyModelDTOList = getHobbyModelDTOList(hobbyModel);
        List<String> txt = Files.readAllLines(Paths.get(new File("").getCanonicalPath() + "/hobby/hobby.txt"));
        List<String> replaced = new ArrayList<>();
        String[] str = {hobbyModelDTOList.get(0).getUserId() + ",1,", hobbyModelDTOList.get(0).getUserId() + ",2,",
                hobbyModelDTOList.get(0).getUserId() + ",3,", hobbyModelDTOList.get(0).getUserId() + ",4,",
                hobbyModelDTOList.get(0).getUserId() + ",5,", hobbyModelDTOList.get(0).getUserId() + ",6,",
                hobbyModelDTOList.get(0).getUserId() + ",7,", hobbyModelDTOList.get(0).getUserId() + ",8,"};
        updateTxt(txt, str, replaced, hobbyModelDTOList);
        Files.write(Paths.get(new File("").getCanonicalPath() + "/hobby/hobby.txt"), replaced);
        List<String> txt1 = Files.readAllLines(Paths.get(new File("").getCanonicalPath() + "/hobby/hobby.txt"));
        List<String> replace = new ArrayList<>();
        Double rate = 0.0;
        Integer id = 1, j = 0;
        for (String line : txt1) {
            if (line.contains("科技") || line.contains("国际") || line.contains("国内") || line.contains("军事") ||
                    line.contains("财经") || line.contains("时尚") || line.contains("娱乐") || line.contains("体育")) {
                id = typeMapper.getTypeId(hobbyModelDTOList.get(j).getHobbyName());
                rate = hobbyModelDTOList.get(j).getHobbyRate();
                replace.add(hobbyModelDTOList.get(0).getUserId() + "," + id + "," + rate);
                j++;
            } else {
                replace.add(line);
            }
        }
        Files.write(Paths.get(new File("").getCanonicalPath() + "/hobby/hobby.txt"), replace);
    }

    /**
     * @Description: 将hobbyModel中的数据分别整理到6个hobbyModelDTO中并都加在一个List中
     * @Param: [hobbyModel]
     * @return: java.util.List<com.cb.web.dto.HobbyModelDTO>
     * @Author: Chen Ben
     * @Date: 2018/8/28
     */
    public static List<HobbyModelDTO> getHobbyModelDTOList(HobbyModel hobbyModel) {
        List<HobbyModelDTO> hobbyModelDTOList = new ArrayList<>();
        HobbyModelDTO hobbyModelDTO = new HobbyModelDTO();
        hobbyModelDTO.setUserId(hobbyModel.getUserId());
        hobbyModelDTO.setHobbyName(hobbyModel.getHobby1Name());
        hobbyModelDTO.setHobbyRate(hobbyModel.getHobby1Rate());
        hobbyModelDTOList.add(hobbyModelDTO);
        HobbyModelDTO hobbyModelDTO1 = new HobbyModelDTO();
        hobbyModelDTO1.setUserId(hobbyModel.getUserId());
        hobbyModelDTO1.setHobbyName(hobbyModel.getHobby2Name());
        hobbyModelDTO1.setHobbyRate(hobbyModel.getHobby2Rate());
        hobbyModelDTOList.add(hobbyModelDTO1);
        HobbyModelDTO hobbyModelDTO2 = new HobbyModelDTO();
        hobbyModelDTO2.setUserId(hobbyModel.getUserId());
        hobbyModelDTO2.setHobbyName(hobbyModel.getHobby3Name());
        hobbyModelDTO2.setHobbyRate(hobbyModel.getHobby3Rate());
        hobbyModelDTOList.add(hobbyModelDTO2);
        HobbyModelDTO hobbyModelDTO3 = new HobbyModelDTO();
        hobbyModelDTO3.setUserId(hobbyModel.getUserId());
        hobbyModelDTO3.setHobbyName(hobbyModel.getHobby4Name());
        hobbyModelDTO3.setHobbyRate(hobbyModel.getHobby4Rate());
        hobbyModelDTOList.add(hobbyModelDTO3);
        HobbyModelDTO hobbyModelDTO4 = new HobbyModelDTO();
        hobbyModelDTO4.setUserId(hobbyModel.getUserId());
        hobbyModelDTO4.setHobbyName(hobbyModel.getHobby5Name());
        hobbyModelDTO4.setHobbyRate(hobbyModel.getHobby5Rate());
        hobbyModelDTOList.add(hobbyModelDTO4);
        HobbyModelDTO hobbyModelDTO5 = new HobbyModelDTO();
        hobbyModelDTO5.setUserId(hobbyModel.getUserId());
        hobbyModelDTO5.setHobbyName(hobbyModel.getHobby6Name());
        hobbyModelDTO5.setHobbyRate(hobbyModel.getHobby6Rate());
        hobbyModelDTOList.add(hobbyModelDTO5);
        return hobbyModelDTOList;
    }

    /**
     * @Description: 将用户的数据都重写格式
     * @Param: [txt, str, replaced, hobbyModelDTOList]
     * @return: java.util.List<java.lang.String>
     * @Author: Chen Ben
     * @Date: 2018/8/29
     */
    public static List<String> updateTxt(List<String> txt, String[] str, List<String> replaced, List<HobbyModelDTO> hobbyModelDTOList) {
        int i = 0;
        for (String line : txt) {
            if (line.contains(str[0]) || line.contains(str[1]) || line.contains(str[2]) || line.contains(str[3]) ||
                    line.contains(str[4]) || line.contains(str[5]) || line.contains(str[6]) || line.contains(str[7])) {
                replaced.add(hobbyModelDTOList.get(i).getHobbyName());
                i++;
            } else {
                replaced.add(line);
            }
        }
        while (i < hobbyModelDTOList.size()) {
            try {
                if (!hobbyModelDTOList.get(i).getHobbyName().equals(null) && hobbyModelDTOList.get(i).getHobbyName().length() > 0) {
                    replaced.add(hobbyModelDTOList.get(i).getHobbyName());
                    i++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                i++;
            }
        }
        return replaced;
    }
}
