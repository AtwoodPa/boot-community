package com.pp.community;

import com.pp.community.entity.User;
import com.pp.community.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Date;

/**
 * TODO
 *
 * @author ss_419
 * @version 1.0
 * @date 2023/8/27 17:23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
// 将启动器加入该测试类，CommunityApplication类为配置类，因为CommunityApplication类内存不足则会被程序运行。
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectUser() throws Exception {
        User user = userMapper.selectById(101);
        System.out.println("selectById = " + user);

        User user1 = userMapper.selectByName("pp");
        System.out.println("selectByName = " + user1);

        User user2 = userMapper.selectByEmail("upp419@example.com");
        System.out.println("selectByEmail = " + user2);
    }

    @Test
    public void testInsertUser() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("123123");
        user.setSalt("abc");
        user.setEmail("test@example.example.example.com");
        user.setHeaderUrl("http://www.nowcoder.com/101.png");
        user.setCreateTime(new Date());


        int rows = userMapper.insert(user);
        System.out.println("rows = " + rows);
        System.out.println(user.getId());
    }
}
