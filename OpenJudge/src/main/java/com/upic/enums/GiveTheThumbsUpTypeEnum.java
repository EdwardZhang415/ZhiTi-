package com.upic.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhubuqing on 2018/3/30.
 */
public enum GiveTheThumbsUpTypeEnum {
    UP("赞", 1), DOWN("踩", 2);

    private String content;

    private int num;

    GiveTheThumbsUpTypeEnum() {
    }

    GiveTheThumbsUpTypeEnum(String content, int num) {
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

    public static GiveTheThumbsUpTypeEnum getEnum(int value) {
        GiveTheThumbsUpTypeEnum giveTheThumbsUpTypeEnum = null;
        GiveTheThumbsUpTypeEnum[] giveTheThumbsUpTypeEnums = GiveTheThumbsUpTypeEnum.values();
        for (int i = 0; i < giveTheThumbsUpTypeEnums.length; i++) {
            if (giveTheThumbsUpTypeEnums[i].getNum() == value) {
                giveTheThumbsUpTypeEnum = giveTheThumbsUpTypeEnums[i];
                break;
            }
        }
        return giveTheThumbsUpTypeEnum;
    }

    public static Map<String, Map<String, Object>> toMap() {
        GiveTheThumbsUpTypeEnum[] giveTheThumbsUpTypeEnums = GiveTheThumbsUpTypeEnum.values();
        Map<String, Map<String, Object>> stringMapHashMap = new HashMap<String, Map<String, Object>>();
        for (int num = 0; num < giveTheThumbsUpTypeEnums.length; num++) {
            Map<String, Object> map = new HashMap<String, Object>();
            String key = String.valueOf(getEnum(giveTheThumbsUpTypeEnums[num].getNum()));
            map.put("num", String.valueOf(giveTheThumbsUpTypeEnums[num].getNum()));
            map.put("content", giveTheThumbsUpTypeEnums[num].getContent());
            stringMapHashMap.put(key, map);
        }
        return stringMapHashMap;
    }
}
