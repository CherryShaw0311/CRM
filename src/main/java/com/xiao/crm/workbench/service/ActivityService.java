package com.xiao.crm.workbench.service;

import com.xiao.crm.vo.PaginationVO;
import com.xiao.crm.workbench.domain.Activity;
import com.xiao.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

/**
 * @author  XiaoHan
 * @date  2020/7/11 12:47
 * @version 1.0
 */
public interface ActivityService {

    boolean save(Activity activity);

    PaginationVO<Activity> pageList(Map<String, Object> map);

    boolean delete(String[] ids);

    Map<String, Object> getUserListAndActivity(String id);

    boolean update(Activity activity);

    Activity detail(String id);

    List<ActivityRemark> getRemarkListByAid(String activityId);

    boolean deleteRemark(String id);

    boolean saveRemark(ActivityRemark ar);
}
