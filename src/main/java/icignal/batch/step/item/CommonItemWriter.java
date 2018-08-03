package icignal.batch.step.item;

import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import icignal.batch.icg.repository.ICGMapper;

@Component
public class CommonItemWriter extends MapperDao{

	private static final Logger log = LoggerFactory.getLogger(CommonItemWriter.class);	

	
	
    @Bean    
    @StepScope
	public MyBatisBatchItemWriter<Map<String,Object>> writerIC(
			@Qualifier("sqlSessionFactoryWriter")	SqlSessionFactory sqlSessionFactory,
			@Qualifier("ICGMapper") ICGMapper mapper,
			@Value("#{jobParameters}") Map<String,Object> jobParameters,
			@Value("#{stepExecution}")  StepExecution stepExecution
			) throws Exception {	
    	
    	String jobName = stepExecution.getJobExecution().getJobInstance().getJobName();
		String stepName = stepExecution.getStepName();
			
		return writer(sqlSessionFactory, jobParameters, jobName, stepName );
	}
    
    
	
	
	
	
	
}
