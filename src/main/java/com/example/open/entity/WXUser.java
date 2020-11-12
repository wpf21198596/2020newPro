package com.example.open.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "wx_user")
public class WXUser  implements Serializable {

    @Id
    @GeneratedValue(generator="id")
    @GenericGenerator(name="id", strategy="uuid")
    private String id;

    @Column
    private String nickName;

    @Column
    private String openId;

    @Column
    private Date createTime;

}
