/*
 * Copyright 2013 zhangyue.com All right reserved. This software is the
 * confidential and proprietary information of Renren.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with zhangyue.com.
 */
package com.zhangyue.zeus.entity;

import java.io.Serializable;

/**
 * hive表及字段信息实体类
 * @date 2013-10-6
 * @author rongneng
 */

public class TableFieldEntity  extends Entity  implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int id;                                           // ID
    
    private int tableId;                                      // tableID
    
    private String field;                                     // 字段名字
    
    private String filedType;                                 // 字段类型
    
    private String comment;                                   // 备注
    
    private String isPartition;                               // 是否为分区 Y-是 N-否

    public TableFieldEntity(){
    }

    /**
     * @param id
     * @param tableId
     * @param field
     * @param filedType
     * @param comment
     * @param isPartition
     */
    public TableFieldEntity(int id, int tableId, String field, String filedType, String comment, String isPartition){
        super();
        this.id = id;
        this.tableId = tableId;
        this.field = field;
        this.filedType = filedType;
        this.comment = comment;
        this.isPartition = isPartition;
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
     * @return the field
     */
    public String getField() {
        return field;
    }

    /**
     * @param field the field to set
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * @return the filedType
     */
    public String getFiledType() {
        return filedType;
    }

    /**
     * @param filedType the filedType to set
     */
    public void setFiledType(String filedType) {
        this.filedType = filedType;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the isPartition
     */
    public String getIsPartition() {
        return isPartition;
    }

    /**
     * @param isPartition the isPartition to set
     */
    public void setIsPartition(String isPartition) {
        this.isPartition = isPartition;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
