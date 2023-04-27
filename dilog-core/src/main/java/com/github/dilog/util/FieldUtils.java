package com.github.dilog.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author my
 * @version v1.0
 * @create 2023-02-16 下午11:54
 */
@Slf4j
public class FieldUtils {

    public static String getFiled(Field f) {
        String str = f.toString().substring(f.toString().lastIndexOf('.') + 1);
        return str;
    }

    public static Object getFieldValue(Object owner, String fieldName) {
        Class ownerClass = owner.getClass();
        Method method = null;
        try {
            // fieldName -> FieldName
//            String methodName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            method = ownerClass.getMethod("get" + getMethodName(fieldName));
        } catch (Exception e) {
            return null;
        }
        // invoke getMethod
        try {
            return method.invoke(owner);
        } catch (Exception e) {
            return null;
        }
    }

    public static void getObjectValue(Object object) throws Exception {
        if (object != null) {
            // 拿到该类
            Class<?> clz = object.getClass();
            // 获取实体类的所有属性，返回Field数组
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                // 属性类型
                log.debug("field type:{}", field.getGenericType());
                // 如果type是类类型，则前面包含"class "，后面跟类名
                // 如果类型是String
                if (field.getGenericType().toString().equals("class java.lang.String")) {
                    // 拿到该属性的getter方法
                    /**
                     * 根据拼凑的字符来找getter方法
                     * 在Boolean值的时候是isXXX（默认使用ide生成getter的都是isXXX）
                     * 如果出现NoSuchMethod异常 就说明它找不到那个getter方法 需要做个规范
                     */
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(field.getName()));
                    // 调用getter方法获取属性值
                    String val = (String) m.invoke(object);
                    if (val != null) {
                        log.info("String type:" + val);
                    }
                }

                // 如果类型是Integer
                if (field.getGenericType().toString().equals("class java.lang.Integer")) {
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(field.getName()));
                    Integer val = (Integer) m.invoke(object);
                    if (val != null) {
                        log.info("Integer type:" + val);
                    }

                }

                // 如果类型是Double
                if (field.getGenericType().toString().equals(
                        "class java.lang.Double")) {
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(field.getName()));
                    Double val = (Double) m.invoke(object);
                    if (val != null) {
                        log.info("Double type:" + val);
                    }

                }

                // 如果类型是Boolean 是封装类
                if (field.getGenericType().toString().equals(
                        "class java.lang.Boolean")) {
                    Method m = (Method) object.getClass().getMethod(
                            field.getName());
                    Boolean val = (Boolean) m.invoke(object);
                    if (val != null) {
                        log.info("Boolean type:" + val);
                    }

                }

                // 如果类型是boolean 基本数据类型不一样 这里有点说名如果定义名是 isXXX的 那就全都是isXXX的
                // 反射找不到getter的具体名
                if (field.getGenericType().toString().equals("boolean")) {
                    Method m = (Method) object.getClass().getMethod(
                            field.getName());
                    Boolean val = (Boolean) m.invoke(object);
                    if (val != null) {
                        log.info("boolean type:" + val);
                    }

                }
                // 如果类型是Date
                if (field.getGenericType().toString().equals(
                        "class java.util.Date")) {
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(field.getName()));
                    Date val = (Date) m.invoke(object);
                    if (val != null) {
                        log.info("Date type:" + val);
                    }

                }
                // 如果类型是Short
                if (field.getGenericType().toString().equals(
                        "class java.lang.Short")) {
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(field.getName()));
                    Short val = (Short) m.invoke(object);
                    if (val != null) {
                        log.info("Short type:" + val);
                    }

                }
                // 其他
            }
        }
    }

    /**
     * 首字母大写
     *
     * @param filedName
     * @return
     * @throws Exception
     */
    private static String getMethodName(String filedName) {
        byte[] items = filedName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

}