package com.example.lifeRecord.dao;

import com.example.lifeRecord.entity.LifeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface LifeRecordDao extends JpaRepository<LifeRecord,String> {

    List<LifeRecord> findByCreateUserOrderByCreateTimeDesc(Integer uid);
}
