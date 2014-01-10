package com.zhangyue.zeus.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.zhangyue.zeus.util.Constants;
import com.zhangyue.zeus.entity.QueriesEntity;
import com.zhangyue.zeus.task.RunningRunner.Running;
import com.zhangyue.zeus.util.ZeusHiveConfiguration;

/**
 * 负责处理提交任务，使用线程池完成任务的提交管理，并将任务运行状态加入队列中，启动了一个线程 来更新任务的状态
 * 
 * @date 2013-9-3
 * @author rongneng
 */
public class QueryManager {

    protected static final Log LOG = LogFactory.getLog(QueryManager.class);
    private static QueryManager queryManager;
    private ExecutorService executor;
    private RunningRunner runner;

    private QueryManager(){
    }

    /**
     * double check QueryManager单例
     * 
     * @return
     */
    public static QueryManager getInstance() {
        if (null == queryManager) {
            synchronized (QueryManager.class) {
                if (null == queryManager) {
                    queryManager = new QueryManager();
                }
            }
        }
        return queryManager;
    }

    /**
     * 初始化
     */
    public void initialize(ZeusHiveConfiguration zeusConfig) {
        // 配置ExecutorService
        // 从配置文件中读取线程池大小，默认为30
        executor = Executors.newFixedThreadPool(zeusConfig.getInt("org.executorService.threadCount", 30));
        // 启动RunningRunner 线程
        startRunner();
        LOG.info("QueryManager started!!");
    }

    /**
     * 启动更新任务状态的线程
     */
    protected void startRunner() {
        runner = new RunningRunner();
        runner.start();
    }

    /**
     * 提交任务，将任务提交到线程池中
     * 
     * @param queriesEntity
     */
    public void submit(QueriesEntity queriesEntity) {
        QueryRunner queryRunner = new QueryRunner(queriesEntity.getId());
        executor.submit(queryRunner);
    }

    public boolean monitor(Running running) {
        if (runner == null) return false;
        runner.add(running);
        return true;
    }

    /**
     * 关闭线程池
     */
    public void shutdown() {
        LOG.info("QueryManager shutting down.");
        if (executor != null) {
            executor.shutdown();
            try {
                executor.awaitTermination(Constants.EXECUTORSERVICE_WAITTIME, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                LOG.error("ExecutorService awaitTermination  error!!", e);
            }
        }
        if (runner != null) {
            runner.shutdown();
        }
        LOG.info("QueryManager shutdown complete!!");
    }
}
