package com.xiao.crm.web.filter;

import com.xiao.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author  XiaoHan
 * @date  2020/7/11 10:42
 * @version 1.0
 */
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        System.out.println("进入到验证有没有登陆过的过滤器");

        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String path = request.getServletPath();

        //不应该被拦截的资源
        if ("/login.jsp".equals(path) || "/settings/user/login.do".equals(path)){
            System.out.println("无条件放行");
            chain.doFilter(request,response);
        }else {
            System.out.println("其他资源必须验证");
            //其他资源必须验证
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            //如果user不为空，说明登陆过
            if (user!=null){
                chain.doFilter(request,response);
            }else {
                //重定向：
                /*
                 * 重定向的路径怎么写？
                 *   在实际项目开发中，对于路径，不论是前端还是后端，应该一律是绝对路径
                 *   关于转发和重定向的路径写法如下：
                 *       转发：
                 *       使用的是一种特殊的绝对路径，这种绝对路径前面不加/项目名，这种路径称之为内部路径
                 *       /login.jsp
                 *
                 *       重定向：
                 *       使用的是传统的绝对路径写法，前面必须以/项目名开头，后面跟着具体的资源名
                 *       /crm/login.jsp
                 *
                 * 为什么使用重定向？使用转发不行吗？
                 *   转发之后，路径会停留在老路径上，而不是跳转之后最新资源的路径
                 *   我们应该在用户跳转到登录页的同时，将地址栏上的路径应该自动设置成登录页面的路径
                 * */
                response.sendRedirect(request.getContextPath()+"/login.jsp");
            }
        }



    }
}
