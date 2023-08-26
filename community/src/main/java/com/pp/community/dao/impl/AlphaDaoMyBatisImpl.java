package com.pp.community.dao.impl;

import com.pp.community.dao.AlphaDao;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 * TODO 业务升级，加入MyBatis
 * 重新指定  ==》 @Primary // 优先装配该bean
 * @author ss_419
 * @version 1.0
 * @date 2023/8/26 21:47
 */
@Repository
@Primary // 优先装配该bean
public class AlphaDaoMyBatisImpl implements AlphaDao {
    @Override
    public String select() {
        return "MyBatis";
    }
}
