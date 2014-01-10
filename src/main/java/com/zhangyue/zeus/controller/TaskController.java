/*
 * Copyright 2013 zhangyue.com All right reserved. This software is the
 * confidential and proprietary information of Renren.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with zhangyue.com.
 */
package com.zhangyue.zeus.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.util.ToolRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.zhangyue.zeus.entity.HiveTableEntity;
import com.zhangyue.zeus.entity.QueriesEntity;
import com.zhangyue.zeus.facility.TableAssembleFacility;
import com.zhangyue.zeus.service.IAuthorityManageService;
import com.zhangyue.zeus.service.IHiveTableService;
import com.zhangyue.zeus.service.ITaskManageService;
import com.zhangyue.zeus.task.QueryManager;
import com.zhangyue.zeus.util.Constants;
import com.zhangyue.zeus.util.HadoopUtil;
import com.zhangyue.zeus.util.Page;
import com.zhangyue.zeus.vo.CheckResult;

/**
 * task 管理controller
 * 
 * @date 2014-1-5
 * @author rongneng
 */
@Controller
@RequestMapping("/task")
public class TaskController extends BaseController {

    private static final Log LOG = LogFactory.getLog(TaskController.class);
    @Autowired
    private ITaskManageService taskManageService;
    @Autowired
    private IHiveTableService hiveTableService;
    @Autowired
    private IAuthorityManageService authorityManageService;

    /**
     * 进入创建任务页面
     * 
     * @param queryId 任务ID
     * @param model 将要在页面现实的数据保存在Model
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public ModelAndView create(@RequestParam(value = "queryId", required = false) Integer queryId, Model model) {
        QueriesEntity queriesEntityParam = new QueriesEntity();
        List<HiveTableEntity> tableList = null;
        int level = (Integer) request.getSession().getAttribute("level");
        int userId = (Integer) request.getSession().getAttribute("userId");
        // 查询具体的某一个任务
        if (null != queryId) {
            queriesEntityParam.setId(queryId);
            taskManageService.setQuery(queriesEntityParam);
            QueriesEntity retQueryEntity = taskManageService.findSubmitTaskById();
            if (null != retQueryEntity) {
                model.addAttribute("name", retQueryEntity.getTaskName());
                model.addAttribute("query", retQueryEntity.getQuerySql());
                model.addAttribute("callback", retQueryEntity.getCallback());
            }
        }
        // 判断是不是管理员，是-列出所有的表，否-列出该用户对应的表
        HiveTableEntity hiveTableParam = new HiveTableEntity();
        hiveTableParam.setUserId(userId);
        hiveTableParam.setLevel(level);
        hiveTableService.setHiveTableEntity(hiveTableParam);
        tableList = hiveTableService.findHiveTableList();
        model.addAttribute("tableList", tableList);
        return new ModelAndView("/query/create");
    }

    /**
     * 提交任务
     * 
     * @param query
     * @param name
     * @return
     */
    @RequestMapping(value = "submit")
    public ModelAndView submitTask(@RequestParam(value = "query", required = false) String query,
        @RequestParam(value = "name", required = false) String name) {
        int userId = getUserId();
        int level = getLevle();
        // 如果任务为空 设置任务名
        if (null == name || name.equals(Constants.BLANK)) {
            Date created = Calendar.getInstance(TimeZone.getDefault()).getTime();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            name = sf.format(created);
        }
        request.setAttribute("name", name);
        request.setAttribute("query", query);
        // 判断是不是管理员，是-列出所有的表，否-列出该用户对应的表
        HiveTableEntity hiveTableParam = new HiveTableEntity();
        hiveTableParam.setUserId(userId);
        hiveTableParam.setLevel(level);
        hiveTableService.setHiveTableEntity(hiveTableParam);
        List<HiveTableEntity> tableList = hiveTableService.findHiveTableList();
        request.setAttribute("tableList", tableList);
        String uri = "/query/create";
        // 校验用户只能同时提交5个任务
        QueriesEntity queriesEntity = new QueriesEntity();
        queriesEntity.setUserId(userId);
        queriesEntity.setStatus(QueriesEntity.RunningStatus.RUNNING.getTypeName());
        taskManageService.setQuery(queriesEntity);
        long count = taskManageService.findSubmitTaskCount();
        if (count >= Constants.SUBMIT_COUNT) {
            request.setAttribute("msg", "您现在有5个任务正在运行，请稍后再提交任务！");
            return new ModelAndView(uri);
        }
        // 校验权限,管理员不做验证
        if (level == 2) {
            CheckResult result = authorityManageService.checkPermission(userId, query);
            if (result.isResult() == true) {
                request.setAttribute("msg", result.getMsg());
                return new ModelAndView(uri);
            }
        }
        LOG.debug("MQuery userId " + userId);
        QueriesEntity queriesEntity2 = new QueriesEntity(name, query, userId);
        taskManageService.setQuery(queriesEntity2);
        taskManageService.addQueryTask();
        QueryManager.getInstance().submit(queriesEntity2);
        LOG.info("task name :" + queriesEntity2.getTaskName() + " query sql name: " + queriesEntity2.getQuerySql());
        if (queriesEntity2 == null || queriesEntity2.getId() == 0) {
            request.setAttribute("msg", "save query failed");
            return new ModelAndView(uri);
        }
        return new ModelAndView(new RedirectView(String.valueOf(queriesEntity2.getId())));
    }

    /**
     * 显示任务详细信息
     * 
     * @param id 任务id
     * @return
     */
    @RequestMapping(value = "{id}")
    public ModelAndView displayTaskInfo(@PathVariable("id") Integer id) {
        QueriesEntity queriesEntity = taskManageService.findSubmitTaskById(id);
        request.setAttribute("query", queriesEntity);
        if (null != queriesEntity.getJobId()) {
            List<Map<String, Object>> jobInfos = new ArrayList<Map<String, Object>>();
            for (String jobId : queriesEntity.getJobId().split(Constants.SEMICOLON)) {
                if (jobId.equals(Constants.BLANK)) {
                    continue;
                }
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("id", jobId);
                map.put("url", HadoopUtil.getJobTrackerURL(jobId));
                jobInfos.add(map);
            }
            request.setAttribute("jobInfos", jobInfos);
        }
        request.setAttribute("createdTime", queriesEntity.getCreated());
        request.setAttribute("updatedTime", queriesEntity.getUpdated());
        if (queriesEntity.getCpuTime() != 0) {
            request.setAttribute("cpuTime", queriesEntity.getCpuTime());
        }
        if (queriesEntity.getTotalTime() != 0) {
            request.setAttribute("totalTime", queriesEntity.getTotalTime());
        }
        if (queriesEntity.getCpuTime() != 0 && queriesEntity.getTotalTime() != 0
            && queriesEntity.getCpuTime() > queriesEntity.getTotalTime()) {
            request.setAttribute("savedTime", Math.abs(queriesEntity.getCpuTime() - queriesEntity.getTotalTime()));
        }
        return new ModelAndView("/query/info");
    }

    /**
     * 终止任务
     * 
     * @param id 任务ID
     * @param joId map-reduce任务ID
     * @return
     */
    @RequestMapping(value = "/stop/{id}/{jobId}")
    public ModelAndView killJob(@PathVariable("id") Integer id, @PathVariable("jobId") String joId) {
        int res = 0;
        String[] args = { "-kill", joId };
        String msg = Constants.BLANK;
        String msgType = Constants.BLANK;
        // 杀掉任务
        QueriesEntity queriesEntity = taskManageService.findSubmitTaskById(id);
        try {
            JobClient jobClient = new JobClient();
            res = ToolRunner.run(jobClient, args);
            jobClient.close();
        } catch (Exception e) {
            LOG.error("Hdoop  job -kill  jobId  exception",e);
        }
        if (res == 0) {
            // 杀掉成功修改状态
            queriesEntity.setStatus(QueriesEntity.RunningStatus.KILLED.getTypeName());
            taskManageService.setQuery(queriesEntity);
            taskManageService.updateQueryTask();
            msg = "你的任务已经成功终止！！";
            msgType = "success";
        } else {
            msg = "你的任务终止失败！！,请重新执行终止按钮！";
            msgType = "error";
        }
        request.setAttribute("msg", msg);
        request.setAttribute("msgType", msgType);
        return new ModelAndView(new RedirectView(String.valueOf(id)));
    }

    /**
     * 任务列表
     * 
     * @param queryName 任务名字
     * @param extra 区分我的任务 所有任务
     * @param page 当前页
     * @param pageSize 每页显示的最大条数
     * @return
     */
    @RequestMapping("list")
    public ModelAndView list(@RequestParam(value = "queryName", required = false) String queryName,
        @RequestParam(value = "extra",required = false) String extra, @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        int userId = getUserId();
        QueriesEntity queryParam = new QueriesEntity();
        // 搜索我的任务
        if (Constants.MY_TASK_TAG.equals(extra)) {
            if (queryName != null) {
                queryParam.setTaskName(queryName);
            }
            queryParam.setUserId(userId);
            request.setAttribute("extra", extra);
        }
        queryParam.setPageNo(page);
        queryParam.setPageSize(pageSize);
        taskManageService.setQuery(queryParam);
        Page<QueriesEntity> pagination = taskManageService.findSubmitTaskPage();
        request.setAttribute("queryName", queryName);
        request.setAttribute("pagination", pagination);
        return new ModelAndView("/query/list");
    }

    /**
     * 查询表的详细信息
     * 
     * @param tableName
     * @return
     */
    @RequestMapping(value = "viewtableinfo", method = RequestMethod.POST)
    public ModelAndView viewClomun(@RequestParam(value = "tableId", required = false) Integer tableId) {
        HiveTableEntity hiveTableParam = new HiveTableEntity();
        hiveTableParam.setId(tableId);
        hiveTableService.setHiveTableEntity(hiveTableParam);
        List<HiveTableEntity> tableList = hiveTableService.findAllTableFieldById();
        try {
            ajaxOutPut(null, TableAssembleFacility.assembleColumn(tableList));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 搜索表名
     * 
     * @param tableName
     * @return
     */
    @RequestMapping(value = "querytablename", method = RequestMethod.POST)
    public ModelAndView searchTable(@RequestParam(value = "tableName", required = false) String tableName) {
        // 判断是不是管理员，是-列出所有的表，否-列出该用户对应的表
        int level = (Integer) request.getSession().getAttribute("level");
        int userId = (Integer) request.getSession().getAttribute("userId");
        List<HiveTableEntity> tableList = null;
        HiveTableEntity hiveTableParam = new HiveTableEntity();
        hiveTableParam.setUserId(userId);
        hiveTableParam.setTableName(tableName);
        hiveTableParam.setLevel(level);
        hiveTableService.setHiveTableEntity(hiveTableParam);
        tableList = hiveTableService.findHiveTableList();
        try {
            ajaxOutPut(null, TableAssembleFacility.assembleTable(tableList));
        } catch (IOException e) {
            LOG.error("AjaxOutPut  table info  JSON  exception", e);
        }
        return null;
    }

}
