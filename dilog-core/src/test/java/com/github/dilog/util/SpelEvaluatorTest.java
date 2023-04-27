package com.github.dilog.util;

import com.github.dilog.bean.MethodResult;
import com.github.dilog.service.evaluator.SpelEvaluator;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SpelEvaluatorTest {

    public void targetMethod(String p1, int p2) {
    }

    public void targetMethod2(UserInfo userInfo) {
    }

    @Test
    public void test() throws Exception {
        RootObject root = new RootObject();
        root.setArgs(new Object[]{"123", 456});
        Method m = SpelEvaluatorTest.class.getMethod("targetMethod", String.class, int.class);

        SpelEvaluator e = new SpelEvaluator("bean('a')", m);
        assertEquals("a_bean", e.apply(root));

        e = new SpelEvaluator("#p1", m);
        assertEquals("123", e.apply(root));
        root = new RootObject();
        root.setArgs(new Object[]{null, 456});
        assertNull(e.apply(root));

    }

    @Test
    public void test2() throws Exception {
        RootObject root = new RootObject();
        UserInfo userInfo = new UserInfo();
        userInfo.setName("张三");
        userInfo.setAge(99);
        WorkInfo workInfo = new WorkInfo();
        workInfo.setName("北京北京");
        userInfo.setWorkInfo(workInfo);
        List<String> infoList = new ArrayList<>();
        infoList.add("111");
        infoList.add("222");
        userInfo.setInfoList(infoList);
        List<WorkInfo> workList = new ArrayList<>();
        workList.add(workInfo);
        userInfo.setWorkList(workList);
        root.setArgs(new Object[]{userInfo});
        Method m = SpelEvaluatorTest.class.getMethod("targetMethod2", UserInfo.class);

        SpelEvaluator e = new SpelEvaluator("#userInfo.name", m);
        assertEquals("张三", e.apply(root));
        e = new SpelEvaluator("#userInfo.workInfo.name", m);
        assertEquals("北京北京", e.apply(root));
        e = new SpelEvaluator("#userInfo.infoList", m);
        System.out.println(e.apply(root));
        e = new SpelEvaluator("#userInfo.workList", m);
        System.out.println(e.apply(root));
    }

    public static class RootObject extends MethodResult {

        public String bean(String name) {
            return name + "_bean";
        }
    }

    public static class WorkInfo {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class UserInfo {

        private String name;

        private int age;

        private WorkInfo workInfo;

        private List<String> infoList;

        private List<WorkInfo> workList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public WorkInfo getWorkInfo() {
            return workInfo;
        }

        public void setWorkInfo(WorkInfo workInfo) {
            this.workInfo = workInfo;
        }

        public List<String> getInfoList() {
            return infoList;
        }

        public void setInfoList(List<String> infoList) {
            this.infoList = infoList;
        }

        public List<WorkInfo> getWorkList() {
            return workList;
        }

        public void setWorkList(List<WorkInfo> workList) {
            this.workList = workList;
        }
    }
}

