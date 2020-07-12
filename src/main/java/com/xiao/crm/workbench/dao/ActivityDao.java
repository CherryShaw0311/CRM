package com.xiao.crm.workbench.dao;

import com.xiao.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

/**
 * @author  XiaoHan
 * @date  2020/7/11 12:09
 * @version 1.0
 */
public interface ActivityDao {
    

    int save(Activity activity);

    List<Activity> getDataListByCondition(Map<String, Object> map);

    int getTotalByCondition(Map<String, Object> map);

    int delete(String[] ids);

    Activity getById(String id);

    int update(Activity activity);
}
