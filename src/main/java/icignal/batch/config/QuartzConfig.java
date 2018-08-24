package icignal.batch.config;


import java.util.HashMap;
import java.util.Map;

import org.quartz.Scheduler;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;


@Configuration
public class QuartzConfig {


	 @Autowired
	 private JobLauncher jobLauncher;
	 
	 @Autowired
	 private JobLocator jobLocator;
	 
	 
	 @Autowired
	 private Scheduler scheduler;
	 
	 
	 
	 @Bean
	 public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
	  JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
	  jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
	  
	  return jobRegistryBeanPostProcessor;
	 }
	 
	 @Bean
	 public JobDetailFactoryBean jobDetailFactoryBean(String jobName) {
	  JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
	  jobDetailFactoryBean.setJobClass(QuartzJobLauncher.class);
	  Map<String, Object> map = new HashMap<String, Object>();
//	  map.put("jobName", "testJob");
	  map.put("jobName", jobName);
	  map.put("jobLauncher", jobLauncher);
	  map.put("jobLocator", jobLocator);
	  
	  jobDetailFactoryBean.setJobDataAsMap(map);
	  
	  return jobDetailFactoryBean;
	 }
	 
	 @Bean
	 public CronTriggerFactoryBean cronTriggerFactoryBean(String jobName) {
	  CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
	  cronTriggerFactoryBean.setJobDetail(jobDetailFactoryBean(jobName).getObject());
	  //run every 10 seconds
	  cronTriggerFactoryBean.setCronExpression("*/10 * * * * ? *");
	  
	  return cronTriggerFactoryBean;
	 }
	 
	 @Bean
	 public SchedulerFactoryBean schedulerFactoryBean() {
		  SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		  schedulerFactoryBean.setTriggers(cronTriggerFactoryBean("testJob").getObject());
		  
		  return schedulerFactoryBean;
		 
		 
	//	 scheduler.scheduleJob(jobDetailFactoryBean)
	 }

	
	
}
