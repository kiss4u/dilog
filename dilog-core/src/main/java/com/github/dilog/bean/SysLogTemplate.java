package com.github.dilog.bean;

import lombok.Builder;
import lombok.Data;

/**
 * @author my
 * @version v1.0
 * @create 2023-02-17 上午12:17
 */
@Data
@Builder
public class SysLogTemplate {

    private String logKey;

    private String param;

    private boolean successOn;

    private String successMessage;

    private int sizeLimit;

    private boolean failOn;

    private String failMessage;

    private String group;

    private String operator;

    private Boolean performance;

}
