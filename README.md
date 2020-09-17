# quartz-simple
简单介绍quartz定时任务的基本使用

本文参考：  
https://github.com/EalenXie/springboot-quartz-simple  
https://www.cnblogs.com/zhanghaoliang/p/7886110.html  
https://www.w3cschool.cn/quartz_doc/quartz_doc-h4ux2cq6.html  

本例子中的测试url:  
访问localhost:89/quartz/startHelloWorldJob 启动一个名为HelloWorld,组名为GroupOne的定时任务，每隔2秒打印 "hello world"  
访问localhost:89/quartz/pauseHelloWorldJob 暂停这个HelloWorld 的定时任务  
访问localhost:89/quartz/resumeHelloWorldJob 恢复这个HelloWorld 的定时任务  
访问localhost:89/quartz/deleteHelloWorldJob 删除这个HelloWorld 的定时任务  
访问localhost:89/quartz/modifyHelloWorldJobCron 修改这个HelloWorld 的定时任务,修改之后,每隔5秒打印 "hello world"
