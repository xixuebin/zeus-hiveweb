package com.zhangyue.zeus.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.zhangyue.zeus.entity.UserEntity;
import com.zhangyue.zeus.service.IUserService;

/**
 * 系统filter类
 * 
 * @date 2014-9-6
 * @author rongneng
 */
public class LoginFilter extends HttpServlet implements Filter {

    private static final long serialVersionUID = 1L;
    private IUserService userService;
    private static Logger LOG = Logger.getLogger(LoginFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {

        WebApplicationContext wac =
                WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
        userService = (IUserService) wac.getBean("userService");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
        ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        String userName = req.getParameter("userName");
        String password = req.getParameter("pwd");
        HttpSession session = req.getSession();
        String url = req.getRequestURI();
        if (url.endsWith(".css") || url.endsWith(".gif") || url.endsWith(".js") || url.endsWith(".less")
            || url.endsWith(".jpg") || url.endsWith(".png") || url.endsWith("login") || url.endsWith("hwi") 
            || url.endsWith("error.jsp") ) {
            chain.doFilter(req, resp);
            return;
        }
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
            userName = (String) session.getAttribute("userName");
            password = (String) session.getAttribute("pwd");
        }
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
            resp.sendRedirect("/login");
            return;
        } else if (StringUtils.isNotBlank(userName) || StringUtils.isNotBlank(password)) {
            chain.doFilter(req, resp);
            LOG.info(userName + " : " + url);
            return;
        }
        try {
            UserEntity userEntity = new UserEntity(userName, password, 0);
            userService.setUserEntity(userEntity);
            UserEntity user = userService.queryUser();
            if (null == user || null == user.getUserName()) {
                request.setAttribute("msg", "你的用户名或者密码不正确");
                resp.sendRedirect("/login");
            } else {
                req.getSession().setAttribute("userName", user.getUserName());
                req.getSession().setAttribute("level", user.getLevel());
                req.setAttribute("level", user.getLevel());
                req.getSession().setAttribute("userId", user.getId());
                req.setAttribute("userName", user.getUserName());
                chain.doFilter(req, resp);
                return;
            }
        } catch (Exception e) {
            LOG.error("Fail to validate user. userName:" + userName, e);
        }
    }
    public static void main(String[] args) {
        String  str = "hwi/login";
        String  ss = "oiuuoiu/hwi/login";
        System.out.println(ss.endsWith(str));
    }
    
}
