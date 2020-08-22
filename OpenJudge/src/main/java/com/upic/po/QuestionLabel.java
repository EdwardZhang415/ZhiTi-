package com.upic.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upic.base.entiy.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;

/**
 * Created by zhubuqing on 2018/3/30.
 * 题目标签中间表
 */
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class QuestionLabel extends BaseEntity {
    private long questionId; // 问题ID

    private long theLabelId; // 标签ID
}
