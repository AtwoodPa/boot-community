package com.pp.community.utils;

import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * TODO
 *
 * @author ss_419
 * @version 1.0
 * @date 2023/8/28 10:40
 */
public class CommunityUtil {

    // 生成随机字符串
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    // md5加密
    public static String md5(String key){
        if (StringUtils.isBlank(key)){
            return null;
        }
        // 调用Spring工具类DigestUtils
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

    /**
     * TODO 处理json字符串的方法
     *
     */
    // 给浏览器返回编码
    public static String getJSONString(int code, String msg, Map<String,Object> map){
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        if (map != null){
            for(String key: map.keySet()){
                json.put(key, map.get(key));
            }
        }
        return json.toJSONString();
    }

    public static String getJSONString(int code, String msg){
        return getJSONString(code, msg);
    }

    public static String getJSONString(int code){
        return getJSONString(code);
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("name","盼盼");
        map.put("age",25);

        System.out.println(getJSONString(200, "success", map));

    }
}
