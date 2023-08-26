package com.pp.community.dao.impl;

import com.pp.community.dao.AlphaDao;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author ss_419
 * @version 1.0
 * @date 2023/8/26 21:40
 */
@Repository
public class AlphaDaoHibernateImpl implements AlphaDao {
    @Override
    public String select() {
        return "Hibernate";
    }
}
