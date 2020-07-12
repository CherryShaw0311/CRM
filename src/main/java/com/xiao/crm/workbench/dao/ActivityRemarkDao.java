package com.xiao.crm.workbench.dao;

import com.xiao.crm.workbench.domain.ActivityRemark;

import java.util.List;

/**
 * @author XiaoHan
 * @version 1.0
 * @date 2020/7/11 15:08
 */
public interface ActivityRemarkDao {
    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);

    List<ActivityRemark> getRemarkListByAid(String activityId);

    int deleteRemark(String id);

    int saveRemark(ActivityRemark ar);
}
