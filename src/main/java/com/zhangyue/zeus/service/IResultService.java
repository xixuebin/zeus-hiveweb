/*
 * Copyright 2014 ireader.com All right reserved. This software is the
 * confidential and proprietary information of ireader.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ireader.com.
 */
package com.zhangyue.zeus.service;

import java.io.OutputStream;

/**
 * 任务结果查询，下载服务接口类
 * 
 * @date 2013-9-6
 * @author rongneng
 */
public interface IResultService {

    /**
     * 下载
     * 
     * @param os
     * @param hdfsResultLocation
     */
    public void downloadData(OutputStream os, String hdfsResultLocation);

}
