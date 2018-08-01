package icignal.batch.step.item;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import icignal.batch.icg.repository.ICGMapper;
import icignal.batch.util.ICNDateUtility;


public class MapperDao {
    
	@Autowired
	public ICGMapper mapper;
	
	
	
	
	private static final Logger log = LoggerFactory.getLogger(MapperDao.class);	
	
    public MyBatisBatchItemWriter<Map<String,Object>> writer( SqlSessionFactory sqlSessionFactory, 
    		 Map<String,Object> jobParameters,	 String stepName ) throws Exception {
    	
    	 final  MyBatisBatchItemWriter<Map<String,Object>> writer = new  MyBatisBatchItemWriter<>();	
    	writer.setSqlSessionFactory(sqlSessionFactory);
    	//writer.setStatementId("icignal.batch.icg.repository.ICGMapper.insertOrdProdDailySumStg");
    	writer.setStatementId((String)findStepInfo(stepName).get("mapperId"));
		 return writer;
    }
	
	

	@SuppressWarnings("serial")
	public MyBatisCursorItemReader<Map<String,Object>> reader( SqlSessionFactory sqlSessionFactory, 
			 													Map<String,Object> jobParameters,	  
			 													String stepName ) throws Exception {
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
    
    
    
    
    @SuppressWarnings("serial")
	public Map<String, Object> findStepInfo(String stepName){
    	log.info("mapper: " + mapper);
    	 return	mapper.findStepByStepId(  
					new HashMap<String, Object>() {	     
						{
			                put("stepId", stepName);
			            }
					} 
				 );
    	
    	
    }
    
    
    
    
    
	
	
}
