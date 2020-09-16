package com.shw.quartz.api.tigger;


import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

import static org.quartz.DateBuilder.dateOf;
import static org.quartz.DateBuilder.futureDate;

/**
 * @author shw
 * @version 1.0
 * @date 2020/9/14 13:53
 * @description 有四种，最简单就是SimpleScheduleBuilder
 */
public class SecondScheduler {
    public static void main(String[] args) throws SchedulerException {

        // 创建一个JobDetail的实例，将该实例与HelloJob绑定
        JobDetail zhlJob = JobBuilder.newJob(HelloTriggerJob.class).withIdentity("zhlJob").build();
        // 开始时间 3秒钟后 （具体时间按实际业务编写）
        Date sDate = new Date();
        sDate.setTime(sDate.getTime() + 3000);
        // 结束时间 6秒钟以后 （具体按照实际业务编写）
        Date eDate = new Date();
        eDate.setTime(eDate.getTime() + 6000);

        // 创建一个Trigger实例，定义该job3秒后执行，在6秒后结束
        SimpleTrigger zhlTrigger = TriggerBuilder.newTrigger()
                .withIdentity("zhlTrigger")
                .startAt(sDate)
                // 5分钟后开始
                //.startAt(futureDate(5, DateBuilder.IntervalUnit.MINUTE))
                // 晚上十点钟结束
                //.endAt(dateOf(22, 0, 0))
                // 当有多个Trigger需要执行但是线程不够时的优先级，默认为5
                .withPriority(10)
                .endAt(eDate)
                // 每隔两秒钟执行一次
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()
                        // 执行器错过执行后的策略，有默认值，也可以显示指定
                        .withMisfireHandlingInstructionNextWithExistingCount())
                .build();
        // 创建Scheduler实例
        StdSchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        scheduler.start();
        scheduler.scheduleJob(zhlJob,zhlTrigger);
    }
}
