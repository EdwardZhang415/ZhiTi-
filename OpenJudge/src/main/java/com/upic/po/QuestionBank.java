package com.upic.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upic.base.entiy.BaseEntity;
import com.upic.enums.QuestionBankStatusEnum;
import com.upic.enums.QuestionBankTypeEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created by zhubuqing on 2018/3/29.
 * 题库
 */
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class QuestionBank extends BaseEntity {
    private String questionBankName; // 题库名称

    @Enumerated(EnumType.STRING)
    private QuestionBankTypeEnum type; // 题库类型

    @Enumerated(EnumType.STRING)
    private QuestionBankStatusEnum status; // 状态

    private long followId; // 跟随ID
}
