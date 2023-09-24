package com.pp.community.mapper;

import com.pp.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ss_419
 */
@Mapper
public interface DiscussPostMapper {
    List<DiscussPost> selectDiscussPosts(int userId, int offset,int limit);
    // @Param注解用于给参数取别名
    // 如果只有一个参数，并且在<if>里使用，则必须加别名
    int selectDiscussPostRows(@Param("userId") Integer userId);
    int deleteByPrimaryKey(Integer id);

    int insert(DiscussPost record);

    int insertSelective(DiscussPost record);

    DiscussPost selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DiscussPost record);

    int updateByPrimaryKey(DiscussPost record);

    int updateCommentCount(int id, int commentCount);
}