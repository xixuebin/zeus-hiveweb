/*
 * Copyright 2013 zhangyue.com All right reserved. This software is the
 * confidential and proprietary information of Renren.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with zhangyue.com.
 */
package com.zhangyue.zeus.dao;

import java.util.List;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.zhangyue.zeus.entity.Entity;

/**
 * 系统中公共的Dao
 * 
 * @date 2013-10-5
 * @author rongneng
 */
@Repository("myBatisDao")
public class MyBatisDao extends SqlSessionDaoSupport {

    public int insert(String key, Object object) throws Exception {
        return getSqlSession().insert(key, object);
    }

    public int batchInsert(String key, List<?> list) throws Exception {
        return getSqlSession().insert(key, list);
    }

    public void delete(String key, Object object) throws Exception {
        getSqlSession().delete(key, object);
    }

    public int batchDelete(String key, List<?> list) throws Exception {
        return getSqlSession().delete(key, list);
    }

    public void update(String key, Object object) throws Exception {
        getSqlSession().update(key, object);
    }

    public int batchUpdate(String key, List<?> list) throws Exception {
        return getSqlSession().update(key, list);
    }

    public Object find(String key, Object params) throws Exception {
        return getSqlSession().selectOne(key, params);
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> List<T> findList(String key) throws Exception {
        return getSqlSession().selectList(key);
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> List<T> findList(String key, Object params) throws Exception {
        return getSqlSession().selectList(key, params);
    }

}
