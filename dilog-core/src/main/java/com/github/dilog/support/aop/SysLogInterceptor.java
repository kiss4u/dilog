package com.github.dilog.support.aop;

import com.github.dilog.bean.MethodResult;
import com.github.dilog.bean.SysLogTemplate;
import com.github.dilog.service.consts.CommonConsts;
import com.github.dilog.service.evaluator.ExpressionEvaluator;
import com.github.dilog.util.MathUtils;
import com.github.dilog.util.Performance;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author my
 * @version v1.0
 * @create 2023-02-17 上午12:49
 */
@Slf4j
public class SysLogInterceptor implements MethodInterceptor, Serializable, SmartInitializingSingleton {

    private SysLogMatch sysLogMatch;

    public void setSysLogMatch(SysLogMatch sysLogMatch) {
        this.sysLogMatch = sysLogMatch;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (AopUtils.isAopProxy(invocation)) {
            return invocation.proceed();
        }
        Performance sw = new Performance(invocation.getMethod().getName());
        Object result = null;
        SysLogTemplate logTemplate = null;
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(invocation);
        MethodResult methodResult = new MethodResult(invocation.getMethod(), invocation.getArguments(), targetClass);

        try {
            sw.start("before");
            logTemplate = sysLogMatch.getLogTemplate(invocation.getMethod(), targetClass);
        } catch (Exception e) {
            log.error("SysLog info before parse exception", e);
        } finally {
            sw.stop();
        }
        try {
            sw.start("execute");
            result = invocation.proceed();
            methodResult.setSuccess(true);
            methodResult.setResult(result);
        } catch (Exception e) {
            methodResult.setSuccess(false);
            methodResult.setThrowable(e);
            methodResult.setErrorMsg(e.getMessage());
        } finally {
            sw.stop();
        }

        try {
            sw.start("after");
            after(methodResult, logTemplate);
        } catch (Exception e) {
            throw e;
        } finally {
            sw.stop();
        }

        if (!Objects.isNull(logTemplate) && logTemplate.getPerformance()) {
            log.info("{}", sw);
        }

        return result;
    }

    private void after(MethodResult methodResult, SysLogTemplate logTemplate) throws Throwable {
        // 入参
        List<Object> params = new ArrayList<>();
        if (StringUtils.isEmpty(logTemplate.getParam())) {
            params = Arrays.asList(methodResult.getArgs());
        } else {
            String[] targets = logTemplate.getParam().split("\\+");
            for (int i = 0; i < targets.length; i++) {
                String target = targets[i].trim();
                ExpressionEvaluator e = new ExpressionEvaluator(target, methodResult.getMethod());
                params.add(target + "=" + e.apply(methodResult));
            }
        }
        // 结果
        int resultSize = logTemplate.getSizeLimit();
        Object result = methodResult.isSuccess() ? methodResult.getResult() : methodResult.getErrorMsg();
        if (methodResult.isSuccess()) {
            Object obj = methodResult.getResult();
            if (obj instanceof List) {
                List collection = (List) methodResult.getResult();
                resultSize = collection.size();
                result = collection.stream().limit(logTemplate.getSizeLimit()).collect(Collectors.toList());
            } else if (obj instanceof Map) {
                TreeMap map = new TreeMap((Map) methodResult.getResult());
                resultSize = map.size();
                result = map.subMap(0, logTemplate.getSizeLimit());
            } else if (obj instanceof Set) {
                Set collection = (Set) methodResult.getResult();
                resultSize = collection.size();
                result = collection.stream().limit(logTemplate.getSizeLimit()).collect(Collectors.toSet());
            }
        }
        //
        String percent = logTemplate.getSizeLimit() < resultSize ? MathUtils.getPercent(logTemplate.getSizeLimit(), resultSize) : "100";
        if (logTemplate.isSuccessOn() || logTemplate.isFailOn()) {
            log.info(CommonConsts.LOG_OUT_DEFAULT, logTemplate.getGroup(), percent, params, result);
        }
        // 失败异常信息
        if (!methodResult.isSuccess()) {
            throw methodResult.getThrowable();
        }
    }


    @Override
    public void afterSingletonsInstantiated() {

    }
}
