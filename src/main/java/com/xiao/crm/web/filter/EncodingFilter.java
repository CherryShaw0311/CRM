package com.xiao.crm.web.filter;

import javax.imageio.spi.ServiceRegistry;
import javax.servlet.*;
import java.io.IOException;

/**
 * @author  XiaoHan
 * @date  2020/7/11 9:55
 * @version 1.0
 */
public class EncodingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        System.out.println("进入到过滤字符编码的过滤器");
        //过滤post请求中文参数乱码
        req.setCharacterEncoding("UTF-8");

        //过滤响应流响应中文乱码的问题
        resp.setContentType("text/html;charset=utf-8");

        //将请求放行
        chain.doFilter(req,resp);
    }
}