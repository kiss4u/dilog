package com.github.dilog.service.evaluator;

import com.github.dilog.bean.MethodResult;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * @author my
 * @version v1.0
 * @create 2023-03-10 上午11:15
 */
public class SpelEvaluator implements Function {

    private static ExpressionParser parser;
    private static ParameterNameDiscoverer parameterNameDiscoverer;

    static {
        parser = new SpelExpressionParser();
        parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
    }

    private final Expression expression;
    private String[] parameterNames;

    public SpelEvaluator(String target, Method defineMethod) {
        expression = parser.parseExpression(target);
        if (defineMethod.getParameterCount() > 0) {
            parameterNames = parameterNameDiscoverer.getParameterNames(defineMethod);
        }
    }

    @Override
    public Object apply(Object o) {
        EvaluationContext context = new StandardEvaluationContext(o);
        MethodResult methodResult = (MethodResult) o;
        if (parameterNames != null) {
            for (int i = 0; i < parameterNames.length; i++) {
                context.setVariable(parameterNames[i], methodResult.getArgs()[i]);
            }
        }
        try {
            return expression.getValue(context);
        } catch (Exception e) {
            return "未匹配结果";
        }
    }
}
