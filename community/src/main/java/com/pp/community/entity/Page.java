package com.pp.community.entity;


import lombok.Data;

/**
 * TODO 封装分页相关的信息
 *
 * @author ss_419
 * @version 1.0
 * @date 2023/8/27 20:00
 */
@Data
public class Page {
    // 当前页码
    private int current = 1;
    // 显示上限
    private int limit = 10;
    // 数据总数total（用于计算总页数）
    private int rows;
    // 查询路径（用于复用分页链接）
    private String path;

    /**
     * 获取当前页的起始行
     */
    public int getOffset(){
        return (current - 1) * limit;
    }
    /**
     * 获取总页数
     */
    public int getTotal(){
        if (rows % limit == 0){
            return rows / limit;
        }else {
            return rows / limit + 1;
        }
    }
    /**
     * 获取起始页码
     */
    public int getFrom(){
        int from = current - 2;
        return from < 1 ? 1 : from;
    }
    /**
     * 获取结束页码
     */
    public int getTo(){
        int to = current + 2;
        int total = getTotal();
        return to > total? total : to;
    }
}
