package icignal.batch.job;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import icignal.batch.config.StepConfig;
import icignal.batch.icg.repository.ICGMapper;
import icignal.batch.util.ICNStringUtility;

public class IcignalBatchCommonJob {
	
	// @Autowired
	public StepConfig stepConfig;
	
//	@Autowired
	public ItemWriter<Map<String,Object>> writerIC;
	
	
//	@Autowired
	public ItemReader<Map<String, Object>> readerB2C;
	
	
	// @Autowired
	public ItemReader<Map<String, Object>> readerIC;
	
	// @Autowired
	public ICGMapper mapper;
	
	public JobBuilderFactory jobBuilderFactory;
	
	public Job callSimpleJobProc(String jobName) throws Exception {
		
		  JobBuilder jobBuilder = jobBuilderFactory.get(jobName).incrementer(new RunIdIncrementer());
		  
		  List<Map<String, Object>> stepInfoList = findJobStepInfo(jobName); 
		  
		  SimpleJobBuilder  sjb = null;
		  try {
			  for(Map<String, Object> step : stepInfoList) {
				  Step stepObj = null;
				  int seq =	(int)step.get("stepSeq");
					
				  String stepMethodNm  =	(String)step.get("stepMethodNm");
				  String stepNm  =	(String)step.get("stepNm");
				  System.out.println("stepMethodNm: " + stepMethodNm + "\t" + "stepNm: " + stepNm );
				  String itemReaderNm  =	(String)step.get("itemReaderNm");
			      String itemWriterNm  =	(String)step.get("itemWriterNm");
			      String stepClassFieldNm  =	(String)step.get("stepClassFieldNm");
				  
			      if(ICNStringUtility.isNotEmptyAll(itemReaderNm, itemWriterNm)) {
			    	  Method method = this.getClass().getField(stepClassFieldNm).get(this).getClass().getMethod(stepMethodNm, String.class, Object.class, Object.class);
						stepObj = (Step)method.invoke( this.getClass().getField(stepClassFieldNm).get(this) , stepNm, 
								this.getClass().getField(itemReaderNm).get(this),
								this.getClass().getField(itemWriterNm).get(this) );
			    	  
			      }else {
			    	  Method method = this.getClass().getField(stepClassFieldNm).get(this).getClass().getMethod(stepMethodNm,  String.class);
						//	stepObj = (Step)method.invoke( stepConfig , stepNm );
							stepObj = (Step)method.invoke( this.getClass().getField(stepClassFieldNm).get(this) , stepNm );
			    	  
			      }
			      
				if(seq == 1)	sjb = jobBuilder.start(stepObj); 
				else sjb = sjb.next(stepObj);
				
			  }
		  }catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			return	   sjb.build();
	}
	

	
	 @SuppressWarnings("serial")
	public Map<String, Object> findJobInfo(String jobName) {
		 
		 List<Map<String, Object>> jobs = mapper.findJobInfo(new HashMap<String, Object>() {
				{
	                put("jobName", jobName);
	            }
			}
				);
		 
		 return jobs.stream().findFirst().get();
	 }
	 
	 
	 @SuppressWarnings("serial")
	public List<Map<String, Object>> findJobStepInfo(String jobName) {
		 
		 List<Map<String, Object>> jobStepList = mapper.findJobStepInfo(new HashMap<String, Object>() {
				{
	                put("jobName", jobName);
	            }
			}
				);
		 
		 return jobStepList;
	 }
	
}
