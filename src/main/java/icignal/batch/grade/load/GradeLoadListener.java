package icignal.batch.grade.load;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

import icignal.batch.icg.repository.ICGMapper;

public class GradeLoadListener extends JobExecutionListenerSupport {
	private static final Logger log = LoggerFactory.getLogger( Class.class);
	
	private final  ICGMapper mapper;
	
	public GradeLoadListener(ICGMapper mapper) {
		this.mapper = mapper;
	}
	
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		 
		log.info(jobExecution.getJobInstance().getJobName() + " is beginning execution.");
		
	}
	
	
	
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("Finish Job! Check the results");
	//		log.info("table insert count::: ");
		
		}
	}
}
