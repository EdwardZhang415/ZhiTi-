package com.upic.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhubuqing on 2018/3/30.
 */
public enum UserThroughStatusEnum {
    HAVE_IN_HAND("进行中", 1), COMPLETED("已完成", 2), NOT_THROUGH("未开始", 3);

    private String content;

    private int num;

    UserThroughStatusEnum() {
    }

    UserThroughStatusEnum(String content, int num) {
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

    public static UserThroughStatusEnum getEnum(int value) {
        UserThroughStatusEnum userThroughStatusEnum = null;
        UserThroughStatusEnum[] userThroughStatusEnums = UserThroughStatusEnum.values();
        for (int i = 0; i < userThroughStatusEnums.length; i++) {
            if (userThroughStatusEnums[i].getNum() == value) {
                userThroughStatusEnum = userThroughStatusEnums[i];
                break;
            }
        }
        return userThroughStatusEnum;
    }

    public static Map<String, Map<String, Object>> toMap() {
        UserThroughStatusEnum[] userThroughStatusEnums = UserThroughStatusEnum.values();
        Map<String, Map<String, Object>> stringMapHashMap = new HashMap<String, Map<String, Object>>();
        for (int num = 0; num < userThroughStatusEnums.length; num++) {
            Map<String, Object> map = new HashMap<String, Object>();
            String key = String.valueOf(getEnum(userThroughStatusEnums[num].getNum()));
            map.put("num", String.valueOf(userThroughStatusEnums[num].getNum()));
            map.put("content", userThroughStatusEnums[num].getContent());
            stringMapHashMap.put(key, map);
        }
        return stringMapHashMap;
    }
}
