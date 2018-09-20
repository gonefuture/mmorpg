package com.wan37.gameServer.config;

import com.github.pagehelper.PageHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;


/**
 * @author 钱伟健 gonefutre
 * @date 2017/9/12 22:27.
 * @E-mail gonefuture@qq.com
 */

/*
 * 注册MyBatis分页插件PageHelper
 */

@Configuration
@MapperScan(basePackages = {"com.wan37"})
public class MybatisConfig {

    @Bean
    public PageHelper pageHelper() {
        System.out.println("MyBatisConfiguration.pageHelper()");
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        pageHelper.setProperties(p);
        return pageHelper;
    }
}