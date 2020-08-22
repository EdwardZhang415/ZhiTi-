package com.upic.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhubuqing on 2018/3/29.
 */
public enum TheSubmitStatusEnum {
    ACCEPTED("通过", 1), WRONG("警告", 2), ERROR("错误", 3), PENDING("等待",4);

    private String content;

    private int num;

    TheSubmitStatusEnum() {
    }

    TheSubmitStatusEnum(String content, int num) {
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

    public static TheSubmitStatusEnum getEnum(int value) {
        TheSubmitStatusEnum theSubmitStatusEnum = null;
        TheSubmitStatusEnum[] theSubmitStatusEnums = TheSubmitStatusEnum.values();
        for (int i = 0; i < theSubmitStatusEnums.length; i++) {
            if (theSubmitStatusEnums[i].getNum() == value) {
                theSubmitStatusEnum = theSubmitStatusEnums[i];
                break;
            }
        }
        return theSubmitStatusEnum;
    }

    public static Map<String, Map<String, Object>> toMap() {
        TheSubmitStatusEnum[] theSubmitStatusEnums = TheSubmitStatusEnum.values();
        Map<String, Map<String, Object>> stringMapHashMap = new HashMap<String, Map<String, Object>>();
        for (int num = 0; num < theSubmitStatusEnums.length; num++) {
            Map<String, Object> map = new HashMap<String, Object>();
            String key = String.valueOf(getEnum(theSubmitStatusEnums[num].getNum()));
            map.put("num", String.valueOf(theSubmitStatusEnums[num].getNum()));
            map.put("content", theSubmitStatusEnums[num].getContent());
            stringMapHashMap.put(key, map);
        }
        return stringMapHashMap;
    }
}
