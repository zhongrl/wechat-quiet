package com.quiet.live.hall.job;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.quiet.live.hall.service.IJEmailMsgService;

import lombok.extern.slf4j.Slf4j;

@Component
@Configuration      
@EnableAsync
@Slf4j
public class SendTemplateMsgTask {
	
	@Autowired
	IJEmailMsgService iJEmailMsgService;
	 //3.添加定时任务
    @Scheduled(cron = "0 0/20 * * * ?")
    private void configureTasks() {
    	try {
    		iJEmailMsgService.sendMsg();
			log.info("执行定时任务时间: " + LocalDateTime.now());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("定时任务异常",e);
		}
    }
}
