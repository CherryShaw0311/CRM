package com.xiao.crm.settings.service.impl;

import com.xiao.crm.exception.LoginException;
import com.xiao.crm.settings.dao.UserDao;
import com.xiao.crm.settings.domain.User;
import com.xiao.crm.settings.service.UserService;
import com.xiao.crm.utils.DateTimeUtil;
import com.xiao.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  XiaoHan
 * @date  2020/7/10 16:08
 * @version 1.0
 */
public class UserServiceImlp implements UserService {
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {

        Map<String,String> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);


        User user = userDao.login(map);
        if (user==null){
            throw new LoginException("账号密码错误");
        }

        //如果程序能够成功执行到这里：说明账号密码正确
        //需要继续向下验证
        String expireTime = user.getExpireTime();
        String currTime = DateTimeUtil.getSysTime();

        if (expireTime.compareTo(currTime)<0){

            throw new LoginException("账号已失效");

        }

        //判断锁定状态：
        String lockState = user.getLockState();
        if ("0".equals(lockState)){
            throw new LoginException("账号被锁定");
        }

        //判断IP
        String allowIps = user.getAllowIps();
        if (!allowIps.contains(ip)){
            throw new LoginException("IP地址受限");
        }

        return user;
    }

    @Override
    public List<User> getUserList() {
        List<User> userList = userDao.getUserList();
        return userList;

    }
}
