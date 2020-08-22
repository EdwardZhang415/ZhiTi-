package com.upic.vo;

import lombok.Data;
import java.util.List;

/**
 * Created by song on 2018/5/3.
 */

    @Data
    public class QuestionVo  {
    private String questionName; // 名字

    private String background; // 背景

    private String describtion; // 描述

    private String input; // 输入格式

    private String output; // 输出格式


    private int publicTypeNum; // 公开类型  0,1,2

    private long memoryLimit;//内存限制

    private long timeLimit;//时间限制

    private String hintsAndInstructions;// 提示与说明

    private long questionBankId; // 题库ID

    private String degreeOfDifficulty; // 难度系数

    private List<TheExampleParam> TheExamples;//样例输入输出

    private List<Long> labels;//标签

    //private String num; // 编号    ?????

    // private String hintsAndInstructions; // 提示与说明   ?????????????

    // private String fileAddress; // 文件地址     ?????

    //private String patternSelection; // 模式选择（显示分数） ??????????????

    //private String subtask; // 得分策略    ????????

    // @Enumerated(EnumType.STRING)
    //private QuestionStatusEnum status; // 题目状态   ????????



    // private String standardProcess; // 标程   ??????????

    //private String degreeOfDifficulty; // 难度系数   ???????
    }

