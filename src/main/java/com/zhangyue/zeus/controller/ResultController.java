/*
 * Copyright 2014 ireader.com All right reserved. This software is the
 * confidential and proprietary information of ireader.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ireader.com.
 */
package com.zhangyue.zeus.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.zhangyue.zeus.entity.QueriesEntity;
import com.zhangyue.zeus.service.IExportDataService;
import com.zhangyue.zeus.service.ITaskManageService;
import com.zhangyue.zeus.util.Constants;

/**
 * 任务结果查询，下载controller
 * @date 2014-1-3
 * @author rongneng
 */
@Controller
@RequestMapping("/result")
public class ResultController extends BaseController {
    
    @Autowired
    private  IExportDataService  exportDataService;
    @Autowired
    private  ITaskManageService  taskManageService;
    private final static Log LOG= LogFactory.getLog(ResultController.class.getName());
    
    /**
     * 查询任务结果或者下载数据
     * @param id
     * @param raw
     * @return
     */
    @RequestMapping(value = "{id}/result")
    public ModelAndView result(@PathVariable("id") Integer id,
        @RequestParam(value = "raw",required = false,defaultValue = "false") boolean raw) {
        QueriesEntity  queriesEntity = taskManageService.findSubmitTaskById(id);
        request.setAttribute("query", queriesEntity);
        // 下载数据
        if (raw) {
            // 设置 文件名=作业名
            this.fileName = queriesEntity.getTaskName() + "." + Constants.EXPORT_FILE_TYPE;
            setResponseHeader();
            try {
                OutputStream os = response.getOutputStream();
                exportDataService.downloadData(os, queriesEntity.getResultLocation(), raw);
            } catch (IOException e) {
                LOG.error("don't had your result", e);
            }
        } else {
            List<List<String>>  itemList = exportDataService.queryResultList(queriesEntity.getResultLocation());
            request.setAttribute("partialResult",itemList);
        }
        return new ModelAndView("/result/result");
    }

}
