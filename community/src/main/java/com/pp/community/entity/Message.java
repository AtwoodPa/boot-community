package com.pp.community.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_message
 */
@Data
public class Message implements Serializable {
    private Integer id;

    private Integer fromId;

    private Integer toId;

    private String conversationId;

    private String content;

    private Integer status;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}