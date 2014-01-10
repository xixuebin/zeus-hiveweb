package com.zhangyue.zeus.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 系统参数配置
 * 
 * @date 2013-9-6
 * @author rongneng
 */
public class ZeusHiveConfiguration {

    private Log LOG = LogFactory.getLog(ZeusHiveConfiguration.class);

    /** 系统参数 */
    private Map<String, String> sysconf = new ConcurrentHashMap<String, String>();

    /** 系统参数分类 key:文件名 value:Properties */
    private Map<String, Properties> propMap = new ConcurrentHashMap<String, Properties>();

    public Map<String, Properties> getPropMap() {
        return Collections.unmodifiableMap(propMap);
    }

    public Properties getPropMapByFileName(String fileName) {
        return propMap.get(fileName);
    }

    public Map<String, String> getSysconf() {
        return Collections.unmodifiableMap(sysconf);
    }

    public String get(String key) {
        return sysconf.get(key);
    }

    public String get(String key, String defaultValue) {
        String value = sysconf.get(key);
        if (null != value && !"".equals(value.trim())) {
            return value;
        }
        return defaultValue;
    }

    public int getInt(String name, int defaultValue) {
        String valueString = get(name);
        if (valueString == null) return defaultValue;
        try {
            return Integer.parseInt(valueString);
        } catch (NumberFormatException e) {
            LOG.error(e.getMessage());
        }
        return defaultValue;
    }

    public void set(String key, String value) {
        sysconf.put(key, value);
    }

    /**
     * 配置初始化
     * 
     * @param propFiles
     * @throws IOException
     */
    public void initialize(String[] propFiles) throws IOException {
        if (propFiles == null) {
            return;
        }
        for (String propFile : propFiles) {
            loadProp(propFile);
        }
    }

    private void loadProp(String propFile) throws IOException {
        Properties prop = new Properties();
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(propFile);
        prop.load(is);

        propMap.put(propFile, prop);

        Set<Entry<Object, Object>> set = prop.entrySet();
        for (Entry<Object, Object> en : set) {
            this.set(en.getKey().toString(), en.getValue().toString());
        }
    }

    private static ReentrantLock lock = new ReentrantLock();

    private static ZeusHiveConfiguration schedConfiguration = null;

    private ZeusHiveConfiguration(){
    }

    public static ZeusHiveConfiguration getInstance() {
        lock.lock();
        if (null == schedConfiguration) {
            schedConfiguration = new ZeusHiveConfiguration();
        }
        lock.unlock();
        return schedConfiguration;
    }

}
