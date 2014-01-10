/*
 * Copyright 2013 zhangyue.com All right reserved. This software is the
 * confidential and proprietary information of Renren.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with zhangyue.com.
 */
package com.zhangyue.zeus.service;

import java.util.List;

import com.zhangyue.zeus.entity.UserEntity;
import com.zhangyue.zeus.exception.UserServiceException;

/**
 * user服务接口类
 * 
 * @date 2013-9-6
 * @author rongneng
 */
public interface IUserService {

    /**
     * 设置User对象，User保存查询的参数 在调用一下方法时设置User
     * 
     * @param vo
     */
    public void setUserEntity(UserEntity userEntity);

    /**
     * 添加用户，插入时先判断是否存在该用户
     * 
     * @param user
     * @return 添加用户的ID -1---该用户名已存在
     */
    public int addUser() throws UserServiceException;

    /**
     * 根据用户名及密码查询用户
     * 
     * @return User
     */
    public UserEntity queryUser() throws UserServiceException;

    /**
     * 根据用户等级查询等级对应的所有用户
     * 
     * @param level
     * @return List<User>
     */
    public List<UserEntity> queryUserByLevel() throws UserServiceException;

    /**
     * 查询全部的用户
     * 
     * @return
     */
    public List<UserEntity> findUserList() throws UserServiceException;
}
