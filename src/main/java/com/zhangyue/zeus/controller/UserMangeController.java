/*
 * Copyright 2014 ireader.com All right reserved. This software is the
 * confidential and proprietary information of ireader.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ireader.com.
 */
package com.zhangyue.zeus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.zhangyue.zeus.entity.UserEntity;
import com.zhangyue.zeus.service.IUserService;

/**
 * 用户管理controller
 * 
 * @date 2014-1-5
 * @author rongneng
 */
@Controller
@RequestMapping("/users")
public class UserMangeController extends BaseController {

    @Autowired
    private IUserService userService;

    /**
     * 添加用户
     * 
     * @param userName 用户名
     * @param passwd 密码
     * @param level 用户角色 1-管理员 2-普通用户
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ModelAndView addUser(@RequestParam(value = "userName", required = false) String userName,
        @RequestParam(value = "passwd", required = false) String passwd,
        @RequestParam(value = "level", required = false) String level) {
        if ((null == userName || userName.equals("")) || (null == passwd || passwd.equals(""))) {
            request.setAttribute("msg", "用户名或者密码不能为空");
            return new ModelAndView("/user/set");
        }
        int level1 = Integer.parseInt(level);
        int ret = 0;
        UserEntity userEntity = new UserEntity(userName, passwd, level1);
        userService.setUserEntity(userEntity);
        ret = userService.addUser(); // -1 已经存在该用户名
        String msg = "set user successful!";
        if (ret != -1) {
            msg = "添加用户成功！";
        } else {
            msg = "该用户已经存在";
        }
        request.setAttribute("msg", msg);
        request.setAttribute("msg-type", "success");
        return new ModelAndView("/user/set");
    }

    /**
     * 访问添加用户页面
     * 
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public ModelAndView addUser() {
        request.setAttribute("user", getUserName());
        return new ModelAndView("/user/set");
    }

}
