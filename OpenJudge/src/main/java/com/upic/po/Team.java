package com.upic.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upic.base.entiy.BaseEntity;
import com.upic.enums.TeamOpennessTypeEnum;
import com.upic.enums.TeamTypeEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created by zhubuqing on 2018/3/30.
 * 团队
 */
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Team extends BaseEntity {
    private String teamName; // 团队名称

    private String teamDeclaration; // 团队宣言

    private String teamBulletin; // 团队公告

    @Enumerated(EnumType.STRING)
    private TeamOpennessTypeEnum opennessType; // 公开程度

    private long userId; // 创建人ID

    @Enumerated(EnumType.STRING)
    private TeamTypeEnum type; // 类型
}
