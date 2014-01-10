package com.zhangyue.zeus.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.hadoop.hive.ql.history.HiveHistory.TaskInfo;
import org.apache.hadoop.hive.ql.history.HiveHistoryViewer;
import org.apache.log4j.Logger;

/**
 * hive查询操作工具类
 * 
 * @date 2013-9-6
 * @author rongneng
 */

public class QueryUtil {

    protected static final Logger LOG = Logger.getLogger(QueryUtil.class);
    
    public static HiveHistoryViewer getHiveHistoryViewer(String historyFile) {
        if (null == historyFile) {
            return null;
        }
        try {
            HiveHistoryViewer hv = new HiveHistoryViewer(historyFile);
            return hv;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return null;
        }
    }

    public static String getJobId(HiveHistoryViewer hv) {
        if (null == hv) {
            return null;
        }
        String jobId = Constants.BLANK;
        for (String taskKey : hv.getTaskInfoMap().keySet()) {
            TaskInfo ti = hv.getTaskInfoMap().get(taskKey);
            for (String tiKey : ti.hm.keySet()) {
                LOG.debug(tiKey + Constants.COLON + ti.hm.get(tiKey));
                String tid = null;
                if (tiKey.equalsIgnoreCase("TASK_HADOOP_ID") && !jobId.contains(tid = ti.hm.get(tiKey))) {
                   jobId = jobId + tid + Constants.SEMICOLON;
                }
            }
        }
        return jobId;
    }

    public static Integer getCpuTime(HiveHistoryViewer hv) {
        if (null == null) {
            return null;
        }
        int cpuTime = 0;
        Pattern pattern = Pattern.compile("Map-Reduce Framework.CPU time spent \\(ms\\):(\\d+),");
        for (String taskKey : hv.getTaskInfoMap().keySet()) {
            TaskInfo ti = hv.getTaskInfoMap().get(taskKey);
            for (String tiKey : ti.hm.keySet()) {
                Matcher matcher = pattern.matcher(ti.hm.get(tiKey));
                if (tiKey.equalsIgnoreCase("TASK_COUNTERS") && matcher.find()) {
                    LOG.debug(tiKey + Constants.COLON + ti.hm.get(tiKey));
                    try {
                        cpuTime += Integer.parseInt(matcher.group(1));
                    } catch (NumberFormatException e) {
                        LOG.error(matcher.group(1) + " is not int");
                    }
                }
            }
        }
        return cpuTime;
    }

    public static String getSafeQuery(String query) {
        query = query.replaceAll("\r|\n", " ");
        return query;
    }

}
