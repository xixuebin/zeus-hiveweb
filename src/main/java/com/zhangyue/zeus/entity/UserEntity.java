/*
 * Copyright 2013 Zhangyue.com All right reserved. This software is the
 * confidential and proprietary information of Renren.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Zhangyue.com.
 */
package com.zhangyue.zeus.entity;

import java.io.Serializable;

/**
 * 用户实体类
 * @date 2013-9-6
 * @author rongneng
 */
public class UserEntity extends Entity  implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int id;                                     // 用户ID
    
    private String userName;                            // 用户名
    
    private String pwd;                                 // 密码
    
    private String isDelete;                            // 是否删除 Y-删除 N-未删除 逻辑删除
    
    private int level;                                  // 1-普通用户 2-管理员

    public UserEntity(){

    }
    
    /**
     * @param userName
     * @param pwd
     * @param level
     */
    public UserEntity(String userName, String pwd, int level){
        super();
        this.userName = userName;
        this.pwd = pwd;
        this.level = level;
    }

    /**
     * @param id
     * @param userName
     * @param pwd
     * @param isDelete
     * @param level
     */
    public UserEntity(int id, String userName, String pwd, String isDelete, int level){
        super();
        this.id = id;
        this.userName = userName;
        this.pwd = pwd;
        this.isDelete = isDelete;
        this.level = level;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @param pwd the pwd to set
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * @return the isDelete
     */
    public String getIsDelete() {
        return isDelete;
    }

    /**
     * @param isDelete the isDelete to set
     */
    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }
}
