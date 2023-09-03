package com.pp.community;

import com.pp.community.filter.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author P_P
 * @create 2023-09-03 9:16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
// 将启动器加入该测试类，CommunityApplication类为配置类，因为CommunityApplication类内存不足则会被程序运行。
@ContextConfiguration(classes = CommunityApplication.class)
public class SensitiveTest {
    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void sensitiveTest(){
        String text = "嫖娼，⭐⭐嫖⭐⭐⭐⭐娼⭐，吸⭐毒⭐⭐";
        String filter = sensitiveFilter.filter(text);
        System.out.println("filter = " + filter);
    }

}
