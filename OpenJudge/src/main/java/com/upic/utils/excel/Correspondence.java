package com.upic.utils.excel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhubuqing on 2018/2/5.
 */
public class Correspondence {
    private static Map<String, String> map = new HashMap<>();

    static {
        map.put("orderNum", "订单编号");
        map.put("theServiceName", "业务名");
        map.put("username", "用户姓名");
        map.put("userPhone", "用户联系方式");
        map.put("salesManNum", "业务员编号");
        map.put("salesManName", "业务员姓名");
        map.put("salesManPhone", "业务员联系方式");
        map.put("price", "订单价格");
        map.put("idCard", "用户身份证号");
    }

    public static String getTranslationWords(String englishWord) {
        return map.get(englishWord);
    }
}
