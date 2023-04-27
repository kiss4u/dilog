package com.github.dilog.util;

import com.github.dilog.service.LogException;
import com.github.dilog.service.evaluator.ExpressionEvaluator;
import com.github.dilog.service.evaluator.MvelEvaluator;
import com.github.dilog.service.evaluator.SpelEvaluator;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExpressionEvaluatorTest {

    public void targetMethod(String p1, int p2) {
    }

    @Test
    public void test() throws Exception {
        Method m = ExpressionEvaluatorTest.class.getMethod("targetMethod", String.class, int.class);

        ExpressionEvaluator e = new ExpressionEvaluator("1+1", m);
        assertTrue(e.getEvaluator() instanceof SpelEvaluator);
        e = new ExpressionEvaluator("spel{1+1}", m);
        assertTrue(e.getEvaluator() instanceof SpelEvaluator);
        e = new ExpressionEvaluator("mvel{1+1}", m);
        assertTrue(e.getEvaluator() instanceof MvelEvaluator);
        assertThrows(LogException.class,() -> new ExpressionEvaluator("xxx{1+1}", m));
    }

}


