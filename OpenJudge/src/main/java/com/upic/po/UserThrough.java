package com.upic.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upic.base.entiy.BaseEntity;
import com.upic.enums.UserThroughStatusEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created by zhubuqing on 2018/3/30.
 * 用户通过
 */
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class UserThrough extends BaseEntity {
    private long missionId; // 任务ID

    private long userId; // 用户ID

    @Enumerated(EnumType.STRING)
    private UserThroughStatusEnum status; // 状态
}
