package com.xiao.crm.settings.web.controller;

import com.xiao.crm.settings.domain.User;
import com.xiao.crm.settings.service.UserService;
import com.xiao.crm.settings.service.impl.UserServiceImlp;
import com.xiao.crm.utils.MD5Util;
import com.xiao.crm.utils.PrintJson;
import com.xiao.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author  XiaoHan
 * @date  2020/7/10 16:10
 * @version 1.0
 */
public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到用户控制器");
        String path = request.getServletPath();

        if ("/settings/user/login.do".equals(path)){
            login(request,response);

        }else if ("/settings/user/xxx.do".equals(path)){

        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("进入到验证登录");
        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");

        //将密码的明文形式转换为MD5的密文形式
        loginPwd = MD5Util.getMD5(loginPwd);
        String ip = request.getRemoteAddr();
        System.out.println("------ip-------"+ip);

        //未来的业务层开发统一使用代理类形态的接口对象
        UserService userService = (UserService)ServiceFactory.getService(new UserServiceImlp());

        User user = null;

        try {
            user = userService.login(loginAct,loginPwd,ip);
            request.getSession().setAttribute("user",user);
            //如果程序执行到此处，说明业务层没有为controller抛出任何的异常
            //如果成功了
            /*
            * {"success":true}*/
            PrintJson.printJsonFlag(response,true);


        } catch (Exception e) {
            e.printStackTrace();
            //一旦程序执行了catch信息，说明业务层为我们验证失败，为controller抛出异常
            /*
            * {"success":true,"msg":?}
            * */
            String msg = e.getMessage();
            /*
            * 我们现在作为controller，需要为ajax请求提供多项信息
            * 可以有两种手段：
            *   （1）将多项信息打包成一个map，将map解析成字符串
            *   （2）创建一个VO
            *           VO的属性有两个：
            *               private boolean success;
            *               private String msg;
            * */
            Map<String,Object> map = new HashMap<>();
            map.put("succsee",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(response,map);
        }




    }


}
