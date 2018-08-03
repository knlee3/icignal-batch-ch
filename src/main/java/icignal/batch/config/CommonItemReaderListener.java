package icignal.batch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;

import icignal.batch.step.item.MapperDao;



public class CommonItemReaderListener extends MapperDao implements ItemReadListener<Object> {
	
	private static final Logger log = LoggerFactory.getLogger(CommonStepExecutionListener.class);
	
	
	private final String stepName;

	public CommonItemReaderListener(String stepName) {
		
		this.stepName = stepName;
		
	}

	@Override
	public void beforeRead() {
		log.info(this.getClass().getName() + " beforeRead......");
	}

	@Override
	public void afterRead(Object item) {
		
		log.info(this.getClass().getName() + "  afterRead......");
		log.info("item: " + item);
		
		//String jobName = stepExecution.getJobExecution().getJobInstance().getJobName();
		//String stepName = stepExecution.getStepName();
	 //   log.info("jobName: " + jobName + " StempName: " +   stepName + " afterStep start...");
	//	log.info("stepExecution.getExitStatus(): " + stepExecution.getExitStatus());
		
		
/*		Map<String, Object> stepInfo = mapper.findStepByStepId(  new HashMap<String, Object>() {	     
				{
	             put("stepId", stepName);
	            }
		 	});
*//*
		Map<String, Object> stepInfo =  findStepInfo(this.stepName, "ItemReader");
		 
		String condExtrApplyYn = (String)stepInfo.get("condExtrApplyYn");

		//해당 스텝이 추출조건 대상인지 체크
		if(ICNStringUtility.isUpperCaseEquals("Y", condExtrApplyYn)) {
			  mapper.updateStepExtrDt(new HashMap<String, Object>() {	     
					{
					 put("stepId", stepName);
				     put("condExtrStartDt", null);
		             put("condExtrEndDt", null);
		            }
			  });
		}*/

	//	log.info( "Read Count: " + stepExecution.getReadCount());
		
		
		
	}

	@Override
	public void onReadError(Exception ex) {
		// TODO Auto-generated method stub
		
	}



}
