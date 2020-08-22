package com.upic.vo;

import com.upic.enums.QuestionBankStatusEnum;
import com.upic.enums.QuestionBankTypeEnum;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class QuestionBankVo {
    private String questionBankName; // 题库名称
    @Enumerated(EnumType.STRING)
    private QuestionBankTypeEnum type; // 题库类型@Enumerated(EnumType.STRING)
    private QuestionBankStatusEnum status; // 状态
}
