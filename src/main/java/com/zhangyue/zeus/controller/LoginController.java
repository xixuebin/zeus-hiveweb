/*
 * Copyright 2013 zhangyue.com All right reserved. This software is the
 * confidential and proprietary information of Renren.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with zhangyue.com.
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
 * 登陆Controller
 * 
 * @date 2014-1-5
 * @author rongneng
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    private IUserService userService;
    /**
     * 跳转到登陆页面
     * @return
     */
    @RequestMapping(value = {"/","/hwi/login","/hwi"})
    public String welcomeRedirect() {
        return "login";
    }
    
    
    
    
    
    /**
     * 登陆处理方法，登陆成功转到default.vm页面
     * @param userName    用户名
     * @param pwd         密码
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam("userName") String userName,
        @RequestParam("pwd") String pwd) {
        if ((null == userName || userName.equals(""))
            || (null == pwd || pwd.equals(""))) {
            request.setAttribute("msg", "用户名或者密码不能为空");
            return new ModelAndView("/login");
        }
        UserEntity userParam = new UserEntity(userName, pwd, 0);
        userService.setUserEntity(userParam);
        UserEntity user = userService.queryUser();
        if (null == user || null == user.getUserName()) {
            request.setAttribute("msg", "你的用户名或者密码不正确");
            return new ModelAndView("/login");
        } else {
            request.getSession().setAttribute("userName", user.getUserName());
            request.getSession().setAttribute("pwd", user.getPwd());
            request.getSession().setAttribute("level", user.getLevel());
            request.getSession().setAttribute("userId", user.getId());
            request.setAttribute("level", user.getLevel());
            request.setAttribute("userName", user.getUserName());
        }
        return new ModelAndView("/layout/default");
    }
    /**
     * 判断该用户是否登陆跳转到登陆或者首页
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView view() {
        String userName = (String) request.getSession().getAttribute("userName");
        if ((null == userName || userName.equals(""))) {
            return new ModelAndView("/login");
        }
        else {
            return new ModelAndView("/layout/default");
            }
    }
    /**
     * 登出，注销session 并转到登陆页面
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout() {
        request.getSession().invalidate();
        return new ModelAndView("/login");
        }
    
    
}
