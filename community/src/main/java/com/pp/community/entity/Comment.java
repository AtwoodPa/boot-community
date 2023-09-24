package com.pp.community.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_comment
 */
@Data
public class Comment implements Serializable {
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    private Integer entityType;

    private Integer entityId;

    private Integer targetId;

    private String content;

    private Integer status;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}