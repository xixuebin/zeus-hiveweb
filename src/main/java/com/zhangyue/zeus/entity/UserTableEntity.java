/*
 * Copyright 2013 Renren.com All right reserved. This software is the
 * confidential and proprietary information of Renren.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Renren.com.
 */
package com.zhangyue.zeus.entity;

import java.io.Serializable;

/**
 * 用户对应数据表实体
 * @date 2014-9-6
 * @author rongneng
 */
public class UserTableEntity implements Serializable {

    private static final long serialVersionUID = 1L;
     
    private int id;                       // id
     
    private int userId;                   // 用户id
    
    private int tableId;                  // table ID

    public UserTableEntity(){
    }

    /**
     * @param id
     * @param userId
     * @param tableId
     */
    public UserTableEntity(int id, int userId, int tableId){
        super();
        this.id = id;
        this.userId = userId;
        this.tableId = tableId;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the tableId
     */
    public int getTableId() {
        return tableId;
    }

    /**
     * @param tableId the tableId to set
     */
    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
