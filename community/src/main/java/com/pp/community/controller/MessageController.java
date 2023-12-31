package com.pp.community.controller;

import com.pp.community.entity.Message;
import com.pp.community.entity.Page;
import com.pp.community.entity.User;
import com.pp.community.service.MessageService;
import com.pp.community.service.UserService;
import com.pp.community.utils.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
 * @date 2023/9/24 21:55
 */
@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private UserService userService;

    // 私信列表
    @RequestMapping(path = "/letter/list", method = RequestMethod.GET)
    public String getLetterList(Model model , Page page){
        // 当前用户信息
        User user = hostHolder.getUser();
        // 分页信息
        page.setLimit(5);
        page.setPath("letter/list");
        page.setRows(messageService.findConversationsCount(user.getId()));
        // 会话列表
        List<Message> conversationList =
                messageService.findConversations(user.getId(), page.getOffset(),page.getLimit());
        List<Map<String,Object>> conversations = new ArrayList<>();
        if (conversationList != null){
            for (Message message : conversationList){
                // 创建私信对象，封装一些信息
                Map<String, Object> map = new HashMap<>();
                map.put("conversation", message);
                map.put("letterCount", messageService.findLettersCount(message.getConversationId()));
                map.put("unreadCount", messageService.findLetterUnreadCount(user.getId(), message.getConversationId()));
                int targetId = (user.getId().equals(message.getFromId()) ? message.getToId() : message.getFromId());
                map.put("target",userService.findUserById(targetId));

                conversations.add(map);
            }
        }
        model.addAttribute("conversations",conversations);
        // 查询未读消息数量
        int letterUnreadCount = messageService.findLetterUnreadCount(user.getId(),null);
        model.addAttribute("letterUnreadCount", letterUnreadCount);

        return "/site/letter";
    }
    @RequestMapping(path = "letter/detail/{conversationId}")
    public String getLetterDetail(@PathVariable("conversationId") String conversationId,Page page,Model model){
        // 分页信息
        page.setLimit(5);
        page.setPath("/letter/detail/ "+ conversationId);
        page.setRows(messageService.findLettersCount(conversationId));

        // 私信列表
        List<Message> letterList = messageService.findLetters(conversationId,page.getOffset(), page.getLimit());
        List<Map<String,Object>> letters = new ArrayList<>();
        if (letterList != null){
            for (Message message : letterList){
                Map<String , Object> map = new HashMap<>();
                map.put("letter", message);
                map.put("formUser",userService.findUserById(message.getFromId()));
                letters.add(map);
            }
        }
        model.addAttribute("letters",letters);
        // 私信目标
        model.addAttribute("target",getLetterTarget(conversationId));
        return "/site/letter-detail";
    }

    private User getLetterTarget(String conversationId){
        String[] ids = conversationId.split("_");
        int id0 = Integer.parseInt(ids[0]);
        int id1 = Integer.parseInt(ids[1]);

        if (hostHolder.getUser().getId() == id0){
            return userService.findUserById(id1);
        }else {
            return userService.findUserById(id0);
        }
    }
}
