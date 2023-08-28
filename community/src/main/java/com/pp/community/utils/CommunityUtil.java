package com.pp.community.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

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
}
