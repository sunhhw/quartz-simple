package com.shw.quartz.api.tigger;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author shw
 * @version 1.0
 * @date 2020/9/14 14:12
 * @description
 */
public class HelloTriggerJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 打印当前时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("现在的时间是："+simpleDateFormat.format(date));

        // 具体的业务逻辑
        System.out.println("执行具体的业务逻辑");
        JobKey key = jobExecutionContext.getJobDetail().getKey();
        Trigger trigger = jobExecutionContext.getTrigger();
        // 打印开始时间
        System.out.println("开始时间："+ simpleDateFormat.format(trigger.getStartTime()));

        // 打印结束时间
        System.out.println("结束时间："+simpleDateFormat.format(trigger.getEndTime()));

    }
}
