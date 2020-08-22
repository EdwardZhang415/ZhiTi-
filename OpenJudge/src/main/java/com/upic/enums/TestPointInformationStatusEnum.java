package com.upic.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhubuqing on 2018/3/29.
 */
public enum TestPointInformationStatusEnum {
    OPEN("开启", 1), CLOSE("关闭", 2);

    private String content;

    private int num;

    TestPointInformationStatusEnum() {
    }

    TestPointInformationStatusEnum(String content, int num) {
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

    public static TestPointInformationStatusEnum getEnum(int value) {
        TestPointInformationStatusEnum testPointInformationStatusEnum = null;
        TestPointInformationStatusEnum[] testPointInformationStatusEnums = TestPointInformationStatusEnum.values();
        for (int i = 0; i < testPointInformationStatusEnums.length; i++) {
            if (testPointInformationStatusEnums[i].getNum() == value) {
                testPointInformationStatusEnum = testPointInformationStatusEnums[i];
                break;
            }
        }
        return testPointInformationStatusEnum;
    }

    public static Map<String, Map<String, Object>> toMap() {
        TestPointInformationStatusEnum[] testPointInformationStatusEnums = TestPointInformationStatusEnum.values();
        Map<String, Map<String, Object>> stringMapHashMap = new HashMap<String, Map<String, Object>>();
        for (int num = 0; num < testPointInformationStatusEnums.length; num++) {
            Map<String, Object> map = new HashMap<String, Object>();
            String key = String.valueOf(getEnum(testPointInformationStatusEnums[num].getNum()));
            map.put("num", String.valueOf(testPointInformationStatusEnums[num].getNum()));
            map.put("content", testPointInformationStatusEnums[num].getContent());
            stringMapHashMap.put(key, map);
        }
        return stringMapHashMap;
    }
}
