package com.upic.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upic.base.entiy.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;

/**
 * Created by zhubuqing on 2018/3/29.
 * 标签
 */
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class TheLabel extends BaseEntity {
    private String theLabelName; // 标签名称
}
