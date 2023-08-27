package com.pp.community.service;

import com.pp.community.entity.DiscussPost;
import com.pp.community.mapper.DiscussPostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO 文章业务
 *
 * @author ss_419
 * @version 1.0
 * @date 2023/8/27 19:15
 */
@Service
public class DiscussPostService {
    @Autowired
    private DiscussPostMapper discussPostMapper;

    /**
     * 查询 发帖记录（分页）
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    public List<DiscussPost> findDiscussPosts(int userId,int offset,int limit) {
        return discussPostMapper.selectDiscussPosts(userId,offset,limit);
    }

    /**
     * 查询总记录数
     * @param userId
     * @return
     */
    public int findDiscussPostRows(int userId){
        return 0;
    }

}
