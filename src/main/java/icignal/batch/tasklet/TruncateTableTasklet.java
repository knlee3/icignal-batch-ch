package icignal.batch.tasklet;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import icignal.batch.Listener.MemberListener;
import icignal.batch.icg.repository.ICGMapper;
import icignal.batch.step.item.MapperDao;

public class TruncateTableTasklet extends MapperDao implements Tasklet {
	
	private static final Logger log = LoggerFactory.getLogger(MemberListener.class);

	
	public TruncateTableTasklet(ICGMapper mapper) {
		super.mapper = mapper;
	}

	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		String jobName = chunkContext.getStepContext().getStepExecution().getJobExecution().getJobInstance().getJobName();
		String stepName = chunkContext.getStepContext().getStepExecution().getStepName();
		
		log.info("jobName: " + jobName);
		log.info("stepName: " + stepName);
		
		truncateTable(jobName,  stepName);
		 return RepeatStatus.FINISHED;
	}

}
