package com.pp.community.service;

import com.pp.community.entity.User;
import com.pp.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author ss_419
 * @version 1.0
 * @date 2023/8/27 19:20
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findUserById(int id){
        return userMapper.selectById(id);
    }
}
