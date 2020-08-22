package com.upic.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhubuqing on 2018/3/29.
 */
public enum CompileStatusEnum {
    ACCEPTED("通过", 1), WRONG("警告", 2), ERROR("错误", 3);

    private String content;

    private int num;

    CompileStatusEnum() {
    }

    CompileStatusEnum(String content, int num) {
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

    public static CompileStatusEnum getEnum(int value) {
        CompileStatusEnum compileStatusEnum = null;
        CompileStatusEnum[] compileStatusEnums = CompileStatusEnum.values();
        for (int i = 0; i < compileStatusEnums.length; i++) {
            if (compileStatusEnums[i].getNum() == value) {
                compileStatusEnum = compileStatusEnums[i];
                break;
            }
        }
        return compileStatusEnum;
    }

    public static Map<String, Map<String, Object>> toMap() {
        CompileStatusEnum[] compileStatusEnums = CompileStatusEnum.values();
        Map<String, Map<String, Object>> stringMapHashMap = new HashMap<String, Map<String, Object>>();
        for (int num = 0; num < compileStatusEnums.length; num++) {
            Map<String, Object> map = new HashMap<String, Object>();
            String key = String.valueOf(getEnum(compileStatusEnums[num].getNum()));
            map.put("num", String.valueOf(compileStatusEnums[num].getNum()));
            map.put("content", compileStatusEnums[num].getContent());
            stringMapHashMap.put(key, map);
        }
        return stringMapHashMap;
    }
}
