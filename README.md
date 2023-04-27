# dilog

## 简介
一个将Java方法入参及结果快速打印到日志的组件

## 使用步骤

### 1、添加@EnableSysLog
启动类上使用@EnableSysLog开启日志功能
```java
@EnableSysLog
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
    }
}
```

### 2、添加@SysLog
方法上使用@SysLog开启打印
```java
@SysLog
public List<String> getList(String a, int b) {
    return new ArrayList();
}
```

## @SysLog属性表

|属性|默认值|说明|
| --- | --- |--- |
|logKey|方法名|指定缓存方法名|
|param|全部入参|参数输出范围|
|successOn|true|是否打印成功日志|
|success|未定义|成功日志输出格式|
|sizeLimit|20|结果集输出数量|
|failOn|true|是否打印失败日志|
|fail|未定义|失败日志输出格式|
|group|default|日志分组|
|operator|未定义|操作人|
|performance|false|是否打印性能指标|

