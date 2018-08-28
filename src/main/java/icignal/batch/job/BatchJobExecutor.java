package icignal.batch.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;


@DisallowConcurrentExecution
public class BatchJobExecutor implements org.quartz.Job {  //extends QuartzJobBean {
 
	 @Autowired
	private JobLocator jobLocator;

    @Autowired
    private JobLauncher jobLauncher;

    private static final Logger log = LoggerFactory.getLogger(BatchJobExecutor.class);
/*
 private String jobName;
// private JobLauncher jobLauncher;
// private JobLocator jobLocator;

 public String getJobName() {
  return jobName;
 }

 public void setJobName(String jobName) {
  this.jobName = jobName;
 }
*/
 /*
 public JobLauncher getJobLauncher() {
  return jobLauncher;
 }

 public void setJobLauncher(JobLauncher jobLauncher) {
  this.jobLauncher = jobLauncher;
 }

 public JobLocator getJobLocator() {
  return jobLocator;
 }

 public void setJobLocator(JobLocator jobLocator) {
  this.jobLocator = jobLocator;
 }*/
/*
 @Override
 protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
  JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
  
  try {
System.out.println("QuartzJobLauncher executeInternal start..........");
System.out.println("jobName:: " + jobName);
	  
   Job job = jobLocator.getJob(jobName);
   JobExecution jobExecution = jobLauncher.run(job, jobParameters);
   
   System.out.println("########### Status: " + jobExecution.getStatus());
   
  } catch(JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException 
    | JobParametersInvalidException | NoSuchJobException  e) {
   e.printStackTrace();
  } 
 }*/

/*@Override
public void execute(JobExecutionContext context) throws JobExecutionException {
	JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
	  
	context.getJobDetail().getJobDataMap().get("jobName");
			
			
	
	  try {
	System.out.println("QuartzJobLauncher executeInternal start..........");
	System.out.println("jobName:: " + jobName);
	
	
	   Job job = jobLocator.getJob(jobName);
	   JobExecution jobExecution = jobLauncher.run(job, jobParameters);
	   
	   System.out.println("########### Status: " + jobExecution.getStatus());
	   
	  } catch(JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException 
	    | JobParametersInvalidException | NoSuchJobException  e) {
	   e.printStackTrace();
	  } 
	
}
*/
    
    /**
     * Quartz Job 으로 들어온 Parameter 를 Spring Batch Parameter 로 변환하여 Spring Batch Job 실행
     * 
     * @param context quartz execution context
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        
            String jobName = BatchHelper.getJobName(context.getMergedJobDataMap());
            log.info("[{}] started.", jobName);
            JobParameters jobParameters;
		
            try {
            	jobParameters = BatchHelper.getJobParameters(context);
				jobLauncher.run(jobLocator.getJob(jobName), jobParameters);
				log.info("[{}] completed.", jobName);
			} catch (SchedulerException  | JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
					| JobParametersInvalidException | NoSuchJobException e) {
				e.printStackTrace();
				
				log.error("job execution exception! - {}", e.getCause());
	            throw new JobExecutionException();
			}
            
      
    }
    
}