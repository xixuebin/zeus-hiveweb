/*
 * Copyright 2013 Renren.com All right reserved. This software is the
 * confidential and proprietary information of Renren.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Renren.com.
 */
package com.zhangyue.zeus.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import com.zhangyue.zeus.service.IExportDataService;
import com.zhangyue.zeus.util.Constants;

/**
 * 任务结果查询，下载服务实现类
 * 
 * @date 2013-9-6
 * @author rongneng
 */
@Service("exportDataService")
public class ExportDataServiceImpl implements IExportDataService {

    private final static Log LOG = LogFactory.getLog(ExportDataServiceImpl.class.getName());

    @Override
    public void downloadData(OutputStream os, String hdfsResultLocation, boolean isDownload) {
        List<String> resultList = readResultData(hdfsResultLocation, isDownload);
        if (resultList.size() == 0) {
            return;
        }
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet(Constants.SHEET_NAME);
        sheet.setColumnWidth(0, 10000);
        for (int i = 0; i < resultList.size(); i++) {
            Row row = sheet.createRow(i);
            if (null == resultList.get(i)) {
                continue;
            }
            String[] items = resultList.get(i).split(Constants.HIVE_RESULT_TAG);
            for (int j = 0; j < items.length; j++) {
                row.createCell(j).setCellValue(items[j]);
            }
        }
        try {
            book.write(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            LOG.error("Export excel error!!", e);
        }
    }

    @Override
    public List<String> readResultData(String hdfsResultLocation, boolean isDownload) {
        List<String> resultList = new ArrayList<String>();
        int readedLines = 0;
        String temp = null;
        try {
            Path hdfsPath = new Path(hdfsResultLocation);
            HiveConf hiveConf = new HiveConf(SessionState.class);
            FileSystem fs = hdfsPath.getFileSystem(hiveConf);
            FileStatus[] fss = fs.listStatus(hdfsPath);
            // 判断可以读取的结果的最大行，下载-60000 页面显示-200
            int numberLimit =
                    (isDownload == true) ? Constants.HIVE_RESULT_DOWNLOAD_LIMIT : Constants.HIVE_RESULT_MAX_LIMIT;
            for (FileStatus fileStatus : fss) {
                Path fsPath = fileStatus.getPath();
                if (readedLines >= numberLimit || fs.getFileStatus(fsPath).isDir()) {
                    break;
                }
                BufferedReader bf = new BufferedReader(new InputStreamReader(fs.open(fsPath), Constants.UTF_ENCODING));
                while ((temp = bf.readLine()) != null) {
                    if (readedLines >= numberLimit) {
                        break;
                    }
                    resultList.add(temp);
                    readedLines++;
                }
                bf.close();
            }
            FileSystem.closeAll();
        } catch (Exception e) {
            LOG.error("don't had your result", e);
        }
        return resultList;
    }

    @Override
    public List<List<String>> queryResultList(String hdfsResultLocation) {
        List<List<String>> retList = new ArrayList<List<String>>();
        List<String> resultList = readResultData(hdfsResultLocation, false);
        if (resultList.size() == 0) {
            return retList;
        }
        int maxCloumn = 0;
        // 前10行
        int firstTen = 10;
        // 计算出前10行字段值的最大值，以最大值为列数，不足的增加达到最大值
        int size = (resultList.size() >= firstTen) ? firstTen : resultList.size();
        for (int i = 0; i < size; i++) {
            // 计算每一行最大的项
            String[] items = resultList.get(i).split(Constants.HIVE_RESULT_TAG);
            if (null != items && items.length != 0) {
                maxCloumn = Math.max(items.length, maxCloumn);
            } else {
                continue;
            }
        }
        for (int i = 0; i < resultList.size(); i++) {
            if (null == resultList.get(i)) {
                continue;
            }
            String[] items = resultList.get(i).split(Constants.HIVE_RESULT_TAG);
            String[] strItem = new String[maxCloumn];
            int len = items.length;
            // 填充数据
            if (len < maxCloumn) {
                for (int j = 0; j < items.length; i++) {
                    strItem[i] = items[i];
                }
                for (int j = len; j < maxCloumn; j++) {
                    strItem[len] = Constants.BLANK;
                }
            } else {
                strItem = items;
            }
            retList.add(Arrays.asList(strItem));
        }
        return retList;
    }

}
