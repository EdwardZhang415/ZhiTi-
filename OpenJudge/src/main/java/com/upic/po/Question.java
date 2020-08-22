package com.upic.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upic.base.entiy.BaseEntity;
import com.upic.enums.QuestionStatusEnum;
import com.upic.enums.PublicTypeEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created by zhubuqing on 2018/3/29.
 * 题目
 */
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Question extends BaseEntity {
    private String questionName; // 名字

    private String background; // 背景

    private String describtion; // 描述

    private String input; // 输入格式

    private String output; // 输出格式

    private String num; // 编号

    private String hintsAndInstructions; // 提示与说明

    private String fileAddress; // 文件地址

    private String patternSelection; // 模式选择（显示分数）

    private String subtask; // 得分策略

    @Enumerated(EnumType.STRING)
    private QuestionStatusEnum status; // 题目状态

    @Enumerated(EnumType.STRING)
    private PublicTypeEnum publicType; // 公开类型

    private String standardProcess; // 标程

    private String degreeOfDifficulty; // 难度系数

    private long questionBankId; // 题库ID

    private long memoryLimit;//内存限制

    private long timeLimit;//时间限制
}
