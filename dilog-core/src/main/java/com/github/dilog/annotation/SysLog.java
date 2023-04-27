package com.github.dilog.annotation;

import java.lang.annotation.*;

/**
 * @author my
 * @version v1.0
 * @create 2023-02-16 下午11:54
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface SysLog {

    /**
     * 名称
     *
     * @return
     */
    String logKey() default "";

    /**
     * 参数
     *
     * @return
     */
    String param() default "";

    /**
     * 成功日志是否输出
     *
     * @return
     */
    boolean successOn() default true;

    /**
     * 成功日志格式
     *
     * @return
     */
    String success() default "";

    /**
     * 结果数量
     *
     * @return
     */
    int sizeLimit() default 20;

    /**
     * 失败日志是否输出
     *
     * @return
     */
    boolean failOn() default true;

    /**
     * 失败日志格式
     *
     * @return
     */
    String fail() default "";

    /**
     * 分组
     *
     * @return
     */
    String group() default "default";

    /**
     * 操作人
     *
     * @return
     */
    String operator() default "";

    /**
     * 性能指标
     *
     * @return
     */
    boolean performance() default false;

}
