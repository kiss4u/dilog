package com.github.dilog.support.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author my
 * @version v1.0
 * @create 2023-02-16 下午11:54
 */
@Slf4j
public class SysLogPointcut extends StaticMethodMatcherPointcut implements Serializable {

    private SysLogMatch sysLogMatch;

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return sysLogMatch.checkSysLog(method, targetClass);
    }

    public void setSysLogMatch(SysLogMatch sysLogMatch) {
        this.sysLogMatch = sysLogMatch;
    }

}
