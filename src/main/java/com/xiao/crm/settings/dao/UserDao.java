package com.xiao.crm.settings.dao;

import com.xiao.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

/**
 * @author XiaoHan
 * @version 1.0
 * @date 2020/7/10 16:05
 */
public interface UserDao {

    User login(Map<String, String> map);

    List<User> getUserList();
}
