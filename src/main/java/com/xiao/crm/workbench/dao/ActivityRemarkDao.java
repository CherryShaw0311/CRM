package com.xiao.crm.workbench.dao;

/**
 * @author XiaoHan
 * @version 1.0
 * @date 2020/7/11 15:08
 */
public interface ActivityRemarkDao {
    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);
}
