package com.upic.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upic.base.entiy.BaseEntity;
import com.upic.enums.GiveTheThumbsUpTypeEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created by zhubuqing on 2018/3/30.
 * 点赞
 */
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class GiveTheThumbsUp extends BaseEntity {
    private long theSolutionId; // 题解ID

    private long userId; // 用户ID

    @Enumerated(EnumType.STRING)
    private GiveTheThumbsUpTypeEnum type; // 点赞类型
}
