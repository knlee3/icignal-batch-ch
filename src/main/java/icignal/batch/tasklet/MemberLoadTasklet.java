package icignal.batch.tasklet;


import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import icignal.batch.icg.repository.ICGMapper;


public class MemberLoadTasklet implements Tasklet {
	
	

	private final  ICGMapper mapper;
	
	public MemberLoadTasklet(ICGMapper mapper) {
		this.mapper = mapper;
	}

	
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		JobExecution je = chunkContext.getStepContext().getStepExecution().getJobExecution();
		//System.out.println("JobExecution: " +je.getId());
		
		// mapper.loadMember(je.getId());
		 return RepeatStatus.FINISHED;
	}

}
