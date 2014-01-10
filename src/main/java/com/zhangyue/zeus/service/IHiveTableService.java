/*
 * Copyright 2013 zhangyue.com All right reserved. This software is the
 * confidential and proprietary information of Renren.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with zhangyue.com.
 */
package com.zhangyue.zeus.service;

import java.util.List;
import com.zhangyue.zeus.entity.HiveTableEntity;
import com.zhangyue.zeus.util.Page;

/**
 * hive table 管理服务接口类
 * 
 * @date 2013-9-6
 * @author rongneng
 */
public interface IHiveTableService {

    /**
     * 设置UserEntity参数对象
     */
    public void setHiveTableEntity(HiveTableEntity hiveTableEntity);

    /**
     * 添加Hive表信息
     * 
     * @return 添加的表的ID，-1 -- 该表名已经存在
     */
    public int addHiveTable();

    /**
     * 添加hive表及字段信息
     * 
     * @param hiveTableEntities 字段列表
     * @return -1 表名已经存在
     */
    public int addHiveTableAndField(List<HiveTableEntity> hiveTableEntities);

    /**
     * 添加HIVE表字段
     * 
     * @return
     */
    public int addTableField(List<HiveTableEntity> entities);

    /**
     * 查询所有hive table
     * 
     * @return
     */
    public List<HiveTableEntity> findAllTable();

    /**
     * 查询hive表对应的字段信息
     * 
     * @return
     */
    public List<HiveTableEntity> findAllTableFieldById();

    /**
     * 根据tableField ID删除字段
     */
    public void deleteTableFieldById();

    /**
     * 根据表的ID删除该表对应的所有的字段信息
     */
    public void deleteFieldByTableId();

    /**
     * 修改hivetable及表的字段信息
     */
    public void updateHiveTable();

    /**
     * 根据用户ID查询对应的hive表
     * 
     * @return List<HiveTableEntity>
     */
    public List<HiveTableEntity> findHiveTableLListByUserId();

    /**
     * 根据ID删除hive表，hive表Id是字段信息表的外键，所以级联删除字段信息
     */
    public void deleteHiveTableById();

    /**
     * 分页查询HIVE_TABLE表
     * 
     * @return
     */
    public Page<HiveTableEntity> findHivePageTable();

    /**
     * 保存用户与hive表的关联
     */
    public void addUserHiveTable();

    /**
     * 解除用户和表的关系
     */
    public void deleteUserTableByUserId();

    /**
     * 用户搜索表格，参数tableName，userId,level
     * 
     * @return
     */
    public List<HiveTableEntity> findHiveTableList();

    /**
     * 批量删除hive table
     * 
     * @param list
     */
    public void batchDeleteTableById(List<Integer> list);

    /**
     * 批量插入用户与表关系
     * 
     * @param list
     */
    public void batchInsertUserHiveTable(List<HiveTableEntity> list);

}
