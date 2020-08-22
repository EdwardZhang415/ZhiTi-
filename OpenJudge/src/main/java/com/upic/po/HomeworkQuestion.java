package com.upic.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upic.base.entiy.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;

/**
 * Created by zhubuqing on 2018/3/30.
 * 作业题目
 */
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class HomeworkQuestion extends BaseEntity {
    private long homeworkId; // 作业ID

    private long questionId; // 题目ID
}
