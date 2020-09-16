package com.shw.quartz.springboot_simple.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shw
 * @version 1.0
 * @date 2020/9/14 17:55
 * @description
 */
public class HelloQuartzServiceImpl implements Job {

    final Logger logger = LoggerFactory.getLogger(HelloQuartzServiceImpl.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //编写自己的逻辑
        logger.info("这里是自己的逻辑代码 Hello World!");
        System.out.println("触发器开始时间"+jobExecutionContext.getTrigger().getStartTime());
        System.out.println("获取name的值："+jobExecutionContext.getJobDetail().getJobDataMap().getString("name"));

    }
}
