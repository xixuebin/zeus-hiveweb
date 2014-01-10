/*
 * Copyright 2013 zhangyue.com All right reserved. This software is the
 * confidential and proprietary information of Renren.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with zhangyue.com.
 */
package com.zhangyue.zeus.facility;

import java.util.List;

import com.zhangyue.zeus.entity.HiveTableEntity;
import com.zhangyue.zeus.util.Constants;

/**
 * table assemble 工具类
 * 
 * @date 2013-9-6
 * @author rongneng
 */
public class TableAssembleFacility {

    /**
     * 组装表字段信息
     * 
     * @param table
     * @return
     */
    public static String assembleColumn(List<HiveTableEntity> list) {
        if (null == list || list.size() == 0) {
            return Constants.NO_DATA;
        }
        StringBuffer sb = new StringBuffer();
        String scroll = Constants.BLANK;
        // DIV出现滚动条
        if (list.size() >= 10) {
            scroll = "overflow-y: scroll;";
        }
        sb.append("<div  id=\"hwi_column\"  style=\"float:right;width:38%;height:45%; " + scroll
                  + " OVERFLOW-x: none;\">");
        sb.append("<table id=\"tablecolumn\" class=\"table table-bordered\">");
        sb.append("<thead><tr><td>字段</td></td><td>类型</td><td>备注</td></tr></thead>");
        sb.append("<tbody>");
        // 组装表字段
        for (HiveTableEntity fs : list) {
            String comment = fs.getIsPartition().equals(Constants.Y) ? fs.getFieldComment() + "--分区字段" : fs.getFieldComment();
            sb.append("<tr><td>" + fs.getField() + "</td><td>" + fs.getFieldType() + "</td><td>" + comment
                      + "</td></tr>");
        }
        sb.append("</tbody></table></div>");
        return sb.toString();
    }

    /**
     * 组装页面表格
     * 
     * @param list
     * @return
     */
    public static String assembleTable(List<HiveTableEntity> list) {
        if (null == list || list.size() == 0) {
            return Constants.NO_DATA;
        }
        StringBuffer sb = new StringBuffer();
        String scroll = Constants.BLANK;
        if (list.size() >= 6) {
            scroll = "overflow-y: scroll;";
        }
        sb.append("<div  id=\"hwi_schema\"  style=\"float:right;width:38%;height:45%; " + scroll
                  + " OVERFLOW-x: none;\">");
        sb.append("<table id=\"tablenameresult\" class=\"table table-bordered\">");
        sb.append("<thead><tr><td>表名</td></td><td>备注</td></tr></thead>");
        sb.append("<tbody>");
        for (HiveTableEntity vo : list) {
            sb.append("<tr><td><a   href=#   onclick=\"viewtableinfo(\'" + vo.getId() + "\')\">" + vo.getTableName()
                      + "</a></td><td>" + vo.getComment() + "</td></tr>");
        }
        sb.append("</tbody></table></div>");
        return sb.toString();
    }

}
