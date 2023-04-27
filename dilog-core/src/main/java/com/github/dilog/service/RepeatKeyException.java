package com.github.dilog.service;

/**
 * @author my
 * @version v1.0
 * @create 2023-02-16 下午11:54
 */
public class RepeatKeyException extends LogException {

    public RepeatKeyException(String message) {
        super(message);
    }

    public RepeatKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepeatKeyException(Throwable cause) {
        super(cause);
    }
}
