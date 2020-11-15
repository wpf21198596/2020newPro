package com.example.lifeRecord.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "pc_life_record")
public class LifeRecord {

    @Id
    @GeneratedValue(generator="id")
    @GenericGenerator(name="id", strategy="uuid")
    private String id;

    @Column//内容
    private String content;

    @Column//创建时间
    private Date createTime;

    @Column//创建人
    private Integer createUser;

}
