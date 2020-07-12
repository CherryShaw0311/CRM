package com.xiao.settings.test;

import com.xiao.crm.settings.dao.UserDao;
import com.xiao.crm.settings.domain.User;
import com.xiao.crm.utils.DateTimeUtil;
import com.xiao.crm.utils.MD5Util;
import com.xiao.crm.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import javax.crypto.spec.PSource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author  XiaoHan
 * @date  2020/7/10 16:37
 * @version 1.0
 */
public class Test1 {
    /*public static void main(String[] args) {
        //验证失效时间
        String expireTime = "2020-10-10 10:10:10";
        //当前系统时间
        String currentTime = DateTimeUtil.getSysTime();
        System.out.println(currentTime);

        int count = expireTime.compareTo(currentTime);
        System.out.println(count);

        String lockState = "0";
        if ("0".equals(lockState)){
            System.out.println("账号已锁定");
        }

        //浏览器端的ip
        String ip = "192.168.1.1";
        String allowIps = "192.168.1.1,192.168.1.2";
        if (allowIps.contains(ip)){
            System.out.println("有效的IP地址，允许登录");
        }else {
            System.out.println("ip地址无效，请联系管理员");
        }

        String pwd = "123";
        String pwd_ = MD5Util.getMD5(pwd);
        System.out.println(pwd_);
    }
    @Test
    public void testLogin(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        UserDao dao = sqlSession.getMapper(UserDao.class);
        Map<String,String> map = new HashMap<>();
        map.put("loginAct","ls");
        map.put("loginPwd","202cb962ac59075b964b07152d234b70");
        User user = dao.login(map);
        System.out.println(user);
    }
*/
}
