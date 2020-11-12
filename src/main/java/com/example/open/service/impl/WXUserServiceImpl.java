package com.example.open.service.impl;

import com.example.open.dao.WXUserDao;
import com.example.open.service.WXUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WXUserServiceImpl implements WXUserService {
    @Autowired
    private WXUserDao wxUserDao;
}
