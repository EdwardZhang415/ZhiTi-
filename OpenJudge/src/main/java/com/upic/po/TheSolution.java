package com.upic.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upic.base.entiy.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;

/**
 * Created by zhubuqing on 2018/3/30.
 * 题解
 */
@Data
@Entity@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})

public class TheSolution extends BaseEntity {
    private long questionId; // 题目ID

    private long userId; // 用户ID

    private String code; // 代码

    private String explanations; // 说明
}
