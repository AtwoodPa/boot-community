package com.pp.community;

import com.pp.community.utils.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * TODO
 *
 * @author ss_419
 * @version 1.0
 * @date 2023/8/28 09:48
 */
@RunWith(SpringRunner.class)
@SpringBootTest
// 将启动器加入该测试类，CommunityApplication类为配置类，因为CommunityApplication类内存不足则会被程序运行。
@ContextConfiguration(classes = CommunityApplication.class)
public class JavaMailTest {

    @Autowired
    private MailClient mailClient;
    // 引入模版引擎
    @Autowired
    private TemplateEngine templateEngine;
    @Test
    public void testEmailProblem() throws Exception {
       mailClient.sendMail("supanpan199919@163.com","Test","Test Java Mail");
    }

    @Test
    public void testHtmlEmail(){
        // 构建内容模版
        Context context = new Context();
        // 设置参数
        context.setVariable("username","monica");
        // 生成html网页
        String content = templateEngine.process("/mail/demo", context);
        System.out.println("content = " + content);
        for (int i = 0; i < 20; i++) {
            mailClient.sendMail("2241856755@qq.com", "测试HTML网页", content);
        }
    }

}
