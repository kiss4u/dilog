package com.github.dilog.config;


import com.github.dilog.annotation.EnableSysLog;
import com.github.dilog.support.aop.SysLogAdvisor;
import com.github.dilog.support.aop.SysLogInterceptor;
import com.github.dilog.support.aop.SysLogMatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.context.annotation.Role;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author my
 * @version v1.0
 * @create 2023-02-16 下午11:54
 */
@Slf4j
@Configuration
public class SysLogAutoConfiguration implements ImportAware {

    private AnnotationAttributes enableSysLog;

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public SysLogMatch sysLogMatch() {
        return new SysLogMatch();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public SysLogInterceptor sysLogInterceptor() {
        SysLogInterceptor interceptor = new SysLogInterceptor();
        interceptor.setSysLogMatch(sysLogMatch());
        return interceptor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public SysLogAdvisor sysLogAdvisor() {
        SysLogAdvisor advisor = new SysLogAdvisor();
        advisor.setSysLogMatch(sysLogMatch());
        advisor.setAdvice(sysLogInterceptor());
        advisor.setOrder(enableSysLog.getNumber("order"));
        return advisor;
    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        this.enableSysLog = AnnotationAttributes.fromMap(
                importMetadata.getAnnotationAttributes(EnableSysLog.class.getName(), false));
    }
}
