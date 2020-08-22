package com.upic.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhubuqing on 2018/3/29.
 */
public enum QuestionBankStatusEnum {
    OPEN("开启", 1), CLOSE("关闭", 2);

    private String content;

    private int num;

    QuestionBankStatusEnum() {
    }

    QuestionBankStatusEnum(String content, int num) {
        this.content = content;
        this.num = num;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public static QuestionBankStatusEnum getEnum(int value) {
        QuestionBankStatusEnum questionBankStatusEnum = null;
        QuestionBankStatusEnum[] questionBankStatusEnums = QuestionBankStatusEnum.values();
        for (int i = 0; i < questionBankStatusEnums.length; i++) {
            if (questionBankStatusEnums[i].getNum() == value) {
                questionBankStatusEnum = questionBankStatusEnums[i];
                break;
            }
        }
        return questionBankStatusEnum;
    }

    public static Map<String, Map<String, Object>> toMap() {
        QuestionBankStatusEnum[] questionBankStatusEnums = QuestionBankStatusEnum.values();
        Map<String, Map<String, Object>> stringMapHashMap = new HashMap<String, Map<String, Object>>();
        for (int num = 0; num < questionBankStatusEnums.length; num++) {
            Map<String, Object> map = new HashMap<String, Object>();
            String key = String.valueOf(getEnum(questionBankStatusEnums[num].getNum()));
            map.put("num", String.valueOf(questionBankStatusEnums[num].getNum()));
            map.put("content", questionBankStatusEnums[num].getContent());
            stringMapHashMap.put(key, map);
        }
        return stringMapHashMap;
    }
}
