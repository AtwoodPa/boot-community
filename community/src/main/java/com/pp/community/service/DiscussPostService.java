package com.pp.community.service;

import com.pp.community.entity.DiscussPost;
import com.pp.community.filter.SensitiveFilter;
import com.pp.community.mapper.DiscussPostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

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
    // 过滤敏感词
    @Autowired
    private SensitiveFilter sensitiveFilter;
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

    /**
     * 发布帖子功能-过滤敏感词
     *
     */
    public int addDiscussPost(DiscussPost post){
        if (post == null){
            throw new IllegalArgumentException("参数不能为空！！");
        }
        // 转义HTML标记
        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
        post.setContent(HtmlUtils.htmlEscape(post.getContent()));
        // 过滤敏感词
        post.setTitle(sensitiveFilter.filter(post.getTitle()));
        post.setContent(sensitiveFilter.filter(post.getContent()));
        return discussPostMapper.insert(post);
    }

    public DiscussPost findDiscussionPostById(Integer id){
        return discussPostMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新 帖子评论数量
     * @param id
     * @param commentCount
     * @return
     */
    public int updateCommentCount(int id,int commentCount){
        return discussPostMapper.updateCommentCount(id,commentCount);
    }

}
