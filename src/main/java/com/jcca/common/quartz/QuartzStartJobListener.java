package com.jcca.common.quartz;

import com.jcca.logic.middle.collect.impl.CollectInterfaceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 初始化开始调度任务
 * 
 * @author
 *
 */
@Configuration
@Slf4j
public class QuartzStartJobListener implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	private CollectInterfaceImpl collectInterface;
	/**
	 * 初始化任务
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		collectInterface.init();
	}



}
