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
import org.springframework.stereotype.Component;

import icignal.batch.Listener.MemberListener;
import icignal.batch.icg.repository.ICGMapper;
import icignal.batch.step.item.MapperDao;


public class StoredProcedureCallTasklet extends MapperDao implements Tasklet {

	private static final Logger log = LoggerFactory.getLogger(MemberListener.class);
//	private final  ICGMapper mapper;
	
	public StoredProcedureCallTasklet(ICGMapper mapper) {
		super.mapper = mapper;
	}

	
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		 
		JobExecution je = chunkContext.getStepContext().getStepExecution().getJobExecution();
		String stepName = chunkContext.getStepContext().getStepExecution().getStepName();
		 
		log.info("stepName: " + stepName);
	/*	 
		String mapperId = (String)mapper.findStepByStepId(  new HashMap<String, Object>() {	     
							{
				                put("stepId", stepName);
				            }
					
			 } ).get("mapperId");
		
		log.info("mapperId: " + mapperId);
		
		*/
		
		String mapperId = (String)findStepInfo(stepName).get("mapperId");
		
		log.info("mapperId: " + mapperId);
			 
		Method method = mapper.getClass().getMethod(mapperId, Map.class);
		method.invoke( mapper , new HashMap<String, Object>().put("pJobExecId", je.getId()));
		

		 return RepeatStatus.FINISHED;
	}


}
