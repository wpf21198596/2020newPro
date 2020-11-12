package com.example.open.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "wx_my_help")
public class WXMyHelp {

    @Id
    @GeneratedValue(generator="id")
    @GenericGenerator(name="id", strategy="uuid")
    private String id;

    @Column//帮助人ID
    private String helper;

    @Column//帮助的人ID
    private String benefiter;

    @Column//帮助的单子
    private String billId;

    @Column//反馈状态 0无 1有效 2无效
    private Integer state;

    @Column//反馈时间
    private Date feedbackTime;

    @Column//创建时间
    private Date createTime;

}
