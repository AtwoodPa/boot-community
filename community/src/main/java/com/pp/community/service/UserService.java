package com.pp.community.service;

import com.pp.community.entity.LoginTicket;
import com.pp.community.entity.User;
import com.pp.community.mapper.LoginTicketMapper;
import com.pp.community.mapper.UserMapper;
import com.pp.community.utils.CommunityConstant;
import com.pp.community.utils.CommunityUtil;
import com.pp.community.utils.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.*;

/**
 * TODO
 *
 * @author ss_419
 * @version 1.0
 * @date 2023/8/27 19:20
 */
@Service
public class UserService implements CommunityConstant {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginTicketMapper loginTicketMapper;
    /**
     * 注册业务
     */
    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;
    public User findUserById(int id){
        return userMapper.selectById(id);
    }

    public Map<String, Object> register(User user){
        Map<String, Object> map = new HashMap<>();
        if (user == null){
            throw new IllegalArgumentException("非法参数");
        }
        // 账号是空的
        if (StringUtils.isBlank(user.getUsername())){
            map.put("usernameMsg","账号不能为空");
            return map;
        }
        if (StringUtils.isBlank(user.getPassword())){
            map.put("passwordMsg","密码不能为空");
            return map;
        }
        if (StringUtils.isBlank(user.getEmail())){
            map.put("emailMsg","邮箱不能为空");
            return map;
        }
        // 验证账号是否有重复
        User selectByName = userMapper.selectByName(user.getUsername());
        if (selectByName != null) {
            // 账号已存在
            map.put("usernameMsg","该用户名已经被注册啦");
            return map;
        }
        // 验证邮箱是否存在
        User userBySelect = userMapper.selectByEmail(user.getEmail());
        if (userBySelect != null){
            // 邮箱已经存在
            map.put("emailMsg", "该邮箱已经被注册了");
            return map;
        }

        // 注册账号
        // 设置盐值
        user.setSalt(CommunityUtil.generateUUID().substring(0,5));
        // md5 + salt
        user.setPassword(CommunityUtil.md5(user.getPassword()+ user.getSalt()) );
        user.setType(0);
        user.setStatus(0);
        user.setActivation(CommunityUtil.generateUUID());
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        user.setCreateTime(new Date());

        userMapper.insertUser(user);

        // 发送激活邮件（html）
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        // 激活路径 http://localhost:8080/community/activation/101/code
        String url = domain + contextPath + "/activation" + user.getId() + "/" + user.getActivation();
        context.setVariable("url",url);
        // 指定模板引擎
        String content = templateEngine.process("/mail/activation", context);
        // 发送html邮件
        mailClient.sendMail(user.getEmail(),"激活账号",content);

        return map;
    }


    /**
     * 激活业务
     */
    public int activation(int userId, String code){
        User user = userMapper.selectById(userId);
        if (user.getStatus() == 1){
            return ACTIVATION_REPEAT;// 重复激活
        }else if (user.getActivation().equals(code)){
            userMapper.updateStatus(userId,1);
            return ACTIVATION_SUCCESS;
        }else {
            return ACTIVATION_FAILURE;
        }
    }

    /**
     * 用户登录操作
     */
    public Map<String,Object> login(String username,String password,long expiredSeconds){
        Map<String,Object> map = new HashMap<>();
        // 空值处理
        if (StringUtils.isBlank(username)){
            map.put("usernameMsg","账号不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)){
            map.put("passwordMsg","密码不能为空");
            return map;
        }
        // 验证账号
        User user = userMapper.selectByName(username);
        if (user == null){
            map.put("usernameMsg","该账号不存在");
            return map;
        }
        // 验证状态
        if (user.getStatus() == 0){
            map.put("usernameMsg","该账号未激活");
            return map;        }
        // 验证密码
        password = CommunityUtil.md5(password + user.getSalt());
        if (!user.getPassword().equals(password)){
            map.put("passwordMsg","密码不正确！！");
            return map;
        }
        // 生成登录凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommunityUtil.generateUUID());
        loginTicket.setStatus(0);
        // 设置过期时间：当前时间向后若干毫秒
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));
        loginTicketMapper.insertLoginTicket(loginTicket);
        map.put("ticket", loginTicket.getTicket());
        return map;
    }

    public void logout(String ticket) {
        // ticket 1 表示无效
        loginTicketMapper.updateStatus(ticket,1);
    }

    public LoginTicket findLoginTicket(String ticket){
        return loginTicketMapper.selectByTicket(ticket);
    }

    public int updateHeader(int userId,String headerUrl){
        return userMapper.updateHeader(userId, headerUrl);
    }
}
