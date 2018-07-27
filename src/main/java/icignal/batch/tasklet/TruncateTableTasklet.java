package icignal.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import icignal.batch.icg.repository.ICGMapper;

public class TruncateTableTasklet implements Tasklet {
	private final  ICGMapper mapper;
	private final  String tableName;
	
	public TruncateTableTasklet(ICGMapper mapper, String tableName) {
		this.mapper = mapper;
		this.tableName  = tableName;
	}

	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		 
		 // JobExecution je = chunkContext.getStepContext().getStepExecution().getJobExecution();
		 
	//	 Map<String, Object> params = chunkContext.getStepContext().getJobParameters();
	//	 String tableName =  (String)params.get("mrt.ch_ord_prod_daily_sum_stg");
		 mapper.truncateTable(this.tableName);
		 return RepeatStatus.FINISHED;
	}

}
