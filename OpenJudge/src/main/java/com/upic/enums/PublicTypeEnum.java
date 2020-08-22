package com.upic.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhubuqing on 2018/3/29.
 */
public enum PublicTypeEnum {
    PRIVATE("私有", 1), SEMI_PUBLIC("半公开", 2), PUBLIC("公开", 3);

    private String content;

    private int num;

    PublicTypeEnum() {
    }

    PublicTypeEnum(String content, int num) {
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

    public static PublicTypeEnum getEnum(int value) {
        PublicTypeEnum publicTypeEnum = null;
        PublicTypeEnum[] publicTypeEnums = PublicTypeEnum.values();
        for (int i = 0; i < publicTypeEnums.length; i++) {
            if (publicTypeEnums[i].getNum() == value) {
                publicTypeEnum = publicTypeEnums[i];
                break;
            }
        }
        return publicTypeEnum;
    }

    public static Map<String, Map<String, Object>> toMap() {
        PublicTypeEnum[] publicTypeEnums = PublicTypeEnum.values();
        Map<String, Map<String, Object>> stringMapHashMap = new HashMap<String, Map<String, Object>>();
        for (int num = 0; num < publicTypeEnums.length; num++) {
            Map<String, Object> map = new HashMap<String, Object>();
            String key = String.valueOf(getEnum(publicTypeEnums[num].getNum()));
            map.put("num", String.valueOf(publicTypeEnums[num].getNum()));
            map.put("content", publicTypeEnums[num].getContent());
            stringMapHashMap.put(key, map);
        }
        return stringMapHashMap;
    }
}
