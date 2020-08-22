package com.upic.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhubuqing on 2018/3/29.
 */
public enum QuestionBankTypeEnum {
    PRIVATE("私人", 1), TEAM("团队", 2), PUBLIC("公共", 3);

    private String content;

    private int num;

    QuestionBankTypeEnum() {
    }

    QuestionBankTypeEnum(String content, int num) {
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

    public static QuestionBankTypeEnum getEnum(int value) {
        QuestionBankTypeEnum questionBankTypeEnum = null;
        QuestionBankTypeEnum[] questionBankTypeEnums = QuestionBankTypeEnum.values();
        for (int i = 0; i < questionBankTypeEnums.length; i++) {
            if (questionBankTypeEnums[i].getNum() == value) {
                questionBankTypeEnum = questionBankTypeEnums[i];
                break;
            }
        }
        return questionBankTypeEnum;
    }

    public static Map<String, Map<String, Object>> toMap() {
        QuestionBankTypeEnum[] questionBankTypeEnums = QuestionBankTypeEnum.values();
        Map<String, Map<String, Object>> stringMapHashMap = new HashMap<String, Map<String, Object>>();
        for (int num = 0; num < questionBankTypeEnums.length; num++) {
            Map<String, Object> map = new HashMap<String, Object>();
            String key = String.valueOf(getEnum(questionBankTypeEnums[num].getNum()));
            map.put("num", String.valueOf(questionBankTypeEnums[num].getNum()));
            map.put("content", questionBankTypeEnums[num].getContent());
            stringMapHashMap.put(key, map);
        }
        return stringMapHashMap;
    }
}
