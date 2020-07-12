package com.xiao.crm.workbench.service.impl;

import com.xiao.crm.utils.SqlSessionUtil;
import com.xiao.crm.vo.PaginationVO;
import com.xiao.crm.workbench.dao.ActivityDao;
import com.xiao.crm.workbench.domain.Activity;
import com.xiao.crm.workbench.service.ActivityService;

import java.util.List;
import java.util.Map;

/**
 * @author  XiaoHan
 * @date  2020/7/11 12:47
 * @version 1.0
 */
public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);

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
}
