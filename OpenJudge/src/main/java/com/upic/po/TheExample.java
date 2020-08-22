package com.upic.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upic.base.entiy.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;

/**
 * Created by zhubuqing on 2018/3/29.
 * 样例
 */
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class TheExample extends BaseEntity {
    private long questionId; // 问题ID

    private String exampleInput; // 样例输入

    private String exampleOutput; // 样例输出
}
