package com.github.dilog.util;

import java.text.NumberFormat;

/**
 * @author jimu
 * @version v1.0
 * @create 2023-04-28 上午1:38
 */
public class MathUtils {

    public static String getPercent(int x, int y) {
        double d1 = x * 1.0;
        double d2 = y * 1.0;
        NumberFormat percentInstance = NumberFormat.getPercentInstance();
        percentInstance.setMinimumFractionDigits(2);
        return percentInstance.format(d1 / d2);
    }

}
