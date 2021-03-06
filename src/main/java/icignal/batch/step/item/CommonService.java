package icignal.batch.step.item;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import icignal.batch.icg.repository.ICGMapper;
import icignal.batch.util.ICNDateUtility;
import icignal.batch.util.ICNStringUtility;


public class CommonService  {
    
	@Autowired
	public ICGMapper mapper;
	
	public final static String ITEM_WRITER = "ItemWriter";
	public final static String ITEM_READER = "ItemReader";
	public final static String SP_TASKLET = "SPTasklet";
	public final static String TRUNC_TASKLET = "TruncTasklet";
	
	
	
	
	private static final Logger log = LoggerFactory.getLogger(CommonService.class);	
	
    public MyBatisBatchItemWriter<Map<String,Object>> writer( SqlSessionFactory sqlSessionFactory, 
    		 Map<String,Object> jobParameters,	String jobName, String stepName ) throws Exception {
        final  MyBatisBatchItemWriter<Map<String,Object>> writer = new  MyBatisBatchItemWriter<>();
    	writer.setSqlSessionFactory(sqlSessionFactory);
    	writer.setStatementId((String)findJobStepMapperInfo(jobName,stepName, ITEM_WRITER ).get("mapperId"));
    	
		 return writer;
    }
	
	

	@SuppressWarnings("serial")
	public MyBatisCursorItemReader<Map<String,Object>> reader( SqlSessionFactory sqlSessionFactory, 
			 													Map<String,Object> jobParameters,	
			 													String jobName,
			 													String stepName ) throws Exception {
		
		   Map<String, Object> stepInfo = findJobStepMapperInfo(jobName,stepName, ITEM_READER);
		   log.info("stepInfo:: " +  stepInfo);
			String mapperId = (String)stepInfo.get("mapperId");			

			log.info("mapperId: " + mapperId);
		 	
			 final MyBatisCursorItemReader<Map<String,Object>> reader = new MyBatisCursorItemReader<>();			 
			 reader.setSqlSessionFactory(sqlSessionFactory);
			 reader.setQueryId(mapperId);
			 
			 String condExtrApplyYn = (String)stepInfo.get("condExtrApplyYn");
			 if(ICNStringUtility.isEquals(condExtrApplyYn, "Y") ) {
			 
				 Date startDt = (Date)stepInfo.get("condExtrStartDt");
				 Date endDt = (Date)stepInfo.get("condExtrEndDt");
				 log.info("############추출조건 기간##########");
				 log.info(ICNDateUtility.getFormattedDate(startDt, ICNDateUtility.yyyyMMdd ) +" ~ " 
							 + ICNDateUtility.getFormattedDate(endDt, ICNDateUtility.yyyyMMdd ));
				log.info("############추출조건 기간##########");
				
				 reader.setParameterValues(new HashMap<String, Object>() {	     
					{
						put("startDt", ICNDateUtility.getFormattedDate(startDt, ICNDateUtility.yyyyMMdd));
						put("endDt", ICNDateUtility.getFormattedDate(endDt, ICNDateUtility.yyyyMMdd ));
		            }
		        });
			 }
			return reader;
	}
    
	

	@SuppressWarnings("serial")
	public MyBatisCursorItemReader<Map<String,Object>> reader( SqlSessionFactory sqlSessionFactory, 
			 													String jobName,
			 													String stepName ) throws Exception {
		
		   Map<String, Object> stepInfo = findJobStepMapperInfo(jobName,stepName, ITEM_READER);
		   log.info("stepInfo:: " +  stepInfo);
			String mapperId = (String)stepInfo.get("mapperId");			

			log.info("mapperId: " + mapperId);
		 	
			 final MyBatisCursorItemReader<Map<String,Object>> reader = new MyBatisCursorItemReader<>();			 
			 reader.setSqlSessionFactory(sqlSessionFactory);
			 reader.setQueryId(mapperId);
			 
			 String condExtrApplyYn = (String)stepInfo.get("condExtrApplyYn");
			 if(ICNStringUtility.isEquals(condExtrApplyYn, "Y") ) {
			 
				 Date startDt = (Date)stepInfo.get("condExtrStartDt");
				 Date endDt = (Date)stepInfo.get("condExtrEndDt");
				 log.info("############추출조건 기간##########");
				 log.info(ICNDateUtility.getFormattedDate(startDt, ICNDateUtility.yyyyMMdd ) +" ~ " 
							 + ICNDateUtility.getFormattedDate(endDt, ICNDateUtility.yyyyMMdd ));
				log.info("############추출조건 기간##########");
				
				 reader.setParameterValues(new HashMap<String, Object>() {	     
					{
						put("startDt", ICNDateUtility.getFormattedDate(startDt, ICNDateUtility.yyyyMMdd ));
						put("endDt", ICNDateUtility.getFormattedDate(endDt, ICNDateUtility.yyyyMMdd ));
		                
		              
		            }
		        });
			 }
			return reader;
	}
    
	
	
	public void truncateTable(String jobName, String stepName) {
		   Map<String, Object> stepInfo =  findJobStepMapperInfo(jobName, stepName, TRUNC_TASKLET);
		  String mapperParam = (String)stepInfo.get("mapperParam");
		  List<String> tableNames = Arrays.asList(mapperParam.split(","));
		  for(String item : tableNames)  mapper.truncateTable(item);
	}
	
    
    
	public List<Map<String, Object>> findJobStepInfo(Map<String, Object> map ){
    	return mapper.findJobStepInfo(map);
    }
	
	

	
	
	
    
    @SuppressWarnings("serial")
	public Map<String, Object> findJobStepMapperInfo(String jobName, String stepName, String itemType){
    	
    	 return	mapper.findJobStepMapperInfo(
					new HashMap<String, Object>() {
						{
			                put("jobName", jobName);
			                put("stepName", stepName);
			                put("itemType", itemType);
			                
			            }
					} 
				 );
    }
    
    
    @SuppressWarnings("serial")
	public void updateJobStepMapperExtrDt(String ridStep, Date condExtrStartDt, Date condExtrEndDt) {
    
	    mapper.updateJobStepMapperExtrDt(new HashMap<String, Object>() {	     
			{
			 put("ridStep", ridStep);
		     put("condExtrStartDt", condExtrStartDt);
	         put("condExtrEndDt", condExtrEndDt);
	        }
	  });
    
    }
    
}
