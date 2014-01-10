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
 * hive信息表实体类
 * @date 2013-9-6
 * @author rongneng
 */
public class HiveTableEntity extends Entity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int id;                                                // hive表ID
    
    private int createUserId;                                      // 创建用户ID
    
    private String tableName;                                      // table 名字
    
    private String comment;                                        // 表字段comment   
    
    private String dbName;                                         // hive库名字 默认为default
    
    private String FieldComment;                                   // 表的字段的备注
    
    private String field;                                          // 字段名称
    
    private String fieldType;                                      // 字段类型
    
    private String isPartition = "N";                              // 是否分区
    
    private String userName;                                       // 用户名字
    
    private int fieldId;                                           // 字段ID
   
    private int userId;                                             // 用户ID
    
    private int level;                                              // 用户等级

    public HiveTableEntity(){
    }

    /**
     * @param createUserId
     * @param tableName
     * @param comment
     * @param dbName
     */
    public HiveTableEntity(int createUserId, String tableName, String comment, String dbName){
        super();
        this.createUserId = createUserId;
        this.tableName = tableName;
        this.comment = comment;
        this.dbName = dbName;
    }

    /**
     * @param id
     * @param fieldComment
     * @param field
     * @param fieldType
     * @param isPartition
     */
    public HiveTableEntity(int id, String fieldComment, String field, String fieldType, String isPartition){
        super();
        this.id = id;
        FieldComment = fieldComment;
        this.field = field;
        this.fieldType = fieldType;
        this.isPartition = isPartition;
    }

    /**
     * @param id
     * @param createUserId
     * @param tableName
     * @param comment
     * @param dbName
     */
    public HiveTableEntity(int id, int createUserId, String tableName, String comment, String dbName){
        super();
        this.id = id;
        this.createUserId = createUserId;
        this.tableName = tableName;
        this.comment = comment;
        this.dbName = dbName;
    }

    /**
     * @param id
     * @param createUserId
     * @param tableName
     * @param comment
     * @param dbName
     * @param fieldComment
     * @param field
     * @param fieldType
     * @param isPartition
     */
    public HiveTableEntity(int id, int createUserId, String tableName, String comment, String dbName,
                           String fieldComment, String field, String fieldType, String isPartition){
        super();
        this.id = id;
        this.createUserId = createUserId;
        this.tableName = tableName;
        this.comment = comment;
        this.dbName = dbName;
        FieldComment = fieldComment;
        this.field = field;
        this.fieldType = fieldType;
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
     * @return the createUserId
     */
    public int getCreateUserId() {
        return createUserId;
    }

    /**
     * @param createUserId the createUserId to set
     */
    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
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
     * @return the dbName
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * @param dbName the dbName to set
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * @return the fieldComment
     */
    public String getFieldComment() {
        return FieldComment;
    }

    /**
     * @param fieldComment the fieldComment to set
     */
    public void setFieldComment(String fieldComment) {
        FieldComment = fieldComment;
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
     * @return the fieldType
     */
    public String getFieldType() {
        return fieldType;
    }

    /**
     * @param fieldType the fieldType to set
     */
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
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
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the fieldId
     */
    public int getFieldId() {
        return fieldId;
    }

    /**
     * @param fieldId the fieldId to set
     */
    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
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
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }

}
