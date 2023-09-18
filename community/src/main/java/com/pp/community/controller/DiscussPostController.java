package com.pp.community.controller;

import com.pp.community.entity.DiscussPost;
import com.pp.community.entity.User;
import com.pp.community.service.DiscussPostService;
import com.pp.community.service.UserService;
import com.pp.community.utils.CommunityUtil;
import com.pp.community.utils.HostHolder;
import org.apache.catalina.startup.HostConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * TODO
 *
 * @author ss_419
 * @version 1.0
 * @date 2023/9/14 11:48
 */
@Controller
@RequestMapping("/discuss")
public class DiscussPostController {
    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addDiscussPost(String title,String content){
        // 首先判断用户是否登录
        User user = hostHolder.getUser();
        if (user == null){
            return CommunityUtil.getJSONString(403,"你还没有登录哦！");
        }
        // 初始化文章数据
        DiscussPost post = new DiscussPost();
        post.setUserId(String.valueOf(user.getId()));
        post.setTitle(title);
        post.setContent(content);
        post.setCreateTime(new Date());


        discussPostService.addDiscussPost(post);
        // 报错的情况，通过统一异常处理来解决
        return CommunityUtil.getJSONString(0,"发布成功！");
    }

    @RequestMapping(path = "/detail/{discussPostId}",method = RequestMethod.GET)
    public String getDiscussPost(@PathVariable("discussPostId") Integer discussPostId, Model model){
        // 帖子
        DiscussPost post = discussPostService.findDiscussionPostById(discussPostId);
        model.addAttribute("post", post);
        // 作者
        User user = userService.findUserById(Integer.parseInt(post.getUserId()));
        model.addAttribute("user", user);

        return "/site/discuss-detail";

    }

}
