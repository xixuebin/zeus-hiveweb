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
import com.zhangyue.zeus.entity.HiveTableEntity;
import com.zhangyue.zeus.exception.HiveTableServiceException;
import com.zhangyue.zeus.service.IHiveTableService;
import com.zhangyue.zeus.util.Page;

/**
 * hive表服务实现类
 * 
 * @date 2013-9-6
 * @author rongneng
 */
@Service("hivetableService")
public class HiveTableServiceImpl implements IHiveTableService {

    @Autowired
    private MyBatisDao myBatisDao;
    private HiveTableEntity hiveTableEntity;
    private static final Log LOG = LogFactory.getLog(UserServiceImpl.class);

    @Override
    public void setHiveTableEntity(HiveTableEntity hiveTableEntity) {
        this.hiveTableEntity = hiveTableEntity;
    }

    @Override
    public int addHiveTable() {
        int ret = 0;
        try {
            // 先判断该表名是否存在
            HiveTableEntity entity =
                    (HiveTableEntity) myBatisDao.find("hiveTableMapper.queryHiveTableByTableName", hiveTableEntity);
            if (null != entity) {
                ret = -1;
                LOG.info("tableName " + hiveTableEntity.getTableName() + " is exist");
                return ret;
            }
            myBatisDao.insert("hiveTableMapper.insertHiveTable", hiveTableEntity);
            ret = hiveTableEntity.getId();
        } catch (Exception e) {
            LOG.error("Add HiveTable error!", e);
            throw new HiveTableServiceException("Add HiveTable error!", e);
        }
        return ret;
    }

    @Override
    public int addHiveTableAndField(List<HiveTableEntity> hiveTableEntities) {
        if (null == hiveTableEntities) {
            LOG.info("hiveTableEntities's size 0");
            return -1;
        }
        HiveTableEntity entity = hiveTableEntities.get(0);
        setHiveTableEntity(entity);
        int tableId = addHiveTable();
        if (tableId == -1) {
            LOG.info("this table name is exists");
            return -1;
        }
        for (int i = 0; i < hiveTableEntities.size(); i++) {
            hiveTableEntities.get(i).setId(tableId);
        }
        addTableField(hiveTableEntities);
        return 0;
    }

    @Override
    public int addTableField(List<HiveTableEntity> entities) {
        int ret = 0;
        try {
            ret = myBatisDao.batchInsert("hiveTableMapper.insertHiveTableField", entities);
        } catch (Exception e) {
            LOG.error("Batch insert TableField list error", e);
            throw new HiveTableServiceException("Batch insert TableField list error", e);
        }
        return ret;
    }

    @Override
    public List<HiveTableEntity> findAllTable() {
        List<HiveTableEntity> retList = null;
        try {
            retList = myBatisDao.findList("hiveTableMapper.queryAllHiveTable");
        } catch (Exception e) {
            LOG.error("query   all hiveTable error", e);
            throw new HiveTableServiceException("query   all hiveTable error", e);
        }
        return retList;
    }

    @Override
    public List<HiveTableEntity> findAllTableFieldById() {
        List<HiveTableEntity> retList = null;
        try {
            retList = myBatisDao.findList("hiveTableMapper.queryHiveTableById", hiveTableEntity);
        } catch (Exception e) {
            LOG.error("findAllTableFieldById  error!", e);
            throw new HiveTableServiceException("findAllTableFieldById  error!", e);
        }
        return retList;
    }

    @Override
    public void deleteTableFieldById() {
        try {
            myBatisDao.delete("hiveTableMapper.deleteTableFieldById", hiveTableEntity);
        } catch (Exception e) {
            LOG.error("deleteTableFieldById  error!", e);
            throw new HiveTableServiceException("deleteTableFieldById  error!", e);
        }
    }

    @Override
    public void deleteFieldByTableId() {
        try {
            myBatisDao.delete("hiveTableMapper.deleteFieldByTableId", hiveTableEntity);
        } catch (Exception e) {
            LOG.error("deleteFieldByTableId  error!", e);
            throw new HiveTableServiceException("deleteFieldByTableId  error!", e);
        }
    }

    @Override
    public void updateHiveTable() {
        try {
            myBatisDao.update("hiveTableMapper.updateHiveTable", hiveTableEntity);
        } catch (Exception e) {
            LOG.error("updateHiveTable  error!", e);
            throw new HiveTableServiceException("updateHiveTable  error!", e);
        }
    }

    @Override
    public List<HiveTableEntity> findHiveTableLListByUserId() {
        List<HiveTableEntity> retList = null;
        try {
            retList = myBatisDao.findList("hiveTableMapper.findHiveTableLListByUserId", hiveTableEntity);
        } catch (Exception e) {
            LOG.error("findHiveTableLListByUserId  error!", e);
            throw new HiveTableServiceException("findHiveTableLListByUserId  error!", e);
        }
        return retList;
    }

    @Override
    public void deleteHiveTableById() {
        try {
            myBatisDao.delete("hiveTableMapper.deleteHiveTableById", hiveTableEntity);
        } catch (Exception e) {
            LOG.error("deleteHiveTableById  error!", e);
            throw new HiveTableServiceException("deleteHiveTableById  error!", e);
        }
    }

    @Override
    public Page<HiveTableEntity> findHivePageTable() {
        int pageStart = (hiveTableEntity.getPageNo() - 1) * hiveTableEntity.getPageSize();
        int totalCount = 0;
        Page<HiveTableEntity> page = new Page<HiveTableEntity>();
        // 查询总数
        try {
            totalCount = (Integer) myBatisDao.find("hiveTableMapper.findHiveTableCount", hiveTableEntity);
            // 设置pageStart
            hiveTableEntity.setPageStart(pageStart);
            // 查询当前页的数据
            List<HiveTableEntity> retList = myBatisDao.findList("hiveTableMapper.findHiveTablePage", hiveTableEntity);
            page.setPageNo(hiveTableEntity.getPageNo());
            page.setPageSize(hiveTableEntity.getPageSize());
            page.setTotalCount(totalCount);
            page.setResult(retList);
        } catch (Exception e) {
            LOG.error("findHivePageTable  error!", e);
            throw new HiveTableServiceException("findHivePageTable  error!", e);
        }
        return page;
    }

    @Override
    public void addUserHiveTable() {
        try {
            myBatisDao.insert("hiveTableMapper.addUserHiveTable", hiveTableEntity);
        } catch (Exception e) {
            LOG.error("addUserHiveTable  error!", e);
            throw new HiveTableServiceException("addUserHiveTable  error!", e);
        }
    }

    @Override
    public void deleteUserTableByUserId() {
        try {
            myBatisDao.insert("hiveTableMapper.deleteUserTableByUserId", hiveTableEntity);
        } catch (Exception e) {
            LOG.error("DeleteUserTableByUserId  error!", e);
            throw new HiveTableServiceException("DeleteUserTableByUserId  error!", e);
        }
    }

    @Override
    public List<HiveTableEntity> findHiveTableList() {
        List<HiveTableEntity> tableList = null;
        try {
            tableList = myBatisDao.findList("hiveTableMapper.findHiveTableList", hiveTableEntity);
        } catch (Exception e) {
            LOG.error("SearchHiveTableList  error!", e);
            throw new HiveTableServiceException("SearchHiveTableList  error!", e);
        }
        return tableList;
    }

    @Override
    public void batchDeleteTableById(List<Integer> list) {
        try {
            myBatisDao.insert("hiveTableMapper.batchDeleteTableById", list);
        } catch (Exception e) {
            LOG.error("batchDeleteTableById  error!", e);
            throw new HiveTableServiceException("batchDeleteTableById  error!", e);
        }
    }

    @Override
    public void batchInsertUserHiveTable(List<HiveTableEntity> list) {
        try {
            myBatisDao.insert("hiveTableMapper.batchInsertUserHiveTable", list);
        } catch (Exception e) {
            LOG.error("batchInsertUserHiveTable  error!", e);
            throw new HiveTableServiceException("batchInsertUserHiveTable  error!", e);
        }
    }

}
