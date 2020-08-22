package com.upic.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upic.base.entiy.BaseEntity;
import com.upic.enums.TeamMembersTypeEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created by zhubuqing on 2018/3/30.
 * 团队成员
 */
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class TeamMembers extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private TeamMembersTypeEnum type; // 成员类型

    private String name; // 成员名称

    private long userId; // 成员ID

    private long teamId; // 团队ID
}
