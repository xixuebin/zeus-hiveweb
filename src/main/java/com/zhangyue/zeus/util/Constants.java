/*
 * Copyright 2013 ireader.com All right reserved. This software is the
 * confidential and proprietary information of Renren.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ireader.com.
 */
package com.zhangyue.zeus.util;

/**
 * 系统的常量定义类
 * 
 * @date 2013-9-6
 * @author rongneng
 */
public class Constants {

    public static final String DEFAULT_SCHEMA = "default";                                  // hive默认的数据库
     
    public static final String NO_DATA = "no_data";                                         // 没有数据标识

    public static final String HIVE_RESULT_TAG = "\001";                                    // hive 查询结果间隔标识

    public static final String TAB_TAG = "\t";                                              // TAB 键

    public static final int HIVE_RESULT_MAX_LIMIT = 200;                                    // hive查询返回结果条数限制页面最多显示200条

    public static final int HIVE_RESULT_DOWNLOAD_LIMIT = 60000;                             // hive查询返回结果条数限制下载最多能下载60000条

    public static final String UTF_ENCODING = "UTF-8";                                      // UTF-8编码

    public static final String BLANK = "";                                                  // 空字符

    public static final String EXPORT_FILE_TYPE = "xls";                                    // 导出文件类型

    public static final String SHEET_NAME = "返回结果";                                       // 设置sheet名字

    public static final String Y = "Y";                                                     // Yes

    public static final String N = "N";                                                     // No
    
    public static final String SUCCESS = "success";                                         // success

    public static final int COMMON_USER = 2;                                                // 普通管理员
    
    public static final int MANAGE_USER = 1;                                                // 管理员

    public static final String HADOOP = "hadoop";                                           // hadoop用户组

    public static final String RUNNING = "RUNNING";                                         // 正在运行任务状态

    public static final int SUBMIT_COUNT = 5;                                               // 用户最多能同时提交的任务数

    public static final String MAP_JOG_TRACKER = "mapred.job.tracker";                      // JOB TRACKER key

    public static final String GLOBAL_ERROR_INFO = "没有你要访问的结果页面！！！！";              // 定义全局的出错信息

    public static final String DEFAULT_RESULT_LOCATION = "default_path";                     // result_location default path

    public static final long EXECUTORSERVICE_WAITTIME = 3000;                                // ExecutorService waitTime 3s   

    public static final String DEFAULT_STATUS = "INITED";                                    // 默认的任务运行状态 INITED
    
    public static final String SEMICOLON = ";";                                              // semicolon ';'
    
    public static final String MY_TASK_TAG = "my";                                           // 我的任务 
    
    public static final int  DEFAULT_MAX_PAGE = 10;                                          // 默认的每页显示的最大条数 
    
    public static final String  COLON = ":";                                                 // 冒号

}
