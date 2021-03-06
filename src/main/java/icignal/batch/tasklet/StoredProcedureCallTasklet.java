package icignal.batch.tasklet;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;


import icignal.batch.icg.repository.ICGMapper;
import icignal.batch.step.item.CommonService;
import icignal.batch.util.ICNStringUtility;


public class StoredProcedureCallTasklet extends CommonService implements Tasklet {

	private static final Logger log = LoggerFactory.getLogger(StoredProcedureCallTasklet.class);
//	private final  ICGMapper mapper;
	
	
	
	public StoredProcedureCallTasklet(ICGMapper mapper) {
		super.mapper = mapper;
	}

	
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		 
		JobExecution je = chunkContext.getStepContext().getStepExecution().getJobExecution();
		String jobName =  je.getJobInstance().getJobName();
		String stepName = chunkContext.getStepContext().getStepExecution().getStepName();
		
		log.info("jobName: " + jobName);
		log.info("stepName: " + stepName);
		log.info("SP_TASKLET: " + SP_TASKLET);

		System.out.println(findJobStepMapperInfo(jobName, stepName, SP_TASKLET ));
		
		
		String mapperId = (String)findJobStepMapperInfo(jobName, stepName, SP_TASKLET ).get("mapperId");
		mapperId =  ICNStringUtility.getStringOfLastSper(mapperId, ".");
		
		log.info("mapperId: " + mapperId);
	    	 
		Method method = mapper.getClass().getMethod(mapperId, Map.class);
		method.invoke( mapper , new HashMap<String, Object>().put("pJobExecId", je.getId()));
		

		 return RepeatStatus.FINISHED;
	}


}
