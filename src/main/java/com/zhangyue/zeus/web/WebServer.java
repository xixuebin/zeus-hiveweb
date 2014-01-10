package com.zhangyue.zeus.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.thread.QueuedThreadPool;
import com.zhangyue.zeus.util.ZeusHiveConfiguration;
/**
 * Jetty  server 管理类
 * @date 2014-1-7
 * @author rongneng
 */
public class WebServer {

    private Server server = null;
    private static Log LOG = LogFactory.getLog(WebServer.class);

    public void initialize(ZeusHiveConfiguration  zeusConf) {
       // String ip = cm.getLocalAddress().getIp();
        server = new Server();
        String applicationHome = System.getProperty("user.dir");
        Connector conn = new SelectChannelConnector();

        conn.setHost(zeusConf.get("server.ip","127.00.0.1"));
        conn.setPort(zeusConf.getInt("zeus.web.server.listen.port", 10119));
        server.setConnectors(new Connector[] { conn });

        WebAppContext context = new WebAppContext();
        context.setContextPath("/");
        context.setWar(applicationHome + "/webapp");
        server.setHandler(context);

        QueuedThreadPool  pool = new QueuedThreadPool();
        pool.setMaxThreads(zeusConf.getInt("webserver.max.thread.count", 10));
        pool.setMinThreads(zeusConf.getInt("webserver.min.thread.count", 5));
        server.setThreadPool(pool);
    }

    public void start() throws Exception {
        server.start();
        LOG.info("Success to start web server!");
        server.join();
    }

    public void stop() throws Exception {
        server.stop();
        LOG.info("Success to stop web server!");
    }
}
