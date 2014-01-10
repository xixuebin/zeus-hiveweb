/*
 * Copyright 2014 ireader.com All right reserved. This software is the
 * confidential and proprietary information of ireader.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ireader.com.
 */
package com.zhangyue.zeus;

import java.io.IOException;
import org.apache.log4j.Logger;
import com.zhangyue.zeus.task.QueryManager;
import com.zhangyue.zeus.util.ZeusHiveConfiguration;
import com.zhangyue.zeus.web.WebServer;

/**
 * zeus 系统启动类
 * 
 * @date 2014-1-7
 * @author rongneng
 */
public class ZeusHiveBootStrap {

    private static final Logger LOG = Logger.getLogger(ZeusHiveBootStrap.class);
    private static WebServer webServer;

    public void initialize() {
        webServer = new WebServer();
        // 读取配置文件
        ZeusHiveConfiguration zeusConfiguration = ZeusHiveConfiguration.getInstance();
        try {
            zeusConfiguration.initialize(new String[] { "zeus.properties" });
        } catch (IOException e) {
            LOG.error("Inittialize  ZeusHiveConfiguration  occur error,May  zeus.properties is not exist", e);
            System.exit(-1);
        }
        QueryManager queryManager = QueryManager.getInstance();
        // 初始化QueryManager
        queryManager.initialize(zeusConfiguration);
        // 初始化webserver
        webServer.initialize(zeusConfiguration);

    }

    public void start() throws IOException {
        // 初始化系统配置
        initialize();
        // 启动webServer
        try {
            webServer.start();
            LOG.info("Zeus Server started !!");
        } catch (Exception e) {
            LOG.error("Hive Web Server occur exception,please  reveiew port etc....", e);
        }
    }

    public static void main(String[] args) throws Exception {
        ZeusHiveBootStrap zeusBootStrap = new ZeusHiveBootStrap();
        zeusBootStrap.start();
    }

    public void stop() throws Exception {
        LOG.info("HWI is shutting down");
        webServer.stop();
    }

}
