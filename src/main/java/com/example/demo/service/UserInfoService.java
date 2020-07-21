package com.example.demo.service;

import com.example.demo.entity.UserInfo;

public interface UserInfoService {
    UserInfo findByUsername(String username);

    UserInfo save(UserInfo userInfo);
}
