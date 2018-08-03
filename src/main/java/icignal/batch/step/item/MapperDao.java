package icignal.batch.step.item;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import icignal.batch.icg.repository.ICGMapper;
import icignal.batch.util.ICNDateUtility;


public class MapperDao  {
    
	@Autowired
	public ICGMapper mapper;
	
	public final static String ITEM_WRITER = "ItemWriter";
	public final static String ITEM_READER = "ItemReader";
	public final static String SP_TASKLET = "SPTasklet";
	public final static String TRUNC_TASKLET = "TruncTasklet";
	
	
	
	
	private static final Logger log = LoggerFactory.getLogger(MapperDao.class);	
	
    public MyBatisBatchItemWriter<Map<String,Object>> writer( SqlSessionFactory sqlSessionFactory, 
    		 Map<String,Object> jobParameters,	String jobName, String stepName ) throws Exception {
    
    	System.out.println("writer...................");
    	 final  MyBatisBatchItemWriter<Map<String,Object>> writer = new  MyBatisBatchItemWriter<>();
    	 
    	// sqlSessionFactory.openSession().selectOne("commonRepository.now");

    	 
    	writer.setSqlSessionFactory(sqlSessionFactory);
    	
    	//writer.setStatementId("icignal.batch.icg.repository.ICGMapper.insertOrdProdDailySumStg");
    	writer.setStatementId((String)findStepInfo(jobName,stepName, ITEM_WRITER ).get("mapperId"));
    	System.out.println("writer...................end ");
		 return writer;
    }
	
	

	@SuppressWarnings("serial")
	public MyBatisCursorItemReader<Map<String,Object>> reader( SqlSessionFactory sqlSessionFactory, 
			 													Map<String,Object> jobParameters,	
			 													String jobName,
			 													String stepName ) throws Exception {
		
		   log.info("reader..............................!!!");
		   log.info("jobName:::: " + jobName);
		   log.info("StepName:::: " + stepName);
		   
		 //  sqlSessionFactory.openSession(ExecutorType.BATCH);
		   
		   Map<String, Object> stepInfo = findStepInfo(jobName,stepName, ITEM_READER);
		   
		   log.debug("stepInfo:: " +  stepInfo);
		   
			String mapperId = (String)stepInfo.get("mapperId");
			Date startDt = (Date)stepInfo.get("condExtrStartDt");
			Date endDt = (Date)stepInfo.get("condExtrEndDt");

			log.info("############추출조건 기간##########");
			log.info(ICNDateUtility.getFormattedDate(startDt, ICNDateUtility.yyyyMMdd ) +" ~ " 
					 + ICNDateUtility.getFormattedDate(endDt, ICNDateUtility.yyyyMMdd ));
			log.info("############추출조건 기간##########");


			log.info("mapperId: " + mapperId);
		 	
			log.info("sqlSessionFactory: " + sqlSessionFactory);
			
			 final MyBatisCursorItemReader<Map<String,Object>> reader = new MyBatisCursorItemReader<>();
			 
	//		 sqlSessionFactory.openSession(ExecutorType.BATCH);
			 
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
    
	
	public void truncateTable(String jobName, String stepName) {
		   Map<String, Object> stepInfo =  findStepInfo(jobName, stepName, TRUNC_TASKLET);
		  String mapperParam = (String)stepInfo.get("mapperParam");
		  List<String> tableNames = Arrays.asList(mapperParam.split(","));
		  for(String item : tableNames)  mapper.truncateTable(item);
	}
	
    
    
    
    @SuppressWarnings("serial")
	public Map<String, Object> findStepInfo(String jobName, String stepName, String itemType){
    	
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
