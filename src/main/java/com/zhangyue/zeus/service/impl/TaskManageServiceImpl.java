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
import com.zhangyue.zeus.entity.QueriesEntity;
import com.zhangyue.zeus.exception.HiveTableServiceException;
import com.zhangyue.zeus.service.ITaskManageService;
import com.zhangyue.zeus.util.Page;

/**
 * 任务管理服务实现类
 * 
 * @date 2013-9-6
 * @author rongneng
 */
@Service("taskManageService")
public class TaskManageServiceImpl implements ITaskManageService {

    private QueriesEntity queriesEntity;
    @Autowired
    private MyBatisDao myBatisDao;
    private static final Log LOG = LogFactory.getLog(TaskManageServiceImpl.class);

    @Override
    public void setQuery(QueriesEntity queriesEntity) {
        this.queriesEntity = queriesEntity;
    }

    @Override
    public int addQueryTask() {
        try {
            myBatisDao.insert("queriesMapper.addQueryTask", queriesEntity);
        } catch (Exception e) {
            LOG.error("AddQueryTask  error!", e);
            throw new HiveTableServiceException("AddQueryTask  error!", e);
        }
        return queriesEntity.getId();
    }

    @Override
    public void updateQueryTask() {
        try {
            myBatisDao.insert("queriesMapper.updateQueryTask", queriesEntity);
        } catch (Exception e) {
            LOG.error("UpdateQueryTask  error!", e);
            throw new HiveTableServiceException("UpdateQueryTask  error!", e);
        }
    }

    @Override
    public Page<QueriesEntity> findSubmitTaskPage() {
        List<QueriesEntity> retList = null;
        Page<QueriesEntity> page = new Page<QueriesEntity>();
        int pageStart = (queriesEntity.getPageNo() - 1) * queriesEntity.getPageSize();
        int count = 0;
        try {
            // 先查询总数
            count = (Integer) myBatisDao.find("queriesMapper.findSumbitTaskPageCount", queriesEntity);
            queriesEntity.setPageStart(pageStart);
            // 查询分页记录
            retList = myBatisDao.findList("queriesMapper.findSumbitTaskPage", queriesEntity);
            page.setPageNo(queriesEntity.getPageNo());
            page.setPageSize(queriesEntity.getPageSize());
            page.setTotalCount(count);
            page.setResult(retList);
        } catch (Exception e) {
            LOG.error("findSumbitTaskPage  error!", e);
            throw new HiveTableServiceException("findSumbitTaskPage  error!", e);
        }
        return page;
    }

    @Override
    public QueriesEntity findSubmitTaskById() {
        QueriesEntity entity = null;
        try {
            entity = (QueriesEntity) myBatisDao.find("queriesMapper.findSumbitTaskById", queriesEntity);
        } catch (Exception e) {
            LOG.error("findSumbitTaskById  error!", e);
            throw new HiveTableServiceException("findSumbitTaskById  error!", e);
        }
        return entity;
    }

    @Override
    public QueriesEntity findSubmitTaskById(int id) {
        QueriesEntity queryParam = new QueriesEntity();
        queryParam.setId(id);
        QueriesEntity entity = null;
        try {
            entity = (QueriesEntity) myBatisDao.find("queriesMapper.findSumbitTaskById", queryParam);
        } catch (Exception e) {
            LOG.error("findSumbitTaskById  error!", e);
            throw new HiveTableServiceException("findSumbitTaskById  error!", e);
        }
        return entity;
    }

    @Override
    public int findSubmitTaskCount() {
        int count = 0;
        // 先查询总数
        try {
            count = (Integer) myBatisDao.find("queriesMapper.findSumbitTaskCount", queriesEntity);
        } catch (Exception e) {
            LOG.error("findSumbitTaskCount  error!", e);
            throw new HiveTableServiceException("findSumbitTaskCount  error!", e);
        }
        return count;
    }

}
