package com.example.demo.config.beetl;

import org.beetl.core.resource.WebAppResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

import java.io.IOException;

/**
 * web beetl模版配置类
 *
 * @author panda
 * @date 2016年11月12日 下午5:03:32
 */
@Configuration
public class BeetlConf {

    @Value("${spring.beetl.config}")
    private String beetConf;

    @Bean(initMethod = "init", name = "beetlConfig")
    public BeetlGroupUtilConfiguration beetlConfiguration() {

        ResourcePatternResolver patternResolver = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader());
        BeetlGroupUtilConfiguration beetlConfiguration = new BeetlGroupUtilConfiguration();
        WebAppResourceLoader webAppResourceLoader = null;
        try {
            webAppResourceLoader = new WebAppResourceLoader(patternResolver.getResource("/templates").getFile().getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        beetlConfiguration.setResourceLoader(webAppResourceLoader);
        //读取配置文件信息
        beetlConfiguration.setConfigFileResource(patternResolver.getResource(beetConf));
        return beetlConfiguration;
    }

    @Bean(name = "beetlViewResolver")
    public BeetlSpringViewResolver getBeetlSpringViewResolver(@Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlConfiguration) {
        BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
        beetlSpringViewResolver.setPrefix("/");
        beetlSpringViewResolver.setSuffix(".html");
        beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
        beetlSpringViewResolver.setOrder(0);
        beetlSpringViewResolver.setConfig(beetlConfiguration);
        return beetlSpringViewResolver;
    }
}
