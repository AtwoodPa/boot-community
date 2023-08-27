package com.pp.community.controller;

import com.pp.community.entity.DiscussPost;
import com.pp.community.entity.Page;
import com.pp.community.entity.User;
import com.pp.community.service.DiscussPostService;
import com.pp.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author ss_419
 * @version 1.0
 * @date 2023/8/27 19:29
 */
@Controller
public class HomeController {
    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page) {
        //
        page.setRows(discussPostService.findDiscussPostRows(0));
        page.setPath("/index");
        // 查询所有文章，此时文章中没有用户信息，只有user_id
        List<DiscussPost> list = discussPostService.findDiscussPosts(0, page.getOffset(), page.getLimit());

        List<Map<String,Object>> discussPosts = new ArrayList<>();
        if (list != null) {
            for (DiscussPost post : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("post",post);
                User user = userService.findUserById(Integer.parseInt(post.getUserId()));
                map.put("user",user);

                discussPosts.add(map);
            }
        }
        // 添加参数
        model.addAttribute("discussPosts", discussPosts);
        return "index";
    }
}
