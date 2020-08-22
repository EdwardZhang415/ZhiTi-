package com.upic.vo;

import com.upic.enums.TeamMembersTypeEnum;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class TeamMemberVo {
    //@Enumerated(EnumType.STRING)
    //private TeamMembersTypeEnum type; // 成员类型

    private String name; // 成员名称
}
