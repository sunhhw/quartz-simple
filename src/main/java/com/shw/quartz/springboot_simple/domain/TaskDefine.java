package com.shw.quartz.springboot_simple.domain;

import com.oracle.tools.packager.mac.MacAppBundler;
import lombok.Builder;
import lombok.Data;
import org.quartz.Job;
import org.quartz.JobKey;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author shw
 * @version 1.0
 * @date 2020/9/14 17:54
 * @description Quartz任务最基本的属性
 */
@Builder
@Data
public class TaskDefine {

    /**
     * 定时任务 的名字和分组名 JobKey,{@link org.quartz.JobKey}
     */
    @NotNull(message = "定时任务的 名字 和 组名 坚决不可为空")
    private JobKey jobKey;

    /**
     * 定时任务的描述（可以定时任务本身的描述，也是可以触发的）
     * {@link org.quartz.JobDetail} description ;
     * {@link org.quartz.Trigger} description
     */
    private String description;

    /**
     * 定时任务的cron （Trigger的CronScheduleBuilder 的cronException）
     * {@link org.quartz.Trigger} CronScheduleBuilder {@link org.quartz.CronScheduleBuilder}
     */
    @NotEmpty(message = "定时任务的执行cron 不能为空")
    private String cronExpression;

    /**
     * 定时任务的元数据
     *
     * {@link org.quartz.JobDataMap}
     */
    private Map<?,?> jobDataMap;

    /**
     * 定时任务的具体执行逻辑类
     * {@link org.quartz.Job}
     */
    @NotNull(message = "定时任务的具体执行逻辑 坚决不能为空")
    private Class<? extends Job> jobClass;



}
