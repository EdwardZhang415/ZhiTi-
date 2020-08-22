package com.upic.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upic.base.entiy.BaseEntity;
import com.upic.enums.LanguageTypeEnum;
import com.upic.enums.TheSubmitStatusEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created by zhubuqing on 2018/3/29.
 * 提交
 */
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class TheSubmit extends BaseEntity {
    private long userId; // 用户ID

    @Enumerated(EnumType.STRING)
    private TheSubmitStatusEnum status; // 状态

    private long isFinished; //是否评测

    private long questionId; // 题目ID

    @Enumerated(EnumType.STRING)
    private LanguageTypeEnum languageType; // 语言类型

    private double timeConsuming; // 耗时

    private double memoryOccupancy; // 内存占用

    private double codeSize; // 代码大小

    private String targetCode; // 目标代码
}
