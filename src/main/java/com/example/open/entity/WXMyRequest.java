package com.example.open.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "wx_my_request")
public class WXMyRequest {

    @Id
    @GeneratedValue(generator="id")
    @GenericGenerator(name="id", strategy="uuid")
    private String id;

    @Column//请求人
    private String requester;

    @Column//口令
    private String command;

    @Column//状态 0进行中 1结束
    private Integer state;

    @Column//创建时间
    private Date createTime;

    private List<WXMyHelp> helpList;

}
