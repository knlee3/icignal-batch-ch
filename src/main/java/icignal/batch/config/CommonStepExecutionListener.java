package icignal.batch.config;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;


import icignal.batch.step.item.MapperDao;
import icignal.batch.util.ICNDateUtility;
import icignal.batch.util.ICNStringUtility;


@Component
public class CommonStepExecutionListener extends MapperDao  implements StepExecutionListener{

	private static final Logger log = LoggerFactory.getLogger(CommonStepExecutionListener.class);
	
	// @Autowired ICGMapper mapper;
	
	@SuppressWarnings("serial")
	@Override
	public void beforeStep(StepExecution stepExecution) {
		String jobName = stepExecution.getJobExecution().getJobInstance().getJobName();
		String stepName = stepExecution.getStepName();
	    log.info("jobName: " + jobName + " StempName: " +   stepName + " beforeStep start...");
	
	   Map<String, Object>  stepInfo = findJobStepMapperInfo(jobName, stepName, ITEM_READER);
	   
	   if(stepInfo != null) {
		  String condExtrApplyYn = (String)stepInfo.get("condExtrApplyYn");
		  //해당 스텝이 추출조건 대상인지 체크
		  if(ICNStringUtility.isUpperCaseEquals("Y", condExtrApplyYn)) {
			  log.info(stepName + " condExtrApplyYn : "+ condExtrApplyYn );	  	
			  if(stepInfo.get("condExtrStartDt") == null ) { 
					  
				   if(ICNStringUtility.isUpperCaseEquals("DAY",(String)stepInfo.get("condExtrUnit"))) {
						  int condExtrBaseDt = stepInfo.get("condExtrBaseDt") == null ? 0 : (int)stepInfo.get("condExtrBaseDt"); // 추출조건 기준일. 0: new Date()
						  int condExtrTerm = stepInfo.get("condExtrTerm") == null ? 0 : (int)stepInfo.get("condExtrTerm");  // 추출조건 기간
						  Date condExtrEndDt = ICNDateUtility.addDay(ICNDateUtility.asDate(LocalDate.now()), condExtrBaseDt);  // 추출조건 종료일
						  Date condExtrStartDt = ICNDateUtility.addDay(condExtrEndDt, condExtrTerm); // 추출조건 시작일
						  log.info("endDt: "+  ICNDateUtility.getFormattedShortDate(condExtrEndDt));
						  log.info("startDt: "+  ICNDateUtility.getFormattedShortDate(condExtrStartDt));
						  updateJobStepMapperExtrDt((String)stepInfo.get("ridStep"), condExtrStartDt, condExtrEndDt);
				   }
			  } 
		  }
		}
	}
	
	@SuppressWarnings("serial")
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		
		String jobName = stepExecution.getJobExecution().getJobInstance().getJobName();
		String stepName = stepExecution.getStepName();
	    log.info("jobName: " + jobName + " StempName: " +   stepName + " afterStep start...");
	//	log.info("stepExecution.getExitStatus(): " + stepExecution.getExitStatus());
		
		ExitStatus status =  stepExecution.getExitStatus();
		
		Map<String, Object> stepInfo = findJobStepMapperInfo(jobName,stepName, ITEM_READER);
		
		if(stepInfo != null &&  ICNStringUtility.isUpperCaseEquals("Y",  (String)stepInfo.get("condExtrApplyYn"))) {
			updateJobStepMapperExtrDt((String)stepInfo.get("ridStep"), null, null);
		}

		log.info( "Data Read Count: " + stepExecution.getReadCount());
		
		return stepExecution.getExitStatus();
	}

	
	
	
}
