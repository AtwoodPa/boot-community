package com.pp.community;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 牛客社区
 * @Date 2023-08-26
 * @author ss_419
 */
@SpringBootApplication// 拥有该注解的类，表明是一个配置文件
@MapperScan("com.pp.community.mapper")
public class CommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommunityApplication.class, args);
	}

}
