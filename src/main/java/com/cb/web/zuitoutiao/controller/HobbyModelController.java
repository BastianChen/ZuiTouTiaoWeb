package com.cb.web.zuitoutiao.controller;

import com.cb.web.zuitoutiao.dto.UserHobbyModelDTO;
import com.cb.web.zuitoutiao.entity.User;
import com.cb.web.zuitoutiao.service.HobbyModelService;
import com.cb.web.zuitoutiao.service.UserService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 负责获取用户的兴趣模型
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-09-06 14:31
 **/
@CrossOrigin(origins = "http://localhost:8081")
@Controller
@Api(value = "HobbyModel", description = "兴趣模型接口")
@RequestMapping(value = "/HobbyModel")
public class HobbyModelController {
    @Autowired
    private HobbyModelService hobbyModelService;
    @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(HobbyModelController.class);

    /**
     * @Description:
     * @Param: []
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @Author: Chen Ben
     * @Date: 2018/9/6
     */
    @RequestMapping(value = "/getHobbyModel", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "获取兴趣模型接口", notes = "获取用户兴趣模型数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value="用户id",dataType="integer", paramType = "query",example="1")})
    public Map<String, Object> getHobbyModel(Integer userId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        UserHobbyModelDTO userHobbyModelDTO = hobbyModelService.getUserHobbyModel(userId);
        Integer totalTimes = userService.getUserTotalTimes(userId);
        if(userHobbyModelDTO.getUserId()==null){
            resultMap.put("userHobbyModelDTO","需要阅读20篇资讯才能生成兴趣模型哦!\n还需阅读"+(20-totalTimes)+"篇资讯");
        }else {
            resultMap.put("userHobbyModelDTO",userHobbyModelDTO);
        }
        return resultMap;
    }
}
