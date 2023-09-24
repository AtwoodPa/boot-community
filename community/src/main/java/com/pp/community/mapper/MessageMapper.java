package com.pp.community.mapper;

import com.pp.community.entity.Message;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface MessageMapper {
    //查询当前用户的会话列表，针对每个会话只返回一条最新的私信
    List<Message> selectConversations(int userId,int offset,int limit);
    // 查询当前用户的会话数量
    int selectConversationCount(int userId);
    // 查询某个会话所包含的私信列表
    List<Message> selectLetters(String conversationId,int offset,int limit);
    // 查询某个会话所包含的私信数量
    int selectLettersCount(String conversationId);
    // 查询未读私信的数量
    int selectLetterUnreadCount(int userId,String conversationId);

    int deleteByPrimaryKey(Integer id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);
}