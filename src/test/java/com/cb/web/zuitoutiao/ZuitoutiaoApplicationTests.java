package com.cb.web.zuitoutiao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZuitoutiaoApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void pathTest() throws FileNotFoundException {
        File file = new File(System.getProperty("user.dir") + "/src/main/resources/static/images/");
        System.out.println(file);
    }
}
