/*
 * Copyright 2014 zhangyue.com All right reserved. This software is the
 * confidential and proprietary information of Renren.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with zhangyue.com.
 */
package com.zhangyue.zeus.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * SpringApplicationContextUtil 获取ApplicationContext对象
 * 
 * @date 2013-9-6
 * @author rongneng
 */

public class SpringContextUtil implements ApplicationContextAware {

    // 声明一个静态变量保存
    private static ApplicationContext applicationContext;

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    @SuppressWarnings("static-access")
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
