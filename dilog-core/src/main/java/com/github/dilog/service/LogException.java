/**
 * Created on  13-09-09 18:58
 */
package com.github.dilog.service;

/**
 * @author my
 * @version v1.0
 * @create 2023-03-10 上午11:14
 */
public class LogException extends RuntimeException {

    public LogException(String message) {
        super(message);
    }

    public LogException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogException(Throwable cause) {
        super(cause);
    }
}
