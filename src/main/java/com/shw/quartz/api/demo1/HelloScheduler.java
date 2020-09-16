package com.shw.quartz.api.demo1;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author shw
 * @version 1.0
 * @date 2020/9/14 10:23
 * @description
 * 每隔两秒钟执行一次，一直执行
 */
public class HelloScheduler {
    public static void main(String[] args) throws SchedulerException {
        //创建一个JobDetail实例，将该实例与Hob ass绑定
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("myjob").build();
        // 创建一个Trigger触发器的实例，定义该job立即执行，并且每2秒钟执行一次，一直执行
        SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger").startNow().withSchedule(
                SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()
        ).build();
        // 创建schedule实例
        StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = stdSchedulerFactory.getScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail,trigger);
    }
}
