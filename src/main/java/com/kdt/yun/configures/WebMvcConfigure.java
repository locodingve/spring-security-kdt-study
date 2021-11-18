package com.kdt.yun.configures;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by yunyun on 2021/11/11.
 */

@Configuration
public class WebMvcConfigure implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/me").setViewName("me");
        registry.addViewController("/admin").setViewName("admin");
    }
}
