package com.pp.community.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * TODO
 *
 * @author ss_419
 * @version 1.0
 * @date 2023/8/29 09:04
 */
public class CookieUtil {
    public static String getValue(HttpServletRequest request,String name){
        if (request == null || name == null){
            throw new IllegalArgumentException("参数为空！！！");
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies){
                if (cookie.getName().equals(name)){
                    // cookie中的name等于传入的参数name
                    return cookie.getValue();
                }
            }
        }
        // 没有数据，返回null
        return null;
    }
}
