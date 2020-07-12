package com.xiao.crm.settings.service;

import com.xiao.crm.exception.LoginException;
import com.xiao.crm.settings.domain.User;

import java.util.List;

/**
 * @author XiaoHan
 * @version 1.0
 * @date 2020/7/10 16:08
 */
public interface UserService {
    User login(String loginAct, String loginPwm, String ip) throws LoginException;

    List<User> getUserList();
}
