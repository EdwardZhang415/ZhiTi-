package com.upic.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhubuqing on 2018/3/30.
 */
public enum TeamMembersTypeEnum {
    ORDINARY("普通成员", 1), ADMINISTRATOR("管理员", 2), CREATOR("创建者", 3);

    private String content;

    private int num;

    TeamMembersTypeEnum() {
    }

    TeamMembersTypeEnum(String content, int num) {
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

    public static QuestionStatusEnum getEnum(int value) {
        QuestionStatusEnum questionStatusEnum = null;
        QuestionStatusEnum[] questionStatusEnums = QuestionStatusEnum.values();
        for (int i = 0; i < questionStatusEnums.length; i++) {
            if (questionStatusEnums[i].getNum() == value) {
                questionStatusEnum = questionStatusEnums[i];
                break;
            }
        }
        return questionStatusEnum;
    }

    public static Map<String, Map<String, Object>> toMap() {
        QuestionStatusEnum[] questionStatusEnums = QuestionStatusEnum.values();
        Map<String, Map<String, Object>> stringMapHashMap = new HashMap<String, Map<String, Object>>();
        for (int num = 0; num < questionStatusEnums.length; num++) {
            Map<String, Object> map = new HashMap<String, Object>();
            String key = String.valueOf(getEnum(questionStatusEnums[num].getNum()));
            map.put("num", String.valueOf(questionStatusEnums[num].getNum()));
            map.put("content", questionStatusEnums[num].getContent());
            stringMapHashMap.put(key, map);
        }
        return stringMapHashMap;
    }
}
