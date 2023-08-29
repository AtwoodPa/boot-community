package com.pp.community.utils;

public interface CommunityConstant {
    /**
     * 激活成功
     */
    int ACTIVATION_SUCCESS = 0;
    /**
     * 重复激活
     */
    int ACTIVATION_REPEAT = 1;
    /**
     * 激活失败
     */
    int ACTIVATION_FAILURE = 2;
    /**
     * 默认的超时时间
     */
    long DEFAULT_EXPIRED_SECONDS = 3600 * 12;
    /**
     * 记住我超时时间
     */
    long REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 100;
}
