package cn.edu.wust.miaosha.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @Author: huhan
 * @Date 2020/6/23 9:40 下午
 * @Description
 * @Verion 1.0
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private MiaoshaUserArgumentResolver argumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(argumentResolver);
    }
}
