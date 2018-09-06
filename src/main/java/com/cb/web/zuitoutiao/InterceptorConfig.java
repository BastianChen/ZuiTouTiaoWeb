package com.cb.web.zuitoutiao;

import com.cb.web.zuitoutiao.interceptor.UserInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Description:
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-09-06 10:55
 **/
@Configuration
@AllArgsConstructor
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    private UserInterceptor userInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor).addPathPatterns("/index");
    }
}
