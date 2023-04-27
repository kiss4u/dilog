package com.github.dilog.util;

import com.github.dilog.service.evaluator.MvelEvaluator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MvelEvaluatorTest {
    @Test
    public void test() {
        MvelEvaluator e = new MvelEvaluator("bean('a')");
        assertEquals("a_bean", e.apply(new RootObject()));
    }


    public static class RootObject {
        public String bean(String name) {
            return name + "_bean";
        }
    }


}


