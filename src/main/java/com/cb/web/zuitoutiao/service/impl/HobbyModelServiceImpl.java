package com.cb.web.zuitoutiao.service.impl;

import com.cb.web.zuitoutiao.dao.HobbyModelMapper;
import com.cb.web.zuitoutiao.dao.UserMapper;
import com.cb.web.zuitoutiao.dto.UserHobbyModelDTO;
import com.cb.web.zuitoutiao.entity.HobbyModel;
import com.cb.web.zuitoutiao.entity.User;
import com.cb.web.zuitoutiao.service.HobbyModelService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-09-06 14:33
 **/
@Service("HobbyModelService")
public class HobbyModelServiceImpl implements HobbyModelService {
    @Autowired
    private HobbyModelMapper hobbyModelMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * @Description: 获取用户的兴趣模型数据
     * @Param: [userId]
     * @return: com.cb.web.zuitoutiao.dto.UserHobbyModelDTO
     * @Author: Chen Ben
     * @Date: 2018/9/6
     */
    @Override
    public UserHobbyModelDTO getUserHobbyModel(Integer userId) {
        UserHobbyModelDTO userHobbyModelDTO = new UserHobbyModelDTO();
        HobbyModel hobbyModel = hobbyModelMapper.getHobbyModel(userId);
        if(hobbyModel!=null){
            User user = userMapper.getUserNameById(userId);
            BeanUtils.copyProperties(hobbyModel, userHobbyModelDTO);
            userHobbyModelDTO.setUserName(user.getUserName());
            return userHobbyModelDTO;
        }else {
            return userHobbyModelDTO;
        }
    }
}
