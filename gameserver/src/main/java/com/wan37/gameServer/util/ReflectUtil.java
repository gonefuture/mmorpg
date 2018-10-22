package com.wan37.gameServer.util;

import java.lang.reflect.Field;
import java.sql.Timestamp;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/22 14:26
 * @version 1.00
 * Description: mmorpg
 */
public class ReflectUtil {


    /**
     * 将obj对象的field属性设置值为value
     *
     * @param obj   对象
     * @param field 属性
     * @param value 值
     * @throws IllegalArgumentException 非法参数异常
     * @throws IllegalAccessException   安全权限异常,一般来说,是由于java在反射时调用了private方法所导致
     */
    private static void setField(Object obj, Field field, String value) throws IllegalArgumentException, IllegalAccessException {
        field.setAccessible(true);
        if (field.getType() == int.class) {
            field.setInt(obj, Integer.parseInt(value));
        } else if (field.getType() == short.class) {
            field.setShort(obj, Short.parseShort(value));
        } else if (field.getType() == byte.class) {
            field.setByte(obj, Byte.parseByte(value));
        } else if (field.getType() == long.class) {
            field.setLong(obj, Long.parseLong(value));
        } else if (field.getType() == double.class) {
            field.setDouble(obj, Double.parseDouble(value));
        } else if (field.getType() == float.class) {
            field.setFloat(obj, Float.parseFloat(value));
        } else if (field.getType() == Timestamp.class) {
            field.set(obj, Timestamp.valueOf(value));
        } else {
            field.set(obj, field.getType().cast(value));
        }

    }
}
