package com.pp.community.service;

import com.pp.community.entity.Message;
import com.pp.community.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @author ss_419
 * @version 1.0
 * @date 2023/9/24 21:51
 */
@Service
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;

    public List<Message> findConversations(int userId,int offset,int limit){
        return messageMapper.selectConversations(userId, offset, limit);
    }

    public int findConversationsCount(int userId){
        return messageMapper.selectConversationCount(userId);
    }

    public List<Message> findLetters(String conversationId,int offset,int limit){
        return messageMapper.selectLetters(conversationId,offset,limit);
    }

    public int findLettersCount(String conversationId){
        return messageMapper.selectLettersCount(conversationId);
    }

    public int findLetterUnreadCount(int userId, String conversationId) {
        return messageMapper.selectLetterUnreadCount(userId,conversationId);
    }
}
