package com.xiao.crm.workbench.web.controller;

import com.xiao.crm.settings.domain.User;
import com.xiao.crm.settings.service.UserService;
import com.xiao.crm.settings.service.impl.UserServiceImlp;
import com.xiao.crm.utils.*;
import com.xiao.crm.vo.PaginationVO;
import com.xiao.crm.workbench.domain.Activity;
import com.xiao.crm.workbench.service.ActivityService;
import com.xiao.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  XiaoHan
 * @date  2020/7/10 16:10
 * @version 1.0
 */
public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到市场活动控制器");
        String path = request.getServletPath();

        if ("/workbench/activity/getUserList.do".equals(path)){
            getUserList(request,response);

        }else if ("/workbench/activity/save.do".equals(path)){
            save(request,response);
        }else if ("/workbench/activity/pageList.do".equals(path)){
            pageList(request,response);
        }

    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到查询市场活动信息列表的操作（结合条件查询+分页查询）");


        String name=request.getParameter("name");
        String owner=request.getParameter("owner");
        String startDate=request.getParameter("startDate");
        String endDate=request.getParameter("endDate");
        String pageNoStr = request.getParameter("pageNo");
        int pageNo = Integer.valueOf(pageNoStr);
        //每页展示的记录数
        String pageSizeStr = request.getParameter("pageSize");
        int pageSize = Integer.valueOf(pageSizeStr);
        //计算出略过的记录数
        int skipCount = (pageNo-1)*pageSize;

        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);

        ActivityService activityService = (ActivityService)ServiceFactory.getService(new ActivityServiceImpl());
        /*
        * 前端要什么？
        *   {"total":100,"dataList":[{市场活动1},{市场活动2},{..}]}
        * 业务层拿到以上信息后，如何返回？
        *       vo
        *       map
        *
        * PaginationVO<T>{
        *   private total;
        *   private List<T> list;
        * }
        * 分页查询，每个模块都有，所以我们选择使用一个通用的VO，比较方便
        *       */
        PaginationVO<Activity> vo = activityService.pageList(map);
        PrintJson.printJsonObj(response,vo);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行添加市场活动");
        String id = UUIDUtil.getUUID();
        String owner =request.getParameter("owner");
        String name =request.getParameter("name");
        String startDate =request.getParameter("startDate");
        String endDate =request.getParameter("endDate");
        String cost =request.getParameter("cost");
        String description =request.getParameter("description");
        //创建时间是系统时间
        String createTime = DateTimeUtil.getSysTime();
        //创建人是当前用户
        String createBy = ((User)request.getSession().getAttribute("user")).getName();

        ActivityService activityService = (ActivityService)ServiceFactory.getService(new ActivityServiceImpl());
        Activity activity = new Activity();
        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setCreateBy(createBy);
        activity.setCreateTime(createTime);
        boolean flag = activityService.save(activity);
        PrintJson.printJsonFlag(response,flag);
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("取得用户信息列表");
        UserService userService = (UserService)ServiceFactory.getService(new UserServiceImlp());
        List<User> userList = userService.getUserList();
        PrintJson.printJsonObj(response,userList);
    }


}



