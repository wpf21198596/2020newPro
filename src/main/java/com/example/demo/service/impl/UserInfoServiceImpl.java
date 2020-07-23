package com.example.demo.service.impl;

import com.example.demo.dao.UserInfoDao;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserInfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserInfoServiceImpl implements UserInfoService {


    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public UserInfo findByUsername(String username) {
        return userInfoDao.findByUsername(username);
    }

    @Override
    public UserInfo save(UserInfo userInfo) {
        UserInfo byUsername = userInfoDao.findByUsername(userInfo.getUsername());
        if(byUsername!=null){
            return null;
        }
        Subject subject = SecurityUtils.getSubject();
        String ip = subject.getSession().getHost();
        userInfo.setIpAddress(ip);
        userInfo.setCreateDate(new Date());
        //生成盐（部分，需要存入数据库中）
        String random=new SecureRandomNumberGenerator().nextBytes().toHex();
        //将原始密码加盐（上面生成的盐），并且用md5算法加密三次，将最后结果存入数据库中
        String password = new Md5Hash(userInfo.getPassword(),random,3).toString();
        userInfo.setSalt(random);
        userInfo.setPassword(password);
        UserInfo user = userInfoDao.save(userInfo);
        return user;
    }
}
