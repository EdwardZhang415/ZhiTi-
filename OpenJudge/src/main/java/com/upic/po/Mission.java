package com.upic.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upic.base.entiy.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;

/**
 * Created by zhubuqing on 2018/3/30.
 * 任务
 */
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Mission extends BaseEntity {
    private long provingGroundsId; // 试炼场ID

    private String missionNum; // 任务编号

    private String missionName; // 任务名称

    private String explanations; // 说明

    private int numberOfRightAnswers; // 答对题数

    private int isPrerequisites; // 是否先决要求

    private double degreeOfDifficulty; // 难度系数
}
