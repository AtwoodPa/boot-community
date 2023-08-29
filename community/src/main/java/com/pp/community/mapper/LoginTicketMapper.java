package com.pp.community.mapper;

import com.pp.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * 使用注解方式写入SQL
 */
@Mapper
public interface LoginTicketMapper {

    @Insert({
            "INSERT INTO login_ticket(user_id, ticket,status,expired) ",
            "values (#{userId},#{ticket},#{status},#{expired}) "
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);
    @Select("SELECT id,user_id, ticket,status,expired FROM `login_ticket` WHERE ticket=#{ticket}")
    LoginTicket selectByTicket(String ticket);

    /**
     * 修改状态
     *
     * @param ticket
     * @param status
     * @return
     */
    @Update("UPDATE login_ticket SET `status` = #{status} WHERE ticket = #{ticket}")
    int updateStatus(String ticket, int status);

    int deleteByPrimaryKey(Integer id);

    int insert(LoginTicket record);

    int insertSelective(LoginTicket record);

    LoginTicket selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LoginTicket record);

    int updateByPrimaryKey(LoginTicket record);
}