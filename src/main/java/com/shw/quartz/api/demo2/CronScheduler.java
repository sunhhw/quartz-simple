package com.shw.quartz.api.demo2;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author shw
 * @version 1.0
 * @date 2020/9/14 10:36
 * @description
 */
public class CronScheduler {

    public static void main(String[] args) throws SchedulerException {

        //jobDetail
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("cronJob").build();
        //cornTrigger
        //每日的9点40触发任务
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("cronTrigger").withSchedule(
                CronScheduleBuilder.cronSchedule("0 46 10 * * ? ")).build();
        //1.每日10点15分触发 0 15 10 ？ * *
        //2.每天下午的2点到2点59分（整点开始，每隔5分钟触发） 0 0/5 14 * * ?
        //3.从周一到周五每天的上午10点15触发 0 15 10 ？ MON-FRI
        //4.每月的第三周的星期五上午10点15分触发 0 15 10 ？ * 6#3
        //5.2016年到2017年每月最后一周的星期五的10点15分触发 0 15 10 ？ * 6L 2016-2017
        //Scheduler实例
        StdSchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail,cronTrigger);

    }

}
