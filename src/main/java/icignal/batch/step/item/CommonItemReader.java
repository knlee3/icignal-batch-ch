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


import icignal.batch.icg.repository.ICGMapper;
import icignal.batch.util.ICNDateUtility;

@Component
public class CommonItemReader {


private static final Logger log = LoggerFactory.getLogger(CommonItemReader.class);	


	@StepScope
	@SuppressWarnings("serial")	
	@Bean
	public MyBatisCursorItemReader<Map<String,Object>>  readerB2C(
			@Qualifier("sqlSessionFactoryB2C")	SqlSessionFactory sqlSessionFactory,
			@Qualifier("ICGMapper") ICGMapper mapper,
			@Value("#{jobParameters}") Map<String,Object> jobParameters,
			@Value("#{stepExecution.stepName}")  String stepName	) throws Exception {
		return reader(sqlSessionFactory, mapper, jobParameters, stepName);
	}
	
	
	@SuppressWarnings("serial")
	public MyBatisCursorItemReader<Map<String,Object>> reader( SqlSessionFactory sqlSessionFactory, ICGMapper mapper,
			 													Map<String,Object> jobParameters,	  String stepName ) throws Exception {
		   log.info("StepName:::: " + stepName);
		   Map<String, Object> stepInfo = 	mapper.findStepByStepId(  
													new HashMap<String, Object>() {	     
														{
											                put("stepId", stepName);
											            }
													} 
												 );
			String mapperId = (String)stepInfo.get("mapperId");
			Date startDt = (Date)stepInfo.get("condExtrStartDt");
			Date endDt = (Date)stepInfo.get("condExtrEndDt");

			log.info("############추출조건 기간##########");
			log.info(ICNDateUtility.getFormattedDate(startDt, ICNDateUtility.yyyyMMdd ) +" ~ " 
					 + ICNDateUtility.getFormattedDate(endDt, ICNDateUtility.yyyyMMdd ));
			log.info("############추출조건 기간##########");


			log.info("mapperId: " + mapperId);
		 	
			
			 final MyBatisCursorItemReader<Map<String,Object>> reader = new MyBatisCursorItemReader<>();
			 
			 reader.setSqlSessionFactory(sqlSessionFactory);
			 reader.setQueryId(mapperId);		
			 
			 reader.setParameterValues(new HashMap<String, Object>() {	     
				{
					put("startDt", ICNDateUtility.getFormattedDate(startDt, ICNDateUtility.yyyyMMdd ));
					put("endDt", ICNDateUtility.getFormattedDate(endDt, ICNDateUtility.yyyyMMdd ));
	                
	              
	            }
	        });
			
			return reader;
		
		
	}
	
	
	@StepScope
	@SuppressWarnings("serial")	
	@Bean
	public MyBatisCursorItemReader<Map<String,Object>>  readerIC(
			@Qualifier("sqlSessionFactoryIC")	SqlSessionFactory sqlSessionFactory, 
			@Qualifier("ICGMapper") ICGMapper mapper,
			@Value("#{jobParameters}") Map<String,Object> jobParameters,
			@Value("#{stepExecution.stepName}")  String stepName
			) throws Exception {
		return reader(sqlSessionFactory, mapper, jobParameters, stepName);
	}

	



	
	
}
