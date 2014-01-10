/*
 * Copyright 2013 zhangyue.com All right reserved. This software is the
 * confidential and proprietary information of Renren.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with zhangyue.com.
 */
package com.test.zhangyue.zeus.service;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zhangyue.zeus.entity.QueriesEntity;
import com.zhangyue.zeus.service.ITaskManageService;
import com.zhangyue.zeus.util.Page;
import com.zhangyue.zeus.util.SpringContextUtil;

/**
 * TaskManageService服务测试类
 * 
 * @date 2013-12-27
 * @author rongneng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:service-context.xml")
public class TaskManageServiceTest {
    
    @Autowired
    private   ITaskManageService   taskManageService;
    @Test
    public void  addQueryTaskTest() {
     // {created},#{groupId},#{taskName},#{querySql},#{resultLocation},#{status},#{userId}
        QueriesEntity entity = new  QueriesEntity();
        entity.setCreated("2013-12-30");
        entity.setTaskName("weirongneng task");
        entity.setQuerySql("select * from  USER");
        entity.setResultLocation("/USER/LOCALTION");
        entity.setStatus("RUNNING");
        entity.setUserId(1);
        entity.setUpdated("2013-12-30");
        taskManageService.setQuery(entity);
            taskManageService.addQueryTask();
        boolean  ret = false;
        if (entity.getId() > 0) {
            ret = true;
        }
        Assert.assertTrue(ret);
    }
    
   /* @Test
    public void  updateQueryTaskTest() {
        QueriesEntity entity = new  QueriesEntity();
        entity.setCpuTime(23432432);
        entity.setErrorCode(9);
        entity.setErrorMsg("fslkjdsfslkdflksafdasfa");
        entity.setJobId("29873453243298;3wr432432243;2343243232;");
        entity.setResultLocation("ASDFSAFSAFSDAFSAETRETRET");
        entity.setStatus("SUCCESSED");
        entity.setTotalTime(23432432);
        entity.setId(104);
        taskManageService.setQuery(entity);
        taskManageService.updateQueryTask();
    }*/
    /*@Test
    public void findSumbitTaskPageTest() {
        QueriesEntity entity = new  QueriesEntity();
        entity.setUserId(1);
        entity.setTaskName("weirongneng");
        entity.setPageNo(2);
        entity.setPageSize(10);
        taskManageService.setQuery(entity);
        Page<QueriesEntity>  page = taskManageService.findSumbitTaskPage();
        page.getTotalCount();
        
    }*/
   /* @Test
    public  void findSumbitTaskById() {
        QueriesEntity entity = new  QueriesEntity();
        entity.setId(137);
        ITaskManageService   taskManageService = (ITaskManageService)SpringContextUtil.getBean("taskManageService");
        taskManageService.setQuery(entity);
        QueriesEntity  entity2 = taskManageService.findSumbitTaskById();
        Assert.assertEquals(137, entity2.getId());
    }*/

}
