package com.shw.quartz.api.demo2;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author shw
 * @version 1.0
 * @date 2020/9/14 10:33
 * @description
 */
public class HelloJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //打印当前的时候
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("现在的时间是："+simpleDateFormat.format(date));
        //具体的业务
        System.out.println("开始生成任务报表或者开始发送邮件");
    }

}
