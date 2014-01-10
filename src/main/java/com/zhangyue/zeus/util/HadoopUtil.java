package com.zhangyue.zeus.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.log4j.Logger;

/**
 * hadoop 操作工具类
 * 
 * @date 2013-9-6
 * @author rongneng
 */
public class HadoopUtil {

    protected static final Logger LOG = Logger.getLogger(HadoopUtil.class);

    public static String getJobTrackerURL(String jobid) {
        HiveConf conf = new HiveConf(SessionState.class);
        String jt = conf.get("mapred.job.tracker");
        String jth = conf.get("mapred.job.tracker.http.address");
        String[] jtparts = null;
        String[] jthttpParts = null;
        if (jt.equalsIgnoreCase("local")) {
            jtparts = new String[2];
            jtparts[0] = "local";
            jtparts[1] = "";
        } else {
            jtparts = jt.split(":");
        }
        if (jth.contains(":")) {
            jthttpParts = jth.split(":");
        } else {
            jthttpParts = new String[2];
            jthttpParts[0] = jth;
            jthttpParts[1] = "";
        }
        return "http://" + jtparts[0] + ":" + jthttpParts[1] + "/jobdetails.jsp?jobid=" + jobid + "&refresh=30";
    }

    /*
     * incorrect, datanode can't be random
     */
    public static String getDataNodeURL(String path) {
        Configuration conf = new Configuration();
        conf.addResource("hdfs-default.xml");
        conf.addResource("hdfs-site.xml");

        String nnHttp = conf.get("dfs.http.address");
        String dnHttp = conf.get("dfs.datanode.http.address");

        String host = "";
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(classLoader.getResource("slaves").openStream()));
            host = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
        }

        String nnPort = "";
        if (nnHttp.contains(":")) {
            nnPort = nnHttp.split(":")[1];
        }

        String dnPort = "";
        if (dnHttp.contains(":")) {
            dnPort = dnHttp.split(":")[1];
        }

        return "http://" + host + ":" + dnPort + "/browseDirectory.jsp?namenodeInfoPort=" + nnPort + "&dir=" + path;
    }

}
