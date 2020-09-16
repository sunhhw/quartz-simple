package com.shw.quartz.springboot_simple.service;

import com.shw.quartz.springboot_simple.domain.TaskDefine;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import sun.net.www.protocol.http.HttpURLConnection;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author shw
 * @version 1.0
 * @date 2020/9/15 10:25
 * @description 核心功能其实就是Scheduler的功能，这里只是简单地实例说明
 * 如需根据自身业务进行扩展，请参考{@link org.quartz.Scheduler}
 */
@Service
public class QuartzJobServiceImpl {

    //Quartz定时任务核心的功能实现类
    private Scheduler scheduler;

    public QuartzJobServiceImpl(@Autowired SchedulerFactoryBean schedulerFactoryBean) {
        scheduler = schedulerFactoryBean.getScheduler();
    }

    /**
     * 创建和启动定时任务
     * {@link org.quartz.Scheduler#scheduleJob(JobDetail, Trigger)}
     *
     * @param task 定时任务
     */
    public void scheduleJon(TaskDefine task) throws SchedulerException {
        // 1.定时任务的名称和组名称
        JobKey jobKey = task.getJobKey();
        // 2.定时任务 的 元数据
        JobDataMap jobDataMap = getJobDataMap(task.getJobDataMap());
        // 3.定时任务的描述
        String description = task.getDescription();
        // 4.定时任务的逻辑实现类
        Class<? extends Job> jobClass = task.getJobClass();
        // 5.定时任务的cron表达式
        String cronExpression = task.getCronExpression();

        JobDetail jobDetail = getJobDetail(jobKey, description, jobDataMap, jobClass);

        Trigger trigger = getTrigger(jobKey, description, jobDataMap, cronExpression);

        scheduler.scheduleJob(jobDetail, trigger);

    }

    /**
     * 暂停Job
     * {@link org.quartz.Scheduler#pauseJob(JobKey)}
     */
    public void pauseJob(JobKey jobKey) throws SchedulerException {
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复job
     *
     * @param jobKey
     */
    public void resumeJob(JobKey jobKey) throws SchedulerException {
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除job
     *
     * @param jobKey
     */
    public void deleteJob(JobKey jobKey) throws SchedulerException {
        scheduler.deleteJob(jobKey);
    }

    public boolean modifyJobCron(TaskDefine define) {
        String cronExpression = define.getCronExpression();
        // 1.如果cron表达式的格式不正确，则返回修改失败
        if (!CronExpression.isValidExpression(cronExpression)) {
            return false;
        }
        JobKey jobKey = define.getJobKey();
        // 根据用户名和组查询到对应的触发器
        TriggerKey triggerKey = new TriggerKey(jobKey.getName(), jobKey.getGroup());
        try {
            CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            JobDataMap jobDataMap = getJobDataMap(define.getJobDataMap());
            // 2.如果cron发生了变化，则按照新的cron触发，进行重新启动定时任务
            if (!cronTrigger.getCronExpression().equalsIgnoreCase(cronExpression)) {
                CronTrigger trigger = TriggerBuilder.newTrigger()
                        .withIdentity(triggerKey)
                        .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                        .usingJobData(jobDataMap)
                        .build();
                scheduler.rescheduleJob(triggerKey, trigger);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取Trigger（Job的触发器，执行规则）
     *
     * @param jobKey         定时任务的名称 组名
     * @param description    定时任务的描述
     * @param jobDataMap     定时任务的元数据
     * @param cronExpression 定时任务的执行cron表达式
     * @return
     */
    public Trigger getTrigger(JobKey jobKey, String description, JobDataMap jobDataMap, String cronExpression) {
        return TriggerBuilder.newTrigger()
                .withIdentity(jobKey.getName(), jobKey.getGroup())
                .withDescription(description)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .usingJobData(jobDataMap)
                .build();
    }

    /**
     * 获取定时任务的定义
     * JobDetail是任务的定义，Job是任务的执行逻辑
     *
     * @param jobKey      定时任务的名称 组名
     * @param description 定时任务的描述
     * @param jobDataMap  定时任务的元数据
     * @param clazz       定时任务真正的逻辑定义类{@link Job}
     * @return
     */
    public JobDetail getJobDetail(JobKey jobKey, String description, JobDataMap jobDataMap, Class<? extends Job> clazz) {
        return JobBuilder.newJob(clazz)
                .withIdentity(jobKey)
                .withDescription(description)
                .usingJobData(jobDataMap)
                .requestRecovery()
                .storeDurably()
                .build();
    }

    public JobDataMap getJobDataMap(Map<?, ?> map) {
        return map == null ? new JobDataMap() : new JobDataMap(map);
    }

}
