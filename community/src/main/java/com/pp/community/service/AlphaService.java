package com.pp.community.service;

import com.pp.community.dao.AlphaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * TODO 测试生命周期，由容器管理创建、初始化、销毁等操作
 *
 * @author ss_419
 * @version 1.0
 * @date 2023/8/26 21:59
 */
@Service
// 单例模式
//@Scope("singleton")
// 解除单例模式，每一次访问bean就会创建一个实例
//@Scope("prototype")
public class AlphaService {
    @Autowired
    private AlphaDao alphaDao;

    public String select(){
        String select = alphaDao.select();
        return select;
    }

    public AlphaService() {
        System.out.println("实例化AlphaService");
    }

    // 该方法在构造器之后调用
    @PostConstruct
    public void init(){
        System.out.println("初始化AlphaService");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("销毁AlphaService");
    }
}
