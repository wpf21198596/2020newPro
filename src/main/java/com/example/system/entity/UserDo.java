package com.example.system.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserDo implements Serializable {
    private Long id;
    private String pickName;
    private String account;
    private String password;
    private String phone;
    private String createIp;
    private Date createTime;
}

