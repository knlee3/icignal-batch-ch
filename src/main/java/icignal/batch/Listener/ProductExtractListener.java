package icignal.batch.Listener;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

import icignal.batch.icg.repository.ICGMapper;

public class ProductExtractListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger( Class.class);
	
	private final  ICGMapper mapper;
	
	public ProductExtractListener(ICGMapper mapper) {
		this.mapper = mapper;
	}
	
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		 
		log.info(jobExecution.getJobInstance().getJobName() + " is beginning execution.");
		
		List<String> tables = Arrays.asList("mrt.ch_prod_stg");
		for(String table : tables) {
			mapper.truncateTable(table);
			log.info(table + " is truncated.");
		}
		
	}
	
	
	
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("Finish Job! Check the results");
			log.info("table insert count::: " + mapper.cntChProdStg());
		
		}
	}
}