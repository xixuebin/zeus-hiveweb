/*
 * Copyright 2013 zhangyue.com All right reserved. This software is the
 * confidential and proprietary information of Renren.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with zhangyue.com.
 */
package com.zhangyue.zeus.entity;

/**
 * hive信息表实体类
 * @date 2013-9-6
 * @author rongneng
 */

public class Entity {
    
    protected  int pageStart;             // 页面显示开始索引
    
    protected  int pageSize;              // 每一页显示的最大条数
    
    protected  int pageNo;                // 页的序号
    
    protected  int count;                 // 总数
    
    public Entity(){
        
    }
    /**
     * @return the pageStart
     */
    public int getPageStart() {
        return pageStart;
    }
    
    /**
     * @param pageStart the pageStart to set
     */
    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }
    
    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }
    
    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    /**
     * @return the pageNo
     */
    public int getPageNo() {
        return pageNo;
    }
    
    /**
     * @param pageNo the pageNo to set
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    
    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    
    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }
    
}
