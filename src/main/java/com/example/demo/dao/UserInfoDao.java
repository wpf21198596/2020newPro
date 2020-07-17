package com.example.demo.dao;

import com.example.demo.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserInfoDao extends JpaRepository<UserInfo,String> {
    UserInfo findByUsername(String username);
}
