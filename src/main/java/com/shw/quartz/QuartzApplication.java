package com.shw.quartz;

import com.shw.quartz.springboot_simple.service.QuartzJobServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author shw
 * @version 1.0
 * @date 2020/9/14 10:19
 * @description
 */
@SpringBootApplication
public class QuartzApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuartzApplication.class,args);
    }
}
