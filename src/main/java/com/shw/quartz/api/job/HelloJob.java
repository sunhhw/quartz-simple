package com.shw.quartz.api.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.*;

/**
 * @author shw
 * @version 1.0
 * @date 2020/9/14 13:01
 * @description
 */
public class HelloJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //打印当前执行时间
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.out.println("现在的时间是：" + format);
        // 具体的业务逻辑
        System.out.println("开始生成任务报表 或者 开始发送邮件");

        JobKey key = jobExecutionContext.getJobDetail().getKey();
        // 打印jobDetail 的name
        System.out.println("jobDetail 的 name：" + key.getName());
        // 打印jobDetail 的group
        System.out.println("jobDetail 的 group：" + key.getGroup());

        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String message = jobDataMap.getString("message");
        float floatJobValue = jobDataMap.getFloat("FloatJobValue");
        // 打印jobDataMap定义的message的值
        System.out.println("jobDataMap定义的message的值：" + message);
        // 打印jobDataMap定义的floatJobValue
        System.out.println("jobDatMap定义的floatJobValue的值："+floatJobValue);

    }
}
