package com.upic.utils.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * 只针对标准实体类
 *
 * @author dtz
 */
public class UpicBeanUtils {

    public static final String DEFAULT_METHOD_NAME = "set";


    public static Map<String, Method> getMethodToMap(Class<?> clazz) {
        return getMethodToMap(clazz, DEFAULT_METHOD_NAME);
    }

    public static Map<String, Method> getMethodToMap(Class<?> clazz, String startMethodName) {
        return getMethodToMaps(clazz, startMethodName);
    }

    protected static Map<String, Method> getMethodToMaps(Class<?> clazz, String startMethodName) {
        Map<String, Method> methodMap = new HashMap<String, Method>();
        try {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            // Stream.of(declaredMethods).filter(x->x.getName().startsWith(methodName)).t
            for (Method m : declaredMethods) {
                if (!m.getName().startsWith(startMethodName)) {
                    continue;
                }
                methodMap.put(getFieldName(m.getName(), startMethodName), m);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return methodMap;
    }

    /**
     * 获取所有属性
     *
     * @param c
     * @return
     */
    protected static Field[] getAllFields(Class<?> c) {
        return c.getDeclaredFields();
    }

    /**
     * 获取所有参数
     *
     * @param c
     * @return
     */
    public static Map<String, Field> getFieldToMap(Class<?> c) {
        Field[] allFields = getAllFields(c);
        Map<String, Field> map = new HashMap<String, Field>();
        Arrays.stream(allFields).forEach(f -> {
            map.put(f.getName(), f);
        });
        return map;
    }

    public static Object setValue(Class<?> sourceobj, Object targetObj, Method m) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return m.invoke(sourceobj, targetObj);
    }

    /**
     * 分解方法获取属性
     *
     * @param methodName
     * @param startMethodName
     * @return
     */
    private static String getFieldName(String methodName, String startMethodName) throws Exception {
        StringBuffer sb = new StringBuffer();
        String[] split = methodName.split(startMethodName);
        String field = split[1];
        if (field == null) {
            throw new Exception("字符串为空");
        }
        for (int i = 0; i < field.length(); i++) {
            char c = field.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(Character.toLowerCase(c));
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
