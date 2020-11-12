package com.example.open.dao;

import com.example.open.entity.WXMyRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface WXMyRequestDao extends JpaRepository<WXMyRequest,String> {
    List<WXMyRequest> findByRequester(String userId);
}
