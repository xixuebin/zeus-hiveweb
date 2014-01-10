/*
 * Copyright 2014 ireader.com All right reserved. This software is the
 * confidential and proprietary information of ireader.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ireader.com.
 */
package com.zhangyue.zeus.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.zhangyue.zeus.entity.HiveTableEntity;
import com.zhangyue.zeus.entity.UserEntity;
import com.zhangyue.zeus.service.IHiveTableService;
import com.zhangyue.zeus.service.IUserService;
import com.zhangyue.zeus.util.Constants;
import com.zhangyue.zeus.util.Page;

/**
 * hive 表管理controller
 * 
 * @date 2014-1-5
 * @author rongneng
 */
@Controller
@RequestMapping("/hivetable")
public class HiveTableMangeController extends BaseController {

    private static final Log LOG = LogFactory.getLog(HiveTableMangeController.class);
    @Autowired
    private IHiveTableService hiveTableService;
    @Autowired
    private IUserService userService;

    /**
     * 进入添加添加hive table页面
     * 
     * @return
     */
    @RequestMapping(value = "addhivetable", method = RequestMethod.GET)
    public ModelAndView addHive() {
        return new ModelAndView("/schema/addhivetable");
    }

    /**
     * 插入hive表及字段信息
     * 
     * @return
     */
    @RequestMapping(value = "addhivetable", method = RequestMethod.POST)
    public ModelAndView addHiveTable() {
        List<HiveTableEntity> fieldList = getTableFieldParameters();
        if (fieldList.size() > 0) {
            // 批量插入
            int ret = hiveTableService.addHiveTableAndField(fieldList);
            if (ret == -1) {
                request.setAttribute("msg", "表名已存在");
                request.setAttribute("msg-type", "error");
            }
        }
        request.setAttribute("msg", "保存表数据成功");
        request.setAttribute("msg-type", "success");
        return new ModelAndView("/schema/addhivetable");
    }

    /**
     * 跳转到hive表页面
     * 
     * @return
     */
    @RequestMapping(value = "hivelist", method = RequestMethod.GET)
    public ModelAndView hiveList() {
        return new ModelAndView("/schema/table-list");
    }

    /**
     * 查询系统中所有的hive表
     */
    @RequestMapping(value = "hivetablelist", produces = "application/json", method = RequestMethod.POST)
    public ModelAndView tableList() {
        HiveTableEntity hiveTableParam = new HiveTableEntity();
        hiveTableParam.setPageNo(getPage());
        hiveTableParam.setPageSize(getPagesize());
        hiveTableService.setHiveTableEntity(hiveTableParam);
        Page<HiveTableEntity> page = hiveTableService.findHivePageTable();
        JSONObject dateObj = new JSONObject();
        JSONArray data = new JSONArray();
        if (null != page.getResult() && page.getResult().size() > 0) {
            for (HiveTableEntity entity : page.getResult()) {
                JSONObject obj = new JSONObject();
                try {
                    obj.put("id", entity.getId());
                    obj.put("createUserName", entity.getUserName());
                    obj.put("comment", entity.getComment());
                    obj.put("tableName", entity.getTableName());
                    data.put(obj);
                } catch (JSONException e) {
                    LOG.error("Put  object into JSONArray error!!", e);
                }
            }
        }
        try {
            dateObj.put("Rows", data);
            dateObj.put("Total", page.getTotalCount());
        } catch (JSONException e1) {
            LOG.error("Put  object into JSONArray error!!", e1);
        }
        try {
            super.ajaxOutPutJson(dateObj.toString());
        } catch (IOException e) {
            LOG.error("Output  json  occur error!!!", e);
        }
        return new ModelAndView("/schema/addhivetable");
    }

    /**
     * 进入编辑表页面,根据表格ID查询表及字段信息
     * 
     * @return
     */
    @RequestMapping(value = "edittable", method = RequestMethod.GET)
    public ModelAndView editTable(@RequestParam(value = "id", required = false) Integer id) {
        HiveTableEntity hiveTableParam = new HiveTableEntity();
        hiveTableParam.setId(id);
        hiveTableService.setHiveTableEntity(hiveTableParam);
        List<HiveTableEntity> retList = hiveTableService.findAllTableFieldById();
        request.setAttribute("tablename", retList.get(0).getTableName());
        request.setAttribute("tablecomment", retList.get(0).getComment());
        request.setAttribute("tablefieldlist", retList);
        request.setAttribute("tableid", id);
        return new ModelAndView("/schema/edithivetable");
    }

    /**
     * 删除hive表
     * 
     * @return
     */
    @RequestMapping(value = "deletetable", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView deletetable() {
        String tableids = request.getParameter("ids");
        List<String> idsList = Arrays.asList(tableids.split(","));
        List<Integer> idList = new ArrayList<Integer>();
        for (String idstr : idsList) {
            if (null != idstr && !idstr.equals("")) {
                int id = Integer.valueOf(idstr);
                idList.add(id);
            }
        }
        // 批量删除hive table
        hiveTableService.batchDeleteTableById(idList);
        try {
            ajaxOutPutJson(Constants.SUCCESS);
        } catch (IOException e) {
            LOG.error("Delete  hive table  occur error!!!", e);
        }
        return null;
    }

    /**
     * 编辑表
     * 
     * @return
     */
    @RequestMapping(value = "edittable", method = RequestMethod.POST)
    public ModelAndView editTableField() {
        String ids = request.getParameter("id");
        int id = 0;
        if (null != ids && !ids.equals("")) {
            id = Integer.valueOf(ids);
        }
        List<HiveTableEntity> fieldList = getTableFieldParameters();
        HiveTableEntity hiveTableEntity = fieldList.get(0);
        hiveTableEntity.setId(id);
        hiveTableService.setHiveTableEntity(hiveTableEntity);
        hiveTableService.updateHiveTable();
        // 删除表对应的field 重新插入？
        hiveTableService.deleteFieldByTableId();
        if (fieldList.size() > 0) {
            for (HiveTableEntity hiveTableEntity1 : fieldList) {
                hiveTableEntity1.setId(id);
            }
        }
        hiveTableService.addTableField(fieldList);
        request.setAttribute("msg", "修改表数据成功");
        request.setAttribute("msg-type", "success");
        // 获取编辑后的表信息
        hiveTableService.setHiveTableEntity(hiveTableEntity);
        List<HiveTableEntity> retList = hiveTableService.findAllTableFieldById();
        request.setAttribute("tablename", retList.get(0).getTableName());
        request.setAttribute("tablecomment", retList.get(0).getComment());
        request.setAttribute("tablefieldlist", retList);
        request.setAttribute("tableid", id);
        return new ModelAndView("/schema/edithivetable");
    }

    /**
     * 进入权限页面
     * 
     * @return
     */
    @RequestMapping(value = "authority", method = RequestMethod.GET)
    public ModelAndView entryAuthority(@RequestParam(value = "commonUserId", required = false) Integer userId,Model model) {
        UserEntity userEntity = new UserEntity();
        userEntity.setLevel(Constants.COMMON_USER);
        userService.setUserEntity(userEntity);
        List<UserEntity> retList = userService.queryUserByLevel();
        model.addAttribute("userList", retList);
        // 选择下拉框的进行授权
        if (null != userId) {
            model.addAttribute("commonUserId", userId);
        // 第一次进入授权页面
        } else if ((null == userId) && (null != retList && retList.size() > 0)){
            model.addAttribute("commonUserId", retList.get(0).getId());
        }
        return new ModelAndView("/schema/authority");
    }

    /**
     * 查询用户对应的权限表
     * 
     * @param userId
     * @return
     */
    @RequestMapping(value = "queryauthority",  produces = "application/json")
    public ModelAndView authorityTableList(@RequestParam(value = "commonUserId", required = false) Integer userId) {
        HiveTableEntity hiveTableEntity = new HiveTableEntity();
        hiveTableEntity.setUserId(userId);
        hiveTableService.setHiveTableEntity(hiveTableEntity);
        // 查询指定用户对应的表
        List<HiveTableEntity> tableList = hiveTableService.findHiveTableLListByUserId();
        hiveTableEntity.setPageNo(getPage());
        hiveTableEntity.setPageSize(getPagesize());
        hiveTableService.setHiveTableEntity(hiveTableEntity);
        // 分页查询系统中的表
        Page<HiveTableEntity> page = hiveTableService.findHivePageTable();
        JSONObject dateObj = new JSONObject();
        JSONArray data = new JSONArray();
        // 将用户对应的表存放在map中，key-tableId，value-tableId
        Map<Integer, Integer> map = listToMap(tableList);
        try {
            if (null != page.getResult() && page.getResult().size() > 0) {
                for (HiveTableEntity entity : page.getResult()) {
                    String isChecked = Constants.N;
                    // 用户有该table权限，设置isChecked = 'Y'
                    if (map.containsKey(entity.getId())) {
                        isChecked = Constants.Y;
                    }
                    JSONObject obj = new JSONObject();
                    obj.put("id", entity.getId());
                    obj.put("createUserName", entity.getUserName());
                    obj.put("comment", entity.getComment());
                    obj.put("tableName", entity.getTableName());
                    obj.put("isChecked", isChecked);
                    data.put(obj);
                }
            }
            dateObj.put("Rows", data);
            dateObj.put("Total", page.getTotalCount());
        } catch (JSONException e) {
            LOG.error("Put  object into JSONArray error!!", e);
        }
        try {
            super.ajaxOutPutJson(dateObj.toString());
        } catch (IOException e) {
            LOG.error("Assemble JSON  occur error", e);
        }
        return null;
    }

    /**
     * 给用户授权
     * 
     * @return
     */
    @RequestMapping(value = "editauthority", method = RequestMethod.GET)
    public ModelAndView editAuthority() {
        @SuppressWarnings("unchecked")
        Map<String, String[]> map = request.getParameterMap();
        // uid
        String[] uid = map.get("userId");
        int userId = Integer.valueOf(uid[0]);
        // tableId
        String[] tableIds = map.get("id");
        // 删除用户对应的用户表，重新插入
        HiveTableEntity hiveTableEntity = new HiveTableEntity();
        hiveTableEntity.setUserId(userId);
        hiveTableService.setHiveTableEntity(hiveTableEntity);
        hiveTableService.deleteUserTableByUserId();
        List<HiveTableEntity> list = new ArrayList<HiveTableEntity>();
        if (tableIds.length > 0) {
            for (String taid : tableIds) {
                HiveTableEntity hiveTableEntity2 = new HiveTableEntity();
                hiveTableEntity2.setUserId(userId);
                hiveTableEntity2.setId(Integer.valueOf(taid));
                list.add(hiveTableEntity2);
            }
        }
        hiveTableService.batchInsertUserHiveTable(list);
        return new ModelAndView(new RedirectView("authority?commonUserId=" + userId + ""));
    }

    /**
     * 获取表字段参数
     * 
     * @return
     */
    public List<HiveTableEntity> getTableFieldParameters() {
        List<HiveTableEntity> retList = new ArrayList<HiveTableEntity>();
        String tableName = request.getParameter("tableName");
        String comment = request.getParameter("comment");
        int userId = (Integer) request.getSession().getAttribute("userId");
        @SuppressWarnings("unchecked")
        Map<String, String[]> map = request.getParameterMap();
        // field
        String[] fields = map.get("field");
        String[] fieldTypes = map.get("fieldtype");
        String[] fieldComments = map.get("fieldcomment");
        String[] isPartitions = map.get("ispartition");
        if (fields != null && fields.length > 0) {
            for (int i = 0; i < fields.length; i++) {
                HiveTableEntity vo = new HiveTableEntity();
                vo.setTableName(tableName);
                vo.setFieldComment(fieldComments[i]);
                vo.setDbName(Constants.DEFAULT_SCHEMA);
                vo.setField(fields[i]);
                vo.setFieldType(fieldTypes[i]);
                vo.setComment(comment);
                vo.setCreateUserId(userId);
                vo.setIsPartition(isPartitions[i]);
                retList.add(vo);
            }
        }
        return retList;
    }

    /**
     * list to map
     * 
     * @param list
     * @return
     */
    public Map<Integer, Integer> listToMap(List<HiveTableEntity> list) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        if (null != list && list.size() > 0) {
            for (HiveTableEntity userTable : list) {
                map.put(userTable.getId(), userTable.getId());
            }
        }
        return map;
    }
}
