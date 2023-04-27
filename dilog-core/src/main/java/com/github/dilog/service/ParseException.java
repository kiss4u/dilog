package com.github.dilog.service;

/**
 * @author my
 * @version v1.0
 * @create 2023-02-16 下午11:54
 */
public class ParseException extends LogException {

    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseException(Throwable cause) {
        super(cause);
    }
}
