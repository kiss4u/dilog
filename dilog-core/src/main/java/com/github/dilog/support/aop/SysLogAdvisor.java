package com.github.dilog.support.aop;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * @author my
 * @version v1.0
 * @create 2023-02-16 下午11:54
 */
public class SysLogAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    private final SysLogPointcut sysLogPointcut = new SysLogPointcut();

    @Override
    public Pointcut getPointcut() {
        return sysLogPointcut;
    }

    public void setSysLogMatch(SysLogMatch sysLogMatch) {
        sysLogPointcut.setSysLogMatch(sysLogMatch);
    }

}
