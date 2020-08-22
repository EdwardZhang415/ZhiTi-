package com.upic.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhubuqing on 2018/3/29.
 */
public enum LanguageTypeEnum {
    PYTHON("Python", 1),
    JAVA("Java", 2),
    C("C", 3),
    CAA("C++", 4),
    CS("C#", 5),
    VISUAL_BASIC("VisualBasic", 6),
    JAVA_SCRIPT("JavaScript", 7),
    OBJECTIVE_C("ObjectiveC", 7);

    private String content;

    private int num;

    LanguageTypeEnum() {
    }

    LanguageTypeEnum(String content, int num) {
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

    public static LanguageTypeEnum getEnum(int value) {
        LanguageTypeEnum languageTypeEnum = null;
        LanguageTypeEnum[] languageTypeEnums = LanguageTypeEnum.values();
        for (int i = 0; i < languageTypeEnums.length; i++) {
            if (languageTypeEnums[i].getNum() == value) {
                languageTypeEnum = languageTypeEnums[i];
                break;
            }
        }
        return languageTypeEnum;
    }

    public static Map<String, Map<String, Object>> toMap() {
        LanguageTypeEnum[] languageTypeEnums = LanguageTypeEnum.values();
        Map<String, Map<String, Object>> stringMapHashMap = new HashMap<String, Map<String, Object>>();
        for (int num = 0; num < languageTypeEnums.length; num++) {
            Map<String, Object> map = new HashMap<String, Object>();
            String key = String.valueOf(getEnum(languageTypeEnums[num].getNum()));
            map.put("num", String.valueOf(languageTypeEnums[num].getNum()));
            map.put("content", languageTypeEnums[num].getContent());
            stringMapHashMap.put(key, map);
        }
        return stringMapHashMap;
    }
}
