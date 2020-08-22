package com.upic.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upic.base.entiy.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;

/**
 * Created by zhubuqing on 2018/3/30.
 * 作业
 */
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Homework extends BaseEntity {
    private long teamId; // 团队ID

    private String homeworkName; // 作业名

    private String type; // 作业类别

    private String content; //作业内容
}
