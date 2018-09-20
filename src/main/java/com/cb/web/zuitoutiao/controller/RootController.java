package com.cb.web.zuitoutiao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author YFZX-CB-1784 ChenBen
 * @create 2018-08-20 11:54
 **/
@CrossOrigin(origins = "http://localhost:8081")
@ApiIgnore
@Controller
public class RootController {
    @RequestMapping("/")
    public String index(){
        return "test";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/image")
    public String image(){
        return "image";
    }

    @RequestMapping("/index")
    public String userIndex() {
        return "test";
    }
}
