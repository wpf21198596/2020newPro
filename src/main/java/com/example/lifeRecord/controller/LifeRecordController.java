package com.example.lifeRecord.controller;

import com.example.demo.common.response.ServerResponse;
import com.example.demo.entity.UserInfo;
import com.example.lifeRecord.dao.LifeRecordDao;
import com.example.lifeRecord.entity.LifeRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/life/record")
public class LifeRecordController {

    @Autowired
    private LifeRecordDao lifeRecordDao;

    @PostMapping("/addRecord")
    public ServerResponse addRecord(LifeRecord lifeRecord){
        lifeRecord.setContent(lifeRecord.getContent().trim());
        lifeRecord.setCreateTime(new Date());
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        lifeRecord.setCreateUser(userInfo.getUid());
        LifeRecord save = lifeRecordDao.save(lifeRecord);
        if(StringUtils.isNotBlank(save.getId())) return ServerResponse.success();
        return ServerResponse.error();
    }

    @GetMapping("/getMyRecords")
    public ServerResponse getMyRecords(){
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        List<LifeRecord> list = lifeRecordDao.findByCreateUserOrderByCreateTimeDesc(userInfo.getUid());
        return ServerResponse.success(list);
    }
}
