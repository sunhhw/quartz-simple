package com.shw.quartz.springboot_simple.controller;

import com.shw.quartz.springboot_simple.domain.TaskDefine;
import com.shw.quartz.springboot_simple.service.HelloQuartzServiceImpl;
import com.shw.quartz.springboot_simple.service.QuartzJobServiceImpl;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shw
 * @version 1.0
 * @date 2020/9/14 17:54
 * @description
 */
@RestController
public class HelloQuartzController {

    @Autowired
    private QuartzJobServiceImpl quartzJobService;

    // 假如这个定时任务的名字叫做HelloWorld，组名GroupOne
    private final JobKey jobKey = JobKey.jobKey("HelloWorld","GroupOne");

    /**
     * 启动Hello World
     * @return
     */
    @GetMapping("/startHelloWorldJob")
    public String startHelloWorldJob() throws SchedulerException {

        Map<String,String> map = new HashMap<>();
        map.put("name","zhangsan");

        // 创建一个定时任务
        TaskDefine task = TaskDefine.builder()
                .jobKey(jobKey)
                // 定时任务的 cron表达式
                .cronExpression("0/2 * * * * ? ")
                // 定时任务的具体执行逻辑
                .jobClass(HelloQuartzServiceImpl.class)
                // 定时任务的描述
                .description("这是一个测试的任务")
                .jobDataMap(map)
                .build();

        quartzJobService.scheduleJon(task);

        return "start HelloWorld Jon Success";
    }

    /**
     * 暂停 hello world
     * @return
     */
    @GetMapping("/pauseHelloWorldJob")
    public String pauseHelloWorldJob() throws SchedulerException {
        quartzJobService.pauseJob(jobKey);
        return "pause HelloWorld Job success";
    }

    /**
     * 删除定时任务
     * @return
     */
    @GetMapping("/deleteHelloWorldJob")
    public String deleteHelloWorldJob() throws SchedulerException {
        quartzJobService.deleteJob(jobKey);
        return "delete HelloWorld Job success";
    }

    /**
     * 恢复
     * @return
     */
    @GetMapping("/resumeHelloWorldJob")
    public String resumeHelloWorldJob() throws SchedulerException {
        quartzJobService.resumeJob(jobKey);
        return "resume HelloWorld Job success";
    }

    /**
     * 修改 hello world 的cron表达式
     * @return
     */
    @GetMapping("/modifyHelloWorldJobCron")
    public String modifyHelloWorldJobCron() {

        // 这是即将要修改的cron的定时任务
        TaskDefine task = TaskDefine.builder()
                .jobKey(jobKey)
                .cronExpression("0/5 * * * * ? ")
                .jobClass(HelloQuartzServiceImpl.class)
                .description("这是修改后的测试任务")
                .build();

        if (quartzJobService.modifyJobCron(task)) {
            return "修改成功";
        }
        return "修改失败";
    }

    /**
     * 立即执行一次（定时任务处于暂停状态才可）
     * @return
     */
    @GetMapping("/run")
    public String runOne() throws SchedulerException {

        Map<String,String> map = new HashMap<>();
        map.put("name","zhangsan");

        TaskDefine task = TaskDefine.builder()
                .jobKey(jobKey)
                .jobDataMap(map)
                .build();

        quartzJobService.run(task);

        return "运行成功";

    }



}
