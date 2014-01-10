package com.zhangyue.zeus.service;

import java.io.OutputStream;
import java.util.List;

/**
 * 导出文件接口
 * 
 * @date 2014-9-6
 * @author rongneng
 */
public interface IExportDataService {

    /**
     * 下载数据
     * 
     * @param os
     * @param hdfsResultLocation
     */
    public void downloadData(OutputStream os, String hdfsResultLocation, boolean isDownload);

    /**
     * 读取hdfs上的结果数据
     * 
     * @param hdfsResultLocation
     * @param isDownload
     * @return List<String>
     */
    public List<String> readResultData(String hdfsResultLocation, boolean isDownload);

    /**
     * 查询任务结果在页面上显示
     * 
     * @param hdfsResultLocation
     * @return List<String[]>
     */
    public List<List<String>> queryResultList(String hdfsResultLocation);

}
