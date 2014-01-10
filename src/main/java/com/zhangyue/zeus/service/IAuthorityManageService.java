/*
 * Copyright 2014 zhangyue.com All right reserved. This software is the
 * confidential and proprietary information of Renren.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with zhangyue.com.
 */
package com.zhangyue.zeus.service;

import com.zhangyue.zeus.vo.CheckResult;

/**
 * 权限管理服务接口
 * 
 * @date 2014-9-6
 * @author rongneng
 */
public interface IAuthorityManageService {

    /**
     * 校验用户查询表的权限验证
     * 
     * @param userId
     * @param sql
     * @return
     */
    public CheckResult checkPermission(int userId, String sql);

}
