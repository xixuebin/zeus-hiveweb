package com.zhangyue.zeus.task;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 从队列取出任务，获取任务状态并更新任务状态线程类
 * 
 * @date 2014-1-5
 * @author rongneng
 */

public class RunningRunner implements Runnable {

    private static final Log LOG = LogFactory.getLog(RunningRunner.class);
    private LinkedBlockingQueue<Running> runnings;
    private Thread thread;

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        LOG.info("RunningRunner started.");
        while (true) {
            try {
                // 如果队列中没有任务，那么阻塞
                Running running = runnings.take();
                Progress progress = running.running();
                switch (progress) {
                    case CONTINUE:
                        // 如果任务为 'CONTINUE',那么放入队列中
                        runnings.put(running);
                        break;
                    case EXIT:
                        break;
                }
                LOG.debug("go to sleep...");
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public boolean add(Running running) {
        return runnings.offer(running);
    }

    public void shutdown() {
        LOG.info("RunningRunner shutting down.");
        try {
            thread.interrupt();
            thread.join(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOG.info("RunningRunner shutdown complete.");
    }

    public static enum Progress {
        CONTINUE, EXIT
    };

    public static interface Running {

        public Progress running();
    }

    public RunningRunner(){
        runnings = new LinkedBlockingQueue<Running>();
    }

}
