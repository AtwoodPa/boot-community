package com.pp.community.config;

import com.pp.community.annotation.LoginRequired;
import com.pp.community.controller.interceptor.AlphaInterceptor;
import com.pp.community.controller.interceptor.LoginRequiredInterceptor;
import com.pp.community.controller.interceptor.LoginTicketInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * TODO MVC配置类
 * 该配置类需要实现WebMvcConfigurer
 *
 * @author ss_419
 * @version 1.0
 * @date 2023/8/29 08:48
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private AlphaInterceptor alphaInterceptor;
    @Autowired
    private LoginTicketInterceptor loginTicketInterceptor;
    /**
     * 该拦截器拦截了带有LoginRequired注解的所有方法
     */
    @Autowired
    private LoginRequiredInterceptor loginRequired;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(alphaInterceptor)// 默认拦截全部
                // 排除静态资源
                .excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg")
                // 指定拦截路径
                .addPathPatterns("/register", "/login");

        registry.addInterceptor(loginTicketInterceptor)// 默认拦截全部
                // 排除静态资源
                .excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg");

        /**
         * 该拦截器拦截了带有LoginRequired注解的所有方法
         */
        registry.addInterceptor(loginRequired)// 默认拦截全部
                // 排除静态资源
                .excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg");
    }
}
