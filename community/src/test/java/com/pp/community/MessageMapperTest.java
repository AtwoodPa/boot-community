package com.pp.community;

import com.pp.community.entity.Message;
import com.pp.community.mapper.MessageMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @author ss_419
 * @version 1.0
 * @date 2023/9/24 12:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest
// 将启动器加入该测试类，CommunityApplication类为配置类，因为CommunityApplication类内存不足则会被程序运行。
@ContextConfiguration(classes = CommunityApplication.class)
public class MessageMapperTest {
    @Autowired
    private MessageMapper mapper;

    @Test
    public void testSelectConversations(){
        List<Message> messages = mapper.selectConversations(111, 0, 0);
        if (messages != null) {
            messages.stream()
                    .forEach(message -> System.out.println(message.toString()));
        }
    }

    @Test
    public void testInsertConversations(){
        Message record = new Message();
        record.setContent("Test message");
        record.setCreateTime(new Date());
        record.setStatus(0);
        record.setToId(111);




        int insert = mapper.insert(record);
        System.out.println("insert = " + insert);
    }
}
