package com.github.dilog.service.evaluator;

import com.github.dilog.service.consts.ELEnum;
import com.github.dilog.service.ParseException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author my
 * @version v1.0
 * @create 2023-03-10 上午11:14
 */
@Slf4j
public class ExpressionEvaluator implements Function {

    private static final Pattern pattern = Pattern.compile("\\s*(\\w+)\\s*\\{(.+)\\}\\s*");
    private Function<Object, Object> evaluator;

    public ExpressionEvaluator(String target, Method defineMethod) {
        Object rt[] = parseEL(target);
        if (rt == null) {
            return;
        }
        ELEnum el = (ELEnum) rt[0];
        String realScript = (String) rt[1];
        if (el == ELEnum.MVEL) {
            evaluator = new MvelEvaluator(realScript);
        } else if (el == ELEnum.SPRING_EL) {
            evaluator = new SpelEvaluator(realScript, defineMethod);
        } else {
            log.warn("ExpressionEvaluator wrong target: {}", target);
        }
    }

    private Object[] parseEL(String target) {
        if (target == null || target.trim().equals("")) {
            return null;
        }
        Object[] rt = new Object[2];
        Matcher matcher = pattern.matcher(target);
        if (!matcher.matches()) {
            rt[0] = ELEnum.SPRING_EL;
            rt[1] = target;
            return rt;
        } else {
            String s = matcher.group(1);
            if ("mvel".equals(s)) {
                rt[0] = ELEnum.MVEL;
            } else if ("spel".equals(s)) {
                rt[0] = ELEnum.SPRING_EL;
            } else {
                throw new ParseException("Evaluator can not parse : " + target);
            }
            rt[1] = matcher.group(2);
            return rt;
        }
    }

    @Override
    public Object apply(Object o) {
        return null != evaluator ? evaluator.apply(o) : null;
    }

    public Function<Object, Object> getEvaluator() {
        return evaluator;
    }
}
