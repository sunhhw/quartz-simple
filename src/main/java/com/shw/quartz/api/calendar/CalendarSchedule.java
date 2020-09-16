package com.shw.quartz.api.calendar;

import com.shw.quartz.api.tigger.HelloTriggerJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.AnnualCalendar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author shw
 * @version 1.0
 * @date 2020/9/14 15:04
 * @description 以AnnualCalendar举例，来实现某一天不执行程序
 */
public class CalendarSchedule {

    public static void main(String[] args) throws SchedulerException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //创建一个JobDetail的实例，将该实例与HelloJob绑定
        JobDetail jobDetail = JobBuilder.newJob(HelloTriggerJob.class).withIdentity("zhlJob").build();
        AnnualCalendar holidays = new AnnualCalendar();
        // 排除今天的时间2017年11月27日（月份是从0～11的）
        GregorianCalendar gregorianCalendar = new GregorianCalendar(2020, 8, 14);

        // 排除的日期，如果为false则为包含
        holidays.setDayExcluded(gregorianCalendar,true);

        // 创建Scheduler实例
        StdSchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();

        // 向Scheduler注册日历
        scheduler.addCalendar("holidays",holidays,false,false);
        SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger().withIdentity("zhlTrigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
                .modifiedByCalendar("holidays")
                .build();
        // 让trigger应用指定的日历规则
        // scheduler.scheduleJob(jobDetail,simpleTrigger)
        System.out.println("现在的时间："+simpleDateFormat.format(new Date()));
        // scheduler与jobDetail，trigge绑定，并且打印出最近一次执行的事件
        System.out.println("最近一次执行时间："+simpleDateFormat.format(scheduler.scheduleJob(jobDetail,simpleTrigger)));
        scheduler.start();
    }

}
