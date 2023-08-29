package com.pp.community;

import com.pp.community.entity.LoginTicket;
import com.pp.community.mapper.LoginTicketMapper;
import com.pp.community.utils.CommunityUtil;
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
 * @date 2023/8/28 19:34
 */
@RunWith(SpringRunner.class)
@SpringBootTest
// 将启动器加入该测试类，CommunityApplication类为配置类，因为CommunityApplication类内存不足则会被程序运行。
@ContextConfiguration(classes = CommunityApplication.class)
public class LoginTicketTest {

    @Autowired
    private LoginTicketMapper loginTicketMapper;
    @Test
    public void testInsert(){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(101);
        String ticket = CommunityUtil.generateUUID();
        loginTicket.setTicket(ticket);
        loginTicket.setStatus(1);
        // 设置过期时间：当前时间向后若干毫秒
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000 * 60));

        loginTicketMapper.insertLoginTicket(loginTicket);

        LoginTicket loginTicket1 = loginTicketMapper.selectByTicket(ticket);
        System.out.println("loginTicket1 = " + loginTicket1);
    }
}
