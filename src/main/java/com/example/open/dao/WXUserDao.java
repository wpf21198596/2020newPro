package com.example.open.dao;

import com.example.open.entity.WXUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface WXUserDao extends JpaRepository<WXUser,String> {
}
