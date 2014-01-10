/*
 * Copyright 2013 zhangyue.com All right reserved. This software is the
 * confidential and proprietary information of Renren.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with zhangyue.com.
 */
package com.zhangyue.zeus.entity;

import java.io.Serializable;

import com.zhangyue.zeus.util.Constants;
import com.zhangyue.zeus.util.DateUtils;

/**
 * 查询结果表实体类
 * @date 2013-9-6
 * @author rongneng
 */
public class QueriesEntity extends Entity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int id;                                       // ID
    
    private String callback;                              // callback
    
    private long cpuTime;                                 // cpu 时间
    
    private String created;                               // 创建时间
    
    private int errorCode;                                // 错误代码
    
    private String errorMsg;                              // 错误代码信息
    
    private String jobId;                                 // 任务ID
    
    private String taskName;                              // 任务名字
    
    private String querySql;                              // 提交任务sql
    
    private String resultLocation;                        // 结果文件存放路径
    
    private String status =Constants.DEFAULT_STATUS;      // 状态
    
    private long totalTime;                               // 任务跑的总时间
    
    private String updated;                               // 任务修改时间
    
    private int userId;                                   // 提交任务用户ID
    
    private String userName;                              // 提交人的任务名
    
    @SuppressWarnings("unused") 
    private RunningStatus runningStatus;                  // 运行时的状态
    /**
     * @param taskName
     * @param querySql
     * @param userId
     */
    public QueriesEntity(String taskName, String querySql, int userId) {
        super();
        this.taskName = taskName;
        this.querySql = querySql;
        this.userId = userId;
        this.created = DateUtils.getDate();
        this.resultLocation = Constants.DEFAULT_RESULT_LOCATION;
    }

    /**
     * @param callback
     * @param created
     * @param errorCode
     * @param errorMsg
     * @param groupId
     * @param jobId
     * @param taskName
     * @param querySql
     * @param resultLocation
     * @param status
     * @param totalTime
     * @param updated
     * @param userId
     * @param runningStatus
     */
    public QueriesEntity(String callback, String created, int errorCode,
                         String errorMsg,  String jobId,
                         String taskName, String querySql,
                         String resultLocation, String status, long totalTime,
                         String updated, int userId, RunningStatus runningStatus){
        super();
        this.callback = callback;
        this.created = created;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.jobId = jobId;
        this.taskName = taskName;
        this.querySql = querySql;
        this.resultLocation = resultLocation;
        this.status = status;
        this.totalTime = totalTime;
        this.updated = updated;
        this.userId = userId;
        this.runningStatus = runningStatus;
    }

    public QueriesEntity(){
    }

    /**
     * @param id
     * @param callback
     * @param cpuTime
     * @param created
     * @param errorCode
     * @param errorMsg
     * @param groupId
     * @param jobId
     * @param taskName
     * @param querySql
     * @param resultLocation
     * @param status
     * @param totalTime
     * @param updated
     * @param userId
     */
    public QueriesEntity(int id, String callback, long cpuTime, String created,
                         int errorCode, String errorMsg, 
                         String jobId, String taskName, String querySql,
                         String resultLocation, String status, long totalTime,
                         String updated, int userId){
        super();
        this.id = id;
        this.callback = callback;
        this.cpuTime = cpuTime;
        this.created = created;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.jobId = jobId;
        this.taskName = taskName;
        this.querySql = querySql;
        this.resultLocation = resultLocation;
        this.status = status;
        this.totalTime = totalTime;
        this.updated = updated;
        this.userId = userId;
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
     * @return the callback
     */
    public String getCallback() {
        return callback;
    }

    /**
     * @param callback the callback to set
     */
    public void setCallback(String callback) {
        this.callback = callback;
    }

    /**
     * @return the cpuTime
     */
    public long getCpuTime() {
        return cpuTime;
    }

    /**
     * @param cpuTime the cpuTime to set
     */
    public void setCpuTime(long cpuTime) {
        this.cpuTime = cpuTime;
    }

    /**
     * @return the created
     */
    public String getCreated() {
        if (created.indexOf(".") != -1) {
            String  createdTime = created.substring(1, 19);
            return createdTime;
        }
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * @return the errorCode
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the errorMsg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * @param errorMsg the errorMsg to set
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * @return the jobId
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * @param jobId the jobId to set
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    /**
     * @return the taskName
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * @param taskName the taskName to set
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * @return the querySql
     */
    public String getQuerySql() {
        return querySql;
    }

    /**
     * @param querySql the querySql to set
     */
    public void setQuerySql(String querySql) {
        this.querySql = querySql;
    }

    /**
     * @return the resultLocation
     */
    public String getResultLocation() {
        return resultLocation;
    }

    /**
     * @param resultLocation the resultLocation to set
     */
    public void setResultLocation(String resultLocation) {
        this.resultLocation = resultLocation;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the totalTime
     */
    public long getTotalTime() {
        return totalTime;
    }

    /**
     * @param totalTime the totalTime to set
     */
    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    /**
     * @return the updated
     */
    public String getUpdated() {
        return updated;
    }

    /**
     * @param updated the updated to set
     */
    public void setUpdated(String updated) {
        this.updated = updated;
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
     * 
     * 状态枚举类
     * @date 2014-9-6
     * @author rongneng
     */
    public static enum RunningStatus {
        INITED("INITED"), RUNNING("RUNNING"), FINISHED("FINISHED"), CANCELLED("CANCELLED"), 
        FAILED("FAILED"), SYNTAXERROR("SYNTAXERROR"), KILLED("KILLED");
        private RunningStatus(String typeName){
            this.typeName = typeName;
        }
        /** 类型名称 */
        private final String typeName;
        public String getTypeName() {
            return typeName;
        }
    }

    
    /**
     * @return the runningStatus
     */
    public RunningStatus getRunningStatus() {
        return RunningStatus.valueOf(status);
    }

    /**
     * @param runningStatus the runningStatus to set
     */
    public void setRunningStatus(RunningStatus runningStatus) {
        this.runningStatus = runningStatus;
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
    
}
