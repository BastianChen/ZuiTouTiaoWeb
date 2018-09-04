package com.cb.web.zuitoutiao.controller;

import com.cb.web.zuitoutiao.entity.User;
import com.cb.web.zuitoutiao.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-09-04 15:58
 **/
@RestController
@Api(value = "user",description = "用户接口")
@RequestMapping("/User")
public class UserController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @ApiResponses(value = {@ApiResponse(code = 200, message = "获取成功"), @ApiResponse(code = 408, message = "接口异常"),})
    @RequestMapping(value = "/show", method=RequestMethod.GET, produces = { "application/json" })
    @ApiOperation(value="用户接口", notes="根据用户账号搜索出用户")
    public User getUser(@ApiParam(required=true, name="accountName", value="账号名")
                            @RequestParam(name = "accountName", required=true) String accountName){
        return userService.getUser(accountName);
    }
}
