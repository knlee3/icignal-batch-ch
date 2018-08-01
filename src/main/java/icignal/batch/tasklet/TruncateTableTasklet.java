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
		
		 mapper.truncateTable(this.tableName);
		 return RepeatStatus.FINISHED;
	}

}
