/*
 * Copyright 2013 zhangyue.com All right reserved. This software is the
 * confidential and proprietary information of Renren.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with zhangyue.com.
 */
package com.zhangyue.zeus.service;

import com.zhangyue.zeus.entity.QueriesEntity;
import com.zhangyue.zeus.util.Page;

/**
 * 任务管理服务接口类
 * 
 * @date 2013-9-6
 * @author rongneng
 */
public interface ITaskManageService {

    // 设置查询的参数
    public void setQuery(QueriesEntity queriesEntity);

    /**
     * 增加任务
     * 
     * @return 任务ID
     */
    public int addQueryTask();

    /**
     * 修改查询任务
     */
    public void updateQueryTask();

    /**
     * 分页查询所有任务 条件为userId/name/组合and
     * 
     * @return
     */
    public Page<QueriesEntity> findSubmitTaskPage();

    /**
     * 根据ID查询一条记录
     */
    public QueriesEntity findSubmitTaskById();

    /**
     * 根据status userId 查询任务数
     * 
     * @return 任务数
     */
    public int findSubmitTaskCount();

    /**
     * 根据ID查询任务信息
     * 
     * @param id
     * @return
     */
    public QueriesEntity findSubmitTaskById(int id);

}
