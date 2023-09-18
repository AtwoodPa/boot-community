package com.pp.community.service;

import com.pp.community.dao.AlphaDao;
import com.pp.community.entity.DiscussPost;
import com.pp.community.entity.User;
import com.pp.community.mapper.DiscussPostMapper;
import com.pp.community.mapper.UserMapper;
import com.pp.community.utils.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.objenesis.ObjenesisHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;

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

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    // 编程式事务
    @Autowired
    private TransactionTemplate transactionTemplate;

    public String select(){
        String select = alphaDao.select();
        return select;
    }
    // REQUIRED ： 支持当前事务（外部事务），如果不存在则创建新事务
    // REQUIRES_NEW：创建一个新事务，并且暂停当前事务（外部事务）
    // NESTED： 如果当前存在事务（外部事务），则嵌套在该事务中执行（独立的提交和回滚），否则和REQUIRED一样
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public Object save1(){
        // 新增用户
        User user = new User();
        user.setUsername("P_P_419");
        user.setSalt(CommunityUtil.generateUUID().substring(0,5));
        user.setPassword(CommunityUtil.md5("123" + user.getSalt()));
        user.setEmail("pp199919@pp.com");
        user.setHeaderUrl("123123");
        user.setCreateTime(new Date());
        userMapper.insertUser(user);
        // 新增帖子
        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId().toString());
        post.setTitle("Hello%%% %%开%%%票%%");
        post.setContent("新任报道可以==》%%% %%开%%%票%%");
        post.setCreateTime(new Date());
        discussPostMapper.insert(post);

        // 人工造错
        Integer.valueOf("abc");
        return "ok";
    }

    /**
     * 编程式事务
     * @return
     */
    public Object save2(){
        // 使用transactionTemplate设置事务属性
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        // 执行事务
        return transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {
                // 事务逻辑
                // 新增用户
                User user = new User();
                user.setUsername("P_P_419");
                user.setSalt(CommunityUtil.generateUUID().substring(0,5));
                user.setPassword(CommunityUtil.md5("123" + user.getSalt()));
                user.setEmail("pp199919@pp.com");
                user.setHeaderUrl("123123");
                user.setCreateTime(new Date());
                userMapper.insertUser(user);
                // 新增帖子
                DiscussPost post = new DiscussPost();
                post.setUserId(user.getId().toString());
                post.setTitle("Hello%%% %%开%%%票%%");
                post.setContent("新任报道可以==》%%% %%开%%%票%%");
                post.setCreateTime(new Date());
                discussPostMapper.insert(post);

                // 人工造错
                Integer.valueOf("abc");
                return "ok";
            }
        });
    }
//
//    public AlphaService() {
//        System.out.println("实例化AlphaService");
//    }

    // 该方法在构造器之后调用
//    @PostConstruct
//    public void init(){
//        System.out.println("初始化AlphaService");
//    }
//
//    @PreDestroy
//    public void destroy(){
//        System.out.println("销毁AlphaService");
//    }
//

}
