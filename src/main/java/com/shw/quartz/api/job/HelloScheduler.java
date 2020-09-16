package com.shw.quartz.api.job;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * @author shw
 * @version 1.0
 * @date 2020/9/14 12:49
 * @description
 */
public class HelloScheduler {
    public static void main(String[] args) throws SchedulerException {
        // 1.创建一个jobDetail的实例，将该实例与HelloJob Class 绑定
        JobDetail jobDetail = JobBuilder.newJob(HelloJob2.class)// 定义Job类为HelloJob类，真正的执行逻辑所在
                .withIdentity("myJob", "group1")// 定义name和group
                .usingJobData("message", "hello myjob1") // 加入属性到jobDataMap
                .usingJobData("FloatJobValue", 8.88f) // 加入属性到jobDataMap
                .build();

        // 2.创建一个Trigger触发器的实例，定义该job立即执行，并且每两秒钟执行一次，一直执行
        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever())
                .build();

        // 3.创建schedule实例
        StdSchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        scheduler.start();
        Date date = scheduler.scheduleJob(jobDetail, trigger);
    }
}
