package com.cb.web.zuitoutiao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = MongoAutoConfiguration.class)
@MapperScan("com.cb.web.zuitoutiao.dao")
@EnableScheduling
public class ZuitoutiaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuitoutiaoApplication.class, args);
    }
}
