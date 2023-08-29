package com.pp.community.controller.interceptor;

import com.pp.community.annotation.LoginRequired;
import com.pp.community.utils.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * TODO
 *
 * @author ss_419
 * @version 1.0
 * @date 2023/8/29 22:21
 */
@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {
    /**
     * 存放了登录的用户信息
     */
    @Autowired
    private HostHolder hostHolder;
    /**
     * 在发起请求之前判断是否登录
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1、判断一下拦截的目标是否是方法
        if (handler instanceof HandlerMethod){
            // 转型成HandlerMethod
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取到拦截的Method对象
            Method method = handlerMethod.getMethod();
            // 尝试从方法的对象上获取注解
            LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);
            if (loginRequired != null && hostHolder.getUser() == null){
                // 有注解，表示该方法需要登录才能访问
                // 但是当前用户未登录
                // 重定向到登陆界面
                response.sendRedirect(request.getContextPath() + "/login");
                return false;
            }
        }

        return true;// 默认继续，不满足条件停止处理
    }
}
