package icignal.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.batch.core.JobExecution;
import icignal.batch.icg.repository.ICGMapper;

public class SumMemAgreeDailyTasklet  implements Tasklet {

	//private final  ICGMapper mapper;
	
	@Autowired
	public ICGMapper mapper;
	
/*	public SumMemAgreeDailyTasklet(ICGMapper mapper) {
		this.mapper = mapper;
	}*/

	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		 
		 JobExecution je = chunkContext.getStepContext().getStepExecution().getJobExecution();
		 mapper.summaryMemAgreeDaily(je.getId());
		 return RepeatStatus.FINISHED;
	}

}
