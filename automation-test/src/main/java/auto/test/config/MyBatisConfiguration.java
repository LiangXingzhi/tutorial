package com.ericsson.ct.cloud.nfvi.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class MyBatisConfiguration {
    @Bean
    public PageHelper pageHelper(){
        System.out.println("MyBatisConfiguration.pagerHelper()");
        PageHelper pageHelper= new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum","true");
        p.setProperty("rowBoundsWithCount","true");
        p.setProperty("reasonable","true");
        p.setProperty("dialect","derby");
        pageHelper.setProperties(p);
        return pageHelper;
    }
}