package com.pp.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * TODO
 *
 * @author ss_419
 * @version 1.0
 * @date 2023/8/26 22:30
 */
@Configuration
public class AlphaConfig {

    /**
     * 该bean的默认名称是simpleDateFormat
     * @return
     */
    @Bean
    public SimpleDateFormat simpleDateFormat(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
