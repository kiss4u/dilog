package com.github.dilog.bean;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author my
 * @version v1.0
 * @create 2023-02-17 上午12:17
 */
@Data
public class MethodResult {

    /**
     * 方法
     */
    private Method method;
    /**
     * 参数
     */
    private Object[] args;
    /**
     * class
     */
    private Class<?> targetClass;

    /**
     * 是否执行成功
     */
    private boolean success;
    /**
     * 返回值
     */
    private Object result;
    /**
     * 异常
     */
    private Throwable throwable;
    /**
     * 异常信息
     */
    private String errorMsg;

    public MethodResult() {

    }

    public MethodResult(Method method, Object[] args, Class<?> targetClass) {
        this.method = method;
        this.args = args;
        this.targetClass = targetClass;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArgs() {
        return args;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
