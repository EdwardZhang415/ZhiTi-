package com.upic.vo;

import com.upic.enums.TeamOpennessTypeEnum;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class NewTeamVo {
    private String teamName;//团队名称

    private String teamDeclaration;//团队宣言

    @Enumerated(EnumType.STRING)
    private TeamOpennessTypeEnum opennessType; // 公开程度
}
