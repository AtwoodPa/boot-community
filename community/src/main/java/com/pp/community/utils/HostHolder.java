package com.pp.community.utils;

import com.pp.community.entity.User;
import org.springframework.stereotype.Component;

/**
 * TODO 持有用户的信息，用于代替Session对象
 *
 * @author ss_419
 * @version 1.0
 * @date 2023/8/29 09:13
 */
@Component
public class HostHolder {
    /**
     * 创建一个以线程为key，进行存取值的ThreadLocal
     */
    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user) {
        users.set(user);
    }

    public User getUser() {
        return users.get();
    }
    /**
     * 清理 ThreadLocal，除了防止在2个请求中添加的或删除的用户
     */
    public void clear(){
        users.remove();
    }
}
