package com.xiao.crm.workbench.service.impl;

import com.xiao.crm.settings.dao.UserDao;
import com.xiao.crm.settings.domain.User;
import com.xiao.crm.utils.SqlSessionUtil;
import com.xiao.crm.vo.PaginationVO;
import com.xiao.crm.workbench.dao.ActivityDao;
import com.xiao.crm.workbench.dao.ActivityRemarkDao;
import com.xiao.crm.workbench.domain.Activity;
import com.xiao.crm.workbench.domain.ActivityRemark;
import com.xiao.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  XiaoHan
 * @date  2020/7/11 12:47
 * @version 1.0
 */
public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public boolean save(Activity activity) {

        boolean flag = true;
        int count = activityDao.save(activity);
        if (count!=1){
            flag = false;

        }
        return flag;

    }

    @Override
    public PaginationVO<Activity> pageList(Map<String, Object> map) {

        //取得total
        int total = activityDao.getTotalByCondition(map);

        //取得dataList
        List<Activity> activityList = activityDao.getDataListByCondition(map);
        //封装到vo

        PaginationVO vo = new PaginationVO();
        vo.setTotal(total);
        vo.setDataList(activityList);

        //返回
        return vo;
    }

    @Override
    public boolean delete(String[] ids) {
        boolean flag = true;
        //查询出需要删除的备注的数量
        int count1 = activityRemarkDao.getCountByAids(ids);

        //删除备注：返回一个收到影响的条数（实际删除的数量）
        int count2 = activityRemarkDao.deleteByAids(ids);
        if (count1!=count2){
            flag = false;
        }

        //删除市场活动
        int count3 = activityDao.delete(ids);
        if (count3!=ids.length){
            flag = false;
        }

        return flag;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {
        Map<String,Object> map = new HashMap<>();

        //取uList
        List<User> uList = userDao.getUserList();

        //取a
        Activity a = activityDao.getById(id);

        //打包
        map.put("uList",uList);
        map.put("a",a);


        return map;
    }

    @Override
    public boolean update(Activity activity) {
        boolean flag = true;
        int count = activityDao.update(activity);
        if (count!=1){
            flag = false;

        }
        return flag;
    }

    @Override
    public Activity detail(String id) {
        Activity a = null;
        a = activityDao.detail(id);
        return a;
    }

    @Override
    public List<ActivityRemark> getRemarkListByAid(String activityId) {
        List<ActivityRemark> arList = activityRemarkDao.getRemarkListByAid(activityId);
        return arList;
    }

    @Override
    public boolean deleteRemark(String id) {
        boolean flag = true;
        int count = activityRemarkDao.deleteRemark(id);
        if (count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public boolean saveRemark(ActivityRemark ar) {
        boolean flag = true;
        int count = activityRemarkDao.saveRemark(ar);
        if (count!=1){
            flag=false;
        }
        return flag;
    }
}
