package com.shw.quartz.api.job;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author shw
 * @version 1.0
 * @date 2020/9/14 13:49
 * @description
 */
public class HelloJob2 implements Job {

    // 这里是第二种获取jobDataMap中的值得方法
    private String message;
    private Float floatJobValue;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Float getFloatJobValue() {
        return floatJobValue;
    }

    public void setFloatJobValue(Float floatJobValue) {
        this.floatJobValue = floatJobValue;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //打印当前执行时间
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.out.println("现在的时间是：" + format);
        // 具体的业务逻辑
        System.out.println("开始生成任务报表 或者 开始发送邮件");

        // 打印jobDataMap定义的message的值
        System.out.println("jobDataMap定义的message的值：" + message);
        // 打印jobDataMap定义的floatJobValue
        System.out.println("jobDatMap定义的floatJobValue的值：" + floatJobValue);

    }

}
