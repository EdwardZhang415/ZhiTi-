package com.upic.vo;

import com.upic.enums.TeamOpennessTypeEnum;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class TeamVo {
    private String teamDeclaration; // 团队宣言
    private String teamBulletin; // 团队公告
    @Enumerated(EnumType.STRING)
    private TeamOpennessTypeEnum opennessType; // 公开程度
}
