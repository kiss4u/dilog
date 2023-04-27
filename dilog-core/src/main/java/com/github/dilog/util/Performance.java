package com.github.dilog.util;

import org.springframework.util.StopWatch;

/**
 * @author my
 * @version v1.0
 * @create 2023-02-17 上午12:17
 */
public class Performance extends StopWatch {

    public Performance(String id) {
        super(id);
    }

    @Override
    public String shortSummary() {
        return "SysLog info performance method [" + this.getId() + "], running time = " + this.getTotalTimeMillis() + "ms";
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(this.shortSummary());
        TaskInfo[] var2 = this.getTaskInfo();
        int var3 = var2.length;
        for (int var4 = 0; var4 < var3; ++var4) {
            TaskInfo task = var2[var4];
            sb.append("; [").append(task.getTaskName()).append("] took ").append(task.getTimeMillis()).append("ms");
            long percent = Math.round(100.0D * (double) task.getTimeMillis() / (double) this.getTotalTimeMillis());
            sb.append(" = ").append(percent).append("%");
        }

        return sb.toString();
    }

}
