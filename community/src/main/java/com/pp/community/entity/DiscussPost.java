package com.pp.community.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 帖子表
 * t_discuss_post
 * @author ss_419
 */
@Data
public class DiscussPost implements Serializable {
    private Integer id;

    private String userId;

    private String title;

    private String content;

    /**
     * 0-普通；1-置顶
     */
    private Integer type;

    /**
     * 0-正常；1-精华；2-拉黑
     */
    private Integer status;

    private Date createTime;

    private Integer commentCount;

    private Double score;

    private static final long serialVersionUID = 1L;
}