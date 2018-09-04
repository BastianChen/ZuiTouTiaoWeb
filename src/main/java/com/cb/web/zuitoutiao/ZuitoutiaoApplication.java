package com.cb.web.zuitoutiao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cb.web.zuitoutiao.dao")
public class ZuitoutiaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuitoutiaoApplication.class, args);
    }
}
