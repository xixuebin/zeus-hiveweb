package com.zhangyue.zeus.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Controller抽象类
 * 
 * @date 2013-9-6
 * @author rongneng
 */
public abstract class BaseController {

    private static final Log LOG = LogFactory.getLog(BaseController.class);
    public static final String USER_COOKIE_NAME = "user";
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
    protected static HashMap<String, String> statusMap; // 任务运行状态
    protected String fileName; // 下载的文件名
    protected int page;
    protected int pagesize;

    public BaseController(){
    }

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    public static synchronized HashMap<String, String> getStatusMap() {
        if (null == statusMap) {
            statusMap = new HashMap<String, String>();
            statusMap.put("INITED", "初始化");
            statusMap.put("RUNNING", "正在运行");
            statusMap.put("FINISHED", "完成");
            statusMap.put("CANCELLED", "取消");
            statusMap.put("FAILED", "失败");
            statusMap.put("SYNTAXERROR", "语法错误");
        }
        return statusMap;
    }

    /**
     * set current user
     * 
     * @param user
     */
    protected void setUser(String user) {
        Cookie cookie = new Cookie(USER_COOKIE_NAME, user);
        cookie.setMaxAge(365 * 24 * 60 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 返回当前用户名
     * 
     * @return
     */
    protected String getUserName() {
        try {
            String userName = (String) request.getSession().getAttribute("userName");
            return userName;
        } catch (Exception e) {
            LOG.error("Request  session  don't  exit  userName ", e);
            return null;
        }
    }

    /**
     * 返回当前的用户ID
     * 
     * @return
     */
    protected int getUserId() {
        try {
            int userId = (Integer) request.getSession().getAttribute("userId");
            return userId;
        } catch (Exception e) {
            LOG.error("Request  session  don't  exit  userId ", e);
            return 0;
        }
    }

    protected int getLevle() {
        try {
            int userId = (Integer) request.getSession().getAttribute("level");
            return userId;
        } catch (Exception e) {
            LOG.error("Request  session  don't  exit  level ", e);
            return 0;
        }
    }

    /**
     * 设置响应的头文件
     */
    public void setResponseHeader() {
        try {
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition",
                "attachment;filename=" + java.net.URLEncoder.encode(this.fileName, "UTF-8"));
            // 客户端不缓存
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            LOG.error("Set response  interrnel error!!!", ex);
        }
    }

    /**
     * return user cookies
     * 
     * @return user cookies hashmap
     */
    protected HashMap<String, String> getCookies() {
        Cookie cookies[] = request.getCookies();
        HashMap<String, String> cookiesMap = new HashMap<String, String>();
        for (int i = 0; i < cookies.length; i++) {
            cookiesMap.put(cookies[i].getName(), cookies[i].getValue());
        }
        return cookiesMap;
    }

    protected void setDefaultContentType() {
        response.setContentType("text/html; charset=utf-8");
    }

    protected void ajaxOutPutJson(Object outputString) throws IOException {
        ajaxOutPut("json", outputString);
    }

    protected void ajaxOutPutXml(Object outputString) throws IOException {
        ajaxOutPut("xml", outputString);
    }

    protected void ajaxOutPut(String outputStyle, Object outputString) throws IOException {
        response.setCharacterEncoding("UTF-8");
        if ("json".equals(outputStyle)) response.setContentType("application/json");
        else if ("xml".equals(outputStyle)) response.setContentType("text/xml");
        else {
            response.setContentType("text/plain");
        }
        PrintWriter out = response.getWriter();
        out.write((outputString == null) ? "" : outputString.toString());
        out.flush();
        out.close();
    }

    /**
     * @return the page
     */
    public int getPage() {
        String p = request.getParameter("page");
        return Integer.valueOf(p);
    }

    /**
     * @param page the page to set
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * @return the pagesize
     */
    public int getPagesize() {
        String psize = request.getParameter("pagesize");
        return Integer.valueOf(psize);
    }

    /**
     * @param pagesize the pagesize to set
     */
    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

}
