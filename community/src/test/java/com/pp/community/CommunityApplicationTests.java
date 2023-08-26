package com.pp.community;

import com.pp.community.dao.AlphaDao;

import com.pp.community.service.AlphaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
// 将启动器加入该测试类，CommunityApplication类为配置类，因为CommunityApplication类内存不足则会被程序运行。
@ContextConfiguration(classes = CommunityApplication.class)
public class CommunityApplicationTests implements ApplicationContextAware {
	// 暂存容器对象
	private ApplicationContext applicationContext;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// 得到Spring容器，实现ApplicationContextAware
		this.applicationContext = applicationContext;
	}


	/**
	 * 测试Spring容器
	 */
	@Test
	public void contextLoads() {
		System.out.println(applicationContext );
	}

	/**
	 * 获取自动装配的bean
	 */
	@Test
	public void testGetAutoBean(){
		// 根据默认装配接口类型Bean来获取
		AlphaDao bean = applicationContext.getBean(AlphaDao.class);
		System.out.println("bean.select() = " + bean.select());
		System.out.println("================================");
		// 根据名称来获取Bean，默认bean的名称是首字母小写的驼峰命名
		AlphaDao alphaDaoHibernateImpl = (AlphaDao) applicationContext.getBean("alphaDaoHibernateImpl");
		System.out.println("alphaDaoHibernateImpl = " + alphaDaoHibernateImpl.select());
	}
	/**
	 * 测试Bean的生命周期
	 *
	 */
	@Test
	public void testBeanInitAndDestroy(){
		// 根据name获取bean对象
		AlphaService alphaService = (AlphaService) applicationContext.getBean("alphaService");
		// bean只被实例化一次，只被销毁一次，它是单例的
		System.out.println("alphaService = " + alphaService);
	}


	@Test
	public void getMyBean(){
		SimpleDateFormat simpleDateFormat =
				(SimpleDateFormat) applicationContext.getBean("simpleDateFormat");
		System.out.println(simpleDateFormat.format(new Date()));
	}

	// 自动注册bean
	@Autowired
	@Qualifier("alphaDaoHibernateImpl")// 指定bean的实现类
	private AlphaDao alphaDao;

	@Test
	public void testBeanAutowired(){
		System.out.println(alphaDao);
	}
}
