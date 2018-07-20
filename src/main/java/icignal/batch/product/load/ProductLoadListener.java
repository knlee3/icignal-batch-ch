package icignal.batch.product.load;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

import icignal.batch.icg.repository.ICGMapper;

public class ProductLoadListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger( Class.class);
	
	private final  ICGMapper mapper;
	
	public ProductLoadListener(ICGMapper mapper) {
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
