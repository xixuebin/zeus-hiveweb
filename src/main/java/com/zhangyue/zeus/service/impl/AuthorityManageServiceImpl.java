/*
 * Copyright 2014 zhangyue.com All right reserved. This software is the
 * confidential and proprietary information of Renren.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with zhangyue.com.
 */
package com.zhangyue.zeus.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zhangyue.zeus.dao.MyBatisDao;
import com.zhangyue.zeus.entity.HiveTableEntity;
import com.zhangyue.zeus.exception.HiveTableServiceException;
import com.zhangyue.zeus.service.IAuthorityManageService;
import com.zhangyue.zeus.util.CheckUserTablePermission;
import com.zhangyue.zeus.vo.CheckResult;

/**
 * 权限管理实现类
 * 
 * @date 2013-9-6
 * @author rongneng
 */
@Service("authorityManageService")
public class AuthorityManageServiceImpl implements IAuthorityManageService {

    private static final Log LOG = LogFactory.getLog(AuthorityManageServiceImpl.class);
    @Autowired
    private MyBatisDao myBatisDao;

    @Override
    public CheckResult checkPermission(int userId, String sql) {
        CheckResult result = new CheckResult();
        CheckUserTablePermission ctp = new CheckUserTablePermission();
        if (StringUtils.isBlank(sql)) {
            result.setMsg("你的查询sql不能为空！");
            result.setResult(true);
            return result;
        }
        HiveTableEntity hiveTableEntity = new HiveTableEntity();
        hiveTableEntity.setUserId(userId);
        List<String> perList = new ArrayList<String>();
        List<HiveTableEntity> tableList = null;
        // 查询用户对应的表
        try {
            tableList = myBatisDao.findList("hiveTableMapper.findHiveTableLListByUserId", hiveTableEntity);
        } catch (Exception e) {
            LOG.error("findHiveTableLListByUserId  error!", e);
            throw new HiveTableServiceException("findHiveTableLListByUserId  error!", e);
        }
        if (null == tableList || tableList.size() == 0) {
            result.setMsg("你现在没有可以查询的表,请联系管理员申请权限！");
            result.setResult(true);
            return result;
        }
        for (HiveTableEntity vo : tableList) {
            perList.add(vo.getTableName().toLowerCase());
        }
        // 验证权限
        result = ctp.checkPermission(sql, perList);
        return result;
    }

}
