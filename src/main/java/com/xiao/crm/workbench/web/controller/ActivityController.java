package com.xiao.crm.workbench.web.controller;

import com.xiao.crm.settings.domain.User;
import com.xiao.crm.settings.service.UserService;
import com.xiao.crm.settings.service.impl.UserServiceImlp;
import com.xiao.crm.utils.*;
import com.xiao.crm.vo.PaginationVO;
import com.xiao.crm.workbench.domain.Activity;
import com.xiao.crm.workbench.domain.ActivityRemark;
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
        else if ("/workbench/activity/delete.do".equals(path)){
            delete(request,response);
        }else if ("/workbench/activity/getUserListAndActivity.do".equals(path)){
            getUserListAndActivity(request,response);
        }else if ("/workbench/activity/update.do".equals(path)){
            update(request,response);
        }else if ("/workbench/activity/detail.do".equals(path)){
            detail(request,response);
        }else if ("/workbench/activity/getRemarkListByAid.do".equals(path)){
            getRemarkListByAid(request,response);

        }else if ("/workbench/activity/deleteRemark.do".equals(path)){
            deleteRemark(request,response);

        }else if ("/workbench/activity/saveRemark.do".equals(path)){
            saveRemark(request,response);
        }


    }

    private void saveRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行添加备注操作");
        String noteContent = request.getParameter("noteContent");
        String activityId = request.getParameter("activityId");
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        String editFlag = "0";

        ActivityRemark ar = new ActivityRemark();
        ar.setNoteContent(noteContent);
        ar.setActivityId(activityId);
        ar.setId(id);
        ar.setCreateTime(createTime);
        ar.setCreateBy(createBy);
        ar.setEditFlag(editFlag);

        ActivityService activityService = (ActivityService)ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = activityService.saveRemark(ar);

        Map<String,Object> map = new HashMap<>();
        map.put("success",flag);
        map.put("ar",ar);
        PrintJson.printJsonObj(response,map);

    }

    private void deleteRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("删除备注操作");
        String id = request.getParameter("id");
        ActivityService activityService = (ActivityService)ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = activityService.deleteRemark(id);
        PrintJson.printJsonFlag(response,flag);
    }

    private void getRemarkListByAid(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("根据市场活动的id，取得备注信息列表");
        String activityId = request.getParameter("activityId");
        ActivityService activityService = (ActivityService)ServiceFactory.getService(new ActivityServiceImpl());
        List<ActivityRemark> arList = activityService.getRemarkListByAid(activityId);
        PrintJson.printJsonObj(response,arList);

    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("跳转到详细信息页");
        String id = request.getParameter("id");
        ActivityService activityService = (ActivityService)ServiceFactory.getService(new ActivityServiceImpl());
        Activity a = activityService.detail(id);

        //传统请求
        request.setAttribute("a",a);

        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request,response);



    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到修改市场活动的操作");

        String id = request.getParameter("id");
        String owner =request.getParameter("owner");
        String name =request.getParameter("name");
        String startDate =request.getParameter("startDate");
        String endDate =request.getParameter("endDate");
        String cost =request.getParameter("cost");
        String description =request.getParameter("description");
        //修改时间是系统时间
        String editTime = DateTimeUtil.getSysTime();
        //修改人是当前用户
        String editBy = ((User)request.getSession().getAttribute("user")).getName();

        ActivityService activityService = (ActivityService)ServiceFactory.getService(new ActivityServiceImpl());
        Activity activity = new Activity();
        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setEditBy(editBy);
        activity.setEditTime(editTime);
        boolean flag = activityService.update(activity);
        PrintJson.printJsonFlag(response,flag);
    }

    private void getUserListAndActivity(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("进入查询用户记录列表和根据市场活动id查询单条记录的操作");
        String id = request.getParameter("id");
        ActivityService activityService = (ActivityService)ServiceFactory.getService(new ActivityServiceImpl());
        /*
        * uList
        * a
        * 临时使用map*/
        Map<String,Object> map = new HashMap<>();

        map = activityService.getUserListAndActivity(id);
        PrintJson.printJsonObj(response,map);

    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行市场活动信息删除的操作（包含批量删除）");
        String[] ids = request.getParameterValues("id");
        ActivityService activityService = (ActivityService)ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = activityService.delete(ids);
        PrintJson.printJsonFlag(response,flag);

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



