package com.github.dilog.util;

import org.slf4j.Logger;

/**
 * @author my
 * @version v1.0
 * @create 2023-02-17 上午10:54
 */
public class SysLogUtils {

    public static void info(Logger log, String template, Object... objects) {
        log.info(template, objects);
    }

    public static void format(Logger log, String template, Object... objects) {
        log.info(template, objects);
    }

}
