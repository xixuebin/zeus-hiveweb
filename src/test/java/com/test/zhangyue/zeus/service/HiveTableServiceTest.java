/*
 * Copyright 2013 zhangyue.com All right reserved. This software is the
 * confidential and proprietary information of Renren.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with zhangyue.com.
 */
package com.test.zhangyue.zeus.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zhangyue.zeus.entity.HiveTableEntity;
import com.zhangyue.zeus.service.IHiveTableService;
import com.zhangyue.zeus.util.Page;

/**
 * hiveTable服务测试类
 * 
 * @date 2013-12-27
 * @author rongneng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:service-context.xml")
public class HiveTableServiceTest {
    @Autowired
    private  IHiveTableService  hiveTableService;
    
   /*@Test
    public void addHiveTableTest() {
        HiveTableEntity   entity =  new HiveTableEntity(2, "wrntest21", "韦荣能测试", "default");
        hiveTableService.setHiveTableEntity(entity);
        int ret = hiveTableService.addHiveTable();
        Assert.assertEquals(-1, ret);
    }*/
    
    /*@Test
    public  void addTableFieldTest() {
        List<HiveTableEntity>  hiveList = new ArrayList<HiveTableEntity>();
        HiveTableEntity  en1 = new HiveTableEntity(1, "名字", "name", "string", "N");
        HiveTableEntity  en2 = new HiveTableEntity(1, "名字", "age", "string", "N");
        HiveTableEntity  en3 = new HiveTableEntity(1, "名字", "addr", "string", "N");
        hiveList.add(en1);
        hiveList.add(en2);
        hiveList.add(en3);
        hiveTableService.addTableField(hiveList);
    }*/
    
   /* @Test
    public void addHiveTableAndField(){
        HiveTableEntity  en1 = new  HiveTableEntity(0, 1, "wrntest1", "韦荣能测试", "default", 
             "test","test1", "string", "N");
        HiveTableEntity  en2 = new  HiveTableEntity(0, 1, "wrntest1", "韦荣能测试", "default", 
            "test","test1", "string", "N");
        HiveTableEntity  en3 = new  HiveTableEntity(0, 1, "wrntest1", "韦荣能测试", "default", 
            "test","test1", "string", "N");
        List<HiveTableEntity>  hiveList = new ArrayList<HiveTableEntity>();
        hiveList.add(en1);
        hiveList.add(en2);
        hiveList.add(en3);
        hiveTableService.addHiveTableAndField(hiveList);
    }*/
    
   /* @Test
    public void findAllTableTest() {
        List<HiveTableEntity>  retList = hiveTableService.findAllTable();
    }*/
    
    /*@Test
    public void findAllTableFieldByIdTest() {
        HiveTableEntity entity = new HiveTableEntity(1, 0, null, null, null);
        hiveTableService.setHiveTableEntity(entity);
        List<HiveTableEntity>  retList = hiveTableService.findAllTableFieldById();
        System.out.println(retList.size());
    }*/
    
    /*@Test
    public void  deleteTableFieldByIdTest() {
        HiveTableEntity entity = new HiveTableEntity();
        entity.setFieldId(4);
        hiveTableService.setHiveTableEntity(entity);
        hiveTableService.deleteTableFieldById();
    }*/
    
    /*@Test
    public void  deleteTableFieldByIdTest() {
        HiveTableEntity entity = new HiveTableEntity();
        entity.setId(1);
        hiveTableService.setHiveTableEntity(entity);
        hiveTableService.deleteFieldByTableId();
    }*/
    /*@Test
    public void  updateHiveTable() {
        HiveTableEntity entity = new HiveTableEntity();
        entity.setId(1);
        entity.setComment("weirongnenngngngngngngn");
        entity.setTableName("uuuuuuuuuuuuuuuuuu");
        hiveTableService.setHiveTableEntity(entity);
        hiveTableService.updateHiveTable();
    }
    */
    /*@Test
    public void  findHiveTableLListByUserId () {
        HiveTableEntity entity = new HiveTableEntity();
        entity.setUserId(2);
        hiveTableService.setHiveTableEntity(entity);
        List<HiveTableEntity> retList = hiveTableService.findHiveTableLListByUserId();
        Assert.assertNotNull(retList);
    }*/
   /* @Test
    public void  deleteTableFieldByIdTest() {
        HiveTableEntity entity = new HiveTableEntity();
        entity.setId(1);
        hiveTableService.setHiveTableEntity(entity);
        hiveTableService.deleteHiveTableById();
    }*/
    @Test
    public  void findHivePageTable() {
        HiveTableEntity  entity = new HiveTableEntity();
        entity.setPageSize(10);
        entity.setPageNo(2);
        hiveTableService.setHiveTableEntity(entity);
        try {
            Page<HiveTableEntity>  page = hiveTableService.findHivePageTable();
            page.getTotalCount();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        
    }
    
}
