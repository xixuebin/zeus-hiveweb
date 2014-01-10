/*
 * Copyright 2013 zhangyue.com All right reserved. This software is the
 * confidential and proprietary information of Renren.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with zhangyue.com.
 */
package com.zhangyue.zeus.exception;

/**
 * MyBatisDao异常类
 * @date 2014-9-6
 * @author rongneng
 */
public class MyBatisDaoException extends Exception {
    private static final long serialVersionUID = 1L;
    public MyBatisDaoException() {
        super();
    }

    public MyBatisDaoException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public MyBatisDaoException(String arg0) {
        super(arg0);
    }

    public MyBatisDaoException(Throwable arg0) {
        super(arg0);
    }
}
