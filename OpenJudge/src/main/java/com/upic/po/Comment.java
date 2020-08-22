package com.upic.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upic.base.entiy.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;

/**
 * Created by zhubuqing on 2018/3/30.
 * 评论
 */
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Comment extends BaseEntity {
    private long theSolutionId; // 题解ID

    private String comment; // 评论

    private long userId; // 用户ID
}
