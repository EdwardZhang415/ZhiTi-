package com.upic.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhubuqing on 2018/3/30.
 */
public enum TeamOpennessTypeEnum {
    PRIVATE("私有", 1), SEMI_PUBLIC("半公开", 2), PUBLIC("公开", 3);

    private String content;

    private int num;

    TeamOpennessTypeEnum() {
    }

    TeamOpennessTypeEnum(String content, int num) {
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

    public static TeamOpennessTypeEnum getEnum(int value) {
        TeamOpennessTypeEnum teamOpennessTypeEnum = null;
        TeamOpennessTypeEnum[] teamOpennessTypeEnums = TeamOpennessTypeEnum.values();
        for (int i = 0; i < teamOpennessTypeEnums.length; i++) {
            if (teamOpennessTypeEnums[i].getNum() == value) {
                teamOpennessTypeEnum = teamOpennessTypeEnums[i];
                break;
            }
        }
        return teamOpennessTypeEnum;
    }

    public static Map<String, Map<String, Object>> toMap() {
        TeamOpennessTypeEnum[] teamOpennessTypeEnums = TeamOpennessTypeEnum.values();
        Map<String, Map<String, Object>> stringMapHashMap = new HashMap<String, Map<String, Object>>();
        for (int num = 0; num < teamOpennessTypeEnums.length; num++) {
            Map<String, Object> map = new HashMap<String, Object>();
            String key = String.valueOf(getEnum(teamOpennessTypeEnums[num].getNum()));
            map.put("num", String.valueOf(teamOpennessTypeEnums[num].getNum()));
            map.put("content", teamOpennessTypeEnums[num].getContent());
            stringMapHashMap.put(key, map);
        }
        return stringMapHashMap;
    }
}
