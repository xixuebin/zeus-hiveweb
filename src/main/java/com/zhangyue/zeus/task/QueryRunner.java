package com.zhangyue.zeus.task;

import java.util.ArrayList;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.CommandNeedRetryException;
import org.apache.hadoop.hive.ql.Driver;
import org.apache.hadoop.hive.ql.history.HiveHistoryViewer;
import org.apache.hadoop.hive.ql.processors.CommandProcessor;
import org.apache.hadoop.hive.ql.processors.CommandProcessorFactory;
import org.apache.hadoop.hive.ql.processors.CommandProcessorResponse;
import org.apache.hadoop.hive.ql.session.SessionState;
import com.zhangyue.zeus.entity.QueriesEntity;
import com.zhangyue.zeus.entity.QueriesEntity.RunningStatus;
import com.zhangyue.zeus.service.ITaskManageService;
import com.zhangyue.zeus.task.RunningRunner.Progress;
import com.zhangyue.zeus.task.RunningRunner.Running;
import com.zhangyue.zeus.util.Constants;
import com.zhangyue.zeus.util.QueryUtil;
import com.zhangyue.zeus.util.SpringContextUtil;

/**
 * 拼装查询sql，向hive提交任务，实时获取查询的jobId,实现Runnable，Running接口
 * 
 * @date 2014-9-3
 * @author rongneng
 */
public class QueryRunner implements Runnable, Running {

    protected static final Log LOG = LogFactory.getLog(QueryRunner.class);
    private QueriesEntity queriesEntity;
    private HiveConf hiveConf;
    private ITaskManageService taskManageService;
    private String historyFile;
    private int queriesEntityId;

    public QueryRunner(int queriesEntityId){
        this.queriesEntityId = queriesEntityId;
    }

    @Override
    public void run() {
        if (initialize()) {
            QueryManager.getInstance().monitor(this);
            runQuery();
            finish();
        }
    }

    /**
     * 初始化任务，先获取hive 运行的session，然后保存任务信息到数据库中
     * 
     * @return
     */
    protected boolean initialize() {
        hiveConf = new HiveConf(SessionState.class);
        SessionState.start(hiveConf);
        historyFile = SessionState.get().getHiveHistory().getHistFileName();
        taskManageService = (ITaskManageService) SpringContextUtil.getBean("taskManageService");
        queriesEntity = new QueriesEntity();
        queriesEntity.setId(queriesEntityId);
        taskManageService.setQuery(queriesEntity);
        queriesEntity = taskManageService.findSubmitTaskById();
        // 初始化失败
        if (queriesEntity == null) {
            LOG.error("queriesEntity<" + queriesEntityId + "> is missing");
            return false;
        }
        return true;
    }

    /**
     * 组装sql，提交查询
     */
    public void runQuery() {
        String result = hiveConf.get("hive.hwi.result", "/user/hive/result");
        queriesEntity.setResultLocation(result + "/" + queriesEntity.getId() + "/");
        queriesEntity.setStatus(RunningStatus.RUNNING.getTypeName());
        taskManageService.setQuery(queriesEntity);
        taskManageService.updateQueryTask();
        // 组装查询的sql
        ArrayList<String> cmds = queryToCmds(queriesEntity);
        long start_time = System.currentTimeMillis();
        // 循环处理每一个sql
        for (String cmd : cmds) {
            try {
                CommandProcessorResponse resp = runCmd(cmd);
                queriesEntity.setErrorMsg(resp.getErrorMessage());
                queriesEntity.setErrorCode(resp.getResponseCode());
            } catch (Exception e) {
                queriesEntity.setErrorMsg(e.getMessage());
                queriesEntity.setErrorCode(500);
                break;
            }
        }
        long end_time = System.currentTimeMillis();
        queriesEntity.setTotalTime((int) (end_time - start_time));
        // 错误信息截取400个字符
        if (StringUtils.isNotEmpty(queriesEntity.getErrorMsg()) && queriesEntity.getErrorMsg().length() > 400) {
            queriesEntity.setErrorMsg(queriesEntity.getErrorMsg().substring(0, 399));
        }
        taskManageService.setQuery(queriesEntity);
        taskManageService.updateQueryTask();
    }

    protected ArrayList<String> queryToCmds(QueriesEntity query) {
        ArrayList<String> cmds = new ArrayList<String>();
        String resultLocation = query.getResultLocation();
        // 去掉回车换行
        String safeQuery = QueryUtil.getSafeQuery(query.getQuerySql());
        // 设置任务名字，方便在jobtractor中查看
        cmds.add("set mapred.job.name=hwi_query#" + query.getId() + "_" + query.getUserId() + " ("
                 + query.getTaskName() + ")");
        // 检查用户日期的设置
        for (String cmd : safeQuery.split(";")) {
            cmd = cmd.trim();
            if (cmd.equals("")) {
                continue;
            }
            String prefix = cmd.split("\\s+")[0];
            // 如果是查询，那么加上查询结果保存的路径
            if ("select".equalsIgnoreCase(prefix)) {
                cmd = "INSERT OVERWRITE DIRECTORY '" + resultLocation + "' " + cmd;
            }
            cmds.add(cmd);
        }
        return cmds;
    }

    protected CommandProcessorResponse runCmd(String cmd) throws RuntimeException, CommandNeedRetryException {
        // 空格分隔
        String[] tokens = cmd.split("\\s+");
        CommandProcessor proc = CommandProcessorFactory.get(tokens[0], hiveConf);
        if (proc == null) {
            throw new RuntimeException("CommandProcessor for " + tokens[0] + " was not found");
        }
        CommandProcessorResponse resp;
        // 如果是sql语句
        if (proc instanceof Driver) {
            Driver qp = (Driver) proc;
            qp.setTryCount(Integer.MAX_VALUE);
            try {
                resp = qp.run(cmd);
            } catch (CommandNeedRetryException e) {
                throw e;
            } finally {
                // 清楚本次查询的相关文件目录，比如本地的session文件
                CommandProcessorFactory.clean((HiveConf) hiveConf);
                qp.close();
            }
            // 设置语句
        } else {
            try {
                resp = proc.run(cmd.substring(tokens[0].length()).trim());
            } catch (CommandNeedRetryException e) {
                throw e;
            } finally {
                CommandProcessorFactory.clean((HiveConf) hiveConf);
            }
        }
        return resp;
    }

    /**
     * 查询完成
     */
    private void finish() {
        HiveHistoryViewer hv = QueryUtil.getHiveHistoryViewer(historyFile);
        String jobId = QueryUtil.getJobId(hv);
        if (jobId != null && !jobId.equals("") && !jobId.equals(queriesEntity.getJobId())) {
            queriesEntity.setJobId(jobId);
        }
        Integer cpuTime = QueryUtil.getCpuTime(hv);
        if (cpuTime != null && cpuTime > 0) {
            queriesEntity.setCpuTime(cpuTime);
        }
        if (queriesEntity.getErrorCode() == 0) {
            queriesEntity.setStatus(RunningStatus.FINISHED.getTypeName());
        } else {
            queriesEntity.setStatus(RunningStatus.FAILED.getTypeName());
        }
        // 错误信息截取400个字符
        if (StringUtils.isNotEmpty(queriesEntity.getErrorMsg()) && queriesEntity.getErrorMsg().length() > 400) {
            queriesEntity.setErrorMsg(queriesEntity.getErrorMsg().substring(0, 399));
        }
        taskManageService.updateQueryTask();
    }

    /**
     * 更新任务的状态
     */
    public Progress running() {
        switch (queriesEntity.getRunningStatus()) {
            case INITED:
                return Progress.CONTINUE;
            case RUNNING:
                HiveHistoryViewer hv = QueryUtil.getHiveHistoryViewer(historyFile);
                String jobId = QueryUtil.getJobId(hv);
                if (jobId != null && !jobId.equals(Constants.BLANK) && !jobId.equals(queriesEntity.getJobId())) {
                    queriesEntity.setJobId(jobId);
                    taskManageService.setQuery(queriesEntity);
                    taskManageService.updateQueryTask();
                }
                return Progress.CONTINUE;
            default:
                return Progress.EXIT;
        }
    }
}
