package icignal.batch.step.item;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.batch.api.chunk.listener.ItemReadListener;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class CommonItemReader  extends MapperDao{


	private static final Logger log = LoggerFactory.getLogger(CommonItemReader.class);	


	@StepScope
	@Bean
	public MyBatisCursorItemReader<Map<String,Object>>  readerB2C(
			@Qualifier("sqlSessionFactoryB2C")	SqlSessionFactory sqlSessionFactory,
			@Value("#{jobParameters}") Map<String,Object> jobParameters,
			@Value("#{stepExecution.stepName}")  String stepName	) throws Exception {
		return reader(sqlSessionFactory,  jobParameters, stepName);
	}
	
	
	
	
	@StepScope
	@Bean
	public MyBatisCursorItemReader<Map<String,Object>>  readerIC(
			@Qualifier("sqlSessionFactoryIC")	SqlSessionFactory sqlSessionFactory, 
			@Value("#{jobParameters}") Map<String,Object> jobParameters,
			@Value("#{stepExecution.stepName}")  String stepName
			) throws Exception {
		return reader(sqlSessionFactory,  jobParameters, stepName);
	}

	



	
	
}
