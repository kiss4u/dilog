package com.github.dilog.support;

import com.github.dilog.annotation.EnableSysLog;
import com.github.dilog.config.SysLogAutoConfiguration;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;
import org.springframework.context.annotation.AutoProxyRegistrar;
import org.springframework.lang.Nullable;

/**
 * @author my
 * @version v1.0
 * @create 2023-02-16 下午11:54
 */
public class SysLogConfigureSelector extends AdviceModeImportSelector<EnableSysLog> {

    @Override
    @Nullable
    protected String[] selectImports(AdviceMode adviceMode) {
        switch (adviceMode) {
            case PROXY:
                return new String[]{AutoProxyRegistrar.class.getName(), SysLogAutoConfiguration.class.getName()};
            case ASPECTJ:
                return new String[]{SysLogAutoConfiguration.class.getName()};
            default:
                return null;
        }
    }
}
