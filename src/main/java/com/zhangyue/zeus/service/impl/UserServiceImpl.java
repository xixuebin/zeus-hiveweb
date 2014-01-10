/*
 * Copyright 2013 zhangyue.com All right reserved. This software is the
 * confidential and proprietary information of Renren.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with zhangyue.com.
 */
package com.zhangyue.zeus.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zhangyue.zeus.dao.MyBatisDao;
import com.zhangyue.zeus.entity.UserEntity;
import com.zhangyue.zeus.exception.UserServiceException;
import com.zhangyue.zeus.service.IUserService;

/**
 * 用户管理服务实现类
 * 
 * @date 2013-9-6
 * @author rongneng
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private MyBatisDao myBatisDao;
    // 查询User对象
    private UserEntity userParam;

    private static final Log LOG = LogFactory.getLog(UserServiceImpl.class);

    @Override
    public void setUserEntity(UserEntity userParam) {
        this.userParam = userParam;
    }

    @Override
    public int addUser() {
        int ret = 0;
        try {
            // 先判断该用户名是否存在
            UserEntity user = queryUser();
            if (null != user) {
                ret = -1;
                LOG.info("userName " + user.getUserName() + " is exist");
                return ret;
            }
            myBatisDao.insert("userMapper.adduser", userParam);
            ret = userParam.getId();
        } catch (Exception e) {
            LOG.error("Add USER error!", e);
            throw new UserServiceException("Add USER error!", e);
        }
        return ret;
    }

    @Override
    public UserEntity queryUser() {
        UserEntity user = null;
        try {
            user = (UserEntity) myBatisDao.find("userMapper.queryUser", userParam);
        } catch (Exception e) {
            LOG.error("Query USER by UserName and Passwd", e);
            throw new UserServiceException("Query USER by UserName and Passwd", e);
        }
        return user;
    }

    @Override
    public List<UserEntity> queryUserByLevel() {
        List<UserEntity> list = null;
        try {
            list = myBatisDao.findList("userMapper.queryUser", userParam);
        } catch (Exception e) {
            LOG.error("Query USER by level", e);
            throw new UserServiceException("Query USER by level", e);
        }
        return list;
    }

    @Override
    public List<UserEntity> findUserList() {
        List<UserEntity> list = null;
        try {
            list = myBatisDao.findList("userMapper.selectByQuery");
        } catch (Exception e) {
            throw new UserServiceException("Query USER by level", e);
        }
        return list;
    }
}
