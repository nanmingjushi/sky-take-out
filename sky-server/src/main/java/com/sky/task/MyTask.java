package com.sky.task;

/* 
    @author nanchao 
    @date 2025/7/4
*/


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;


//自定义 定时任务类
@Component // 被Spring扫描并实例化
@Slf4j
public class MyTask {

    //定时任务
    //@Scheduled(cron = "*/5 * * * * *")
    public void executeTask(){
        log.info("自定义定时任务类开始执行:{}",new Date());
    }
}
