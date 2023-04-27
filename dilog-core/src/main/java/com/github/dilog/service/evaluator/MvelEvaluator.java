package com.github.dilog.service.evaluator;

import org.mvel2.MVEL;

import java.util.function.Function;

/**
 * @author my
 * @version v1.0
 * @create 2023-03-10 上午11:14
 */
public class MvelEvaluator implements Function {

    private String target;

    public MvelEvaluator(String target) {
        this.target = target;
    }

    @Override
    public Object apply(Object o) {
        return MVEL.eval(target, o);
    }
}
