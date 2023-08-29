package com.pp.community.controller.interceptor;

import com.pp.community.entity.LoginTicket;
import com.pp.community.entity.User;
import com.pp.community.service.UserService;
import com.pp.community.utils.CookieUtil;
import com.pp.community.utils.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * TODO 登录凭证拦截器，取代session
 * 拦截器需实现HandlerInterceptor
 *
 * @author ss_419
 * @version 1.0
 * @date 2023/8/29 09:03
 */
@Component
public class LoginTicketInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Autowired
    private HostHolder hostHolder;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从cookie中获取数据
        String ticket = CookieUtil.getValue(request,"ticket");
        if (ticket != null){
            // 查询凭证
            LoginTicket loginTicket = userService.findLoginTicket(ticket);
            // 检查凭证是否有效
            if (loginTicket != null && loginTicket.getStatus() == 0 && loginTicket.getExpired().after(new Date())){
                // 根据凭证查询用户
                User user = userService.findUserById(loginTicket.getUserId());
                // 在本次请求中持有用户【考虑多线程】，线程隔离ThreadLocal
                hostHolder.setUser(user);// 在本次请求中持有用户
            }
        }
        return true;// 表示可以继续向下执行
    }
    /**
     * 在模板引擎之前使用拦截
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();
        if (user != null && modelAndView != null) {
            // TODO 拦截器-在模板引擎之前获得User信息
            modelAndView.addObject("loginUser",user);
        }
    }

    /**
     * 模板执行结束，需清除用户信息
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
