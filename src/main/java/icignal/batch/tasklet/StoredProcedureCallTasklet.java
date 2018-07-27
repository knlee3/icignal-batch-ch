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

import icignal.batch.Listener.MemberListener;
import icignal.batch.icg.repository.ICGMapper;


public class StoredProcedureCallTasklet implements Tasklet {

	private static final Logger log = LoggerFactory.getLogger(MemberListener.class);
	private final  ICGMapper mapper;
	
	public StoredProcedureCallTasklet(ICGMapper mapper) {
		this.mapper = mapper;
	}

	
	@SuppressWarnings("serial")
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		 
		JobExecution je = chunkContext.getStepContext().getStepExecution().getJobExecution();
		String stepName = chunkContext.getStepContext().getStepExecution().getStepName();
		 
		log.info("stepName: " + stepName);
		 
		String mapperId = mapper.findByStepId(  new HashMap<String, Object>() {	     
					{
		                put("stepId", stepName);
		            }
					
			 } ).get("mapperId");
		
		log.info("mapperId: " + mapperId);
			 
		Method method = mapper.getClass().getMethod(mapperId, Map.class);
		method.invoke( mapper , new HashMap<String, Object>().put("pJobExecId", je.getId()));
		

		 return RepeatStatus.FINISHED;
	}


}
