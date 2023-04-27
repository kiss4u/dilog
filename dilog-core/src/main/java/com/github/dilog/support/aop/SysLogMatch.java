package com.github.dilog.support.aop;

import com.github.dilog.bean.SysLogTemplate;
import com.github.dilog.annotation.SysLog;
import com.github.dilog.util.FieldUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author my
 * @version v1.0
 * @create 2023-02-17 上午12:01
 */
@Slf4j
public class SysLogMatch {

    private static volatile ConcurrentHashMap<String, SysLogTemplate> allTemplateMaps = new ConcurrentHashMap<>();

    public boolean checkSysLog(Method method, Class<?> targetClass) {
        if (!Modifier.isPublic(method.getModifiers())) {
            return false;
        }
        SysLog sysLog = AnnotationUtils.findAnnotation(method, SysLog.class);
        if (null != sysLog) {
            getSysLogTemplate(method, sysLog, true);
            log.debug("SysLog info checkSysLog method: {}", method.getName());
            return true;
        }
        return false;
    }

    /**
     * 获取配置模板
     *
     * @param method
     * @param targetClass
     * @return
     */
    public SysLogTemplate getLogTemplate(Method method, Class<?> targetClass) {
        if (!Modifier.isPublic(method.getModifiers())) {
            return null;
        }
        //
        AnnotatedElement annotatedElement = BridgeMethodResolver.findBridgedMethod(ClassUtils.getMostSpecificMethod(method, targetClass));
        SysLog sysLog = AnnotatedElementUtils.findMergedAnnotation(annotatedElement, SysLog.class);
        return getSysLogTemplate(method, sysLog, false);
    }

    /**
     * 获取方法上的注解配置
     *
     * @param method
     * @param sysLog
     * @return
     */
    private SysLogTemplate getSysLogTemplate(Method method, SysLog sysLog, boolean check) {
        String logKey = StringUtils.isEmpty(sysLog.logKey()) ? method.getName() : sysLog.logKey();
        SysLogTemplate logTemplate;
        if (allTemplateMaps.containsKey(logKey)) {
            logTemplate = allTemplateMaps.get(logKey);
            log.debug("SysLog info template key: {}, exist: {}", logKey, logTemplate);
            return logTemplate;
        }

        logTemplate = SysLogTemplate.builder()
                .logKey(logKey)
                .param(sysLog.param())
                .successOn(sysLog.successOn())
                .sizeLimit(sysLog.sizeLimit())
                .successMessage(sysLog.success())
                .failMessage(sysLog.fail())
                .group(sysLog.group())
                .operator(sysLog.operator())
                .performance(sysLog.performance())
                .build();
        //
        formatTemplate(logTemplate);
        //
        validateSysLog(logTemplate, check);
        //
        allTemplateMaps.putIfAbsent(logKey, logTemplate);
        log.debug("SysLog info template key: {}, new: {}", logKey, logTemplate);
        return logTemplate;
    }

    /**
     * @param obj
     */
    private void formatTemplate(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            String key = FieldUtils.getFiled(field);
            Object value = FieldUtils.getFieldValue(obj, key);
            log.debug("SysLog info template field: {}, value: {}", key, value);

        }
    }


    /**
     * 校验参数配置
     *
     * @param logTemplate
     * @param check
     */
    private void validateSysLog(SysLogTemplate logTemplate, boolean check) {
        if (check) {
            log.debug("SysLog info validate: {}", logTemplate);

        }
    }

}
