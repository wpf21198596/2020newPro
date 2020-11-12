package com.example.open.dao;

import com.example.open.entity.WXMyHelp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface WXMyHelpDao extends JpaRepository<WXMyHelp,String> {
    List<WXMyHelp> findByHelper(String id);

    List<WXMyHelp> findByBillId(String id);
}
