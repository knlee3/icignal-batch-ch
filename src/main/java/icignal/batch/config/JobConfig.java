package icignal.batch.config;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import icignal.batch.step.item.MapperDao;

@Configuration
@EnableBatchProcessing
public class JobConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;
		
	@Autowired
	public StepConfig stepConfig;
	
	
	private static final Logger log = LoggerFactory.getLogger(JobConfig.class);
	
	
	@Autowired
	public ItemWriter<Map<String,Object>> writerIC;
	
	
	@Autowired
	public ItemReader<Map<String, Object>> readerB2C;
	
	
	@Autowired
	public ItemReader<Map<String, Object>> readerIC;
	
	@SuppressWarnings({ "serial", "unchecked" })
	@Bean(name="jobCommon")
	public Job jobCommon() throws Exception {
		Job job = null;
		String jobName = "jobOrderProdDaily";
		JobBuilder jobBuilder = jobBuilderFactory.get(jobName);
		jobBuilder =  jobBuilder.incrementer(new RunIdIncrementer());
		SimpleJobBuilder sjb =  null;
		//jobBuilder.start(flow)
		
		MapperDao mapper = new MapperDao();
		List<Map<String,Object>> jobStepList =	(List<Map<String, Object>>) mapper.findJobStepInfo(
													new HashMap<String, Object>() {
														{
											                put("jobName", jobName);
											            }
														} );
		
		for(Map<String, Object> jobStep : jobStepList) {
		  int stepSeq =	(int)jobStep.get("stepSeq");
		 // String stepNm = (String)jobStep.get("stepNm");
		  if(stepSeq == 1)  sjb = jobBuilder.start((Step) stepCall(jobStep));
		  else sjb.next((Step)stepCall(jobStep));
			
		}
		
		sjb.build();

		
		return job;
	}
	
	
	
	private Object stepCall(Map<String, Object> jobStep) {
		
		return null;
	}



	/**
	 * 일별주문상품 집계 잡
	 * @return
	 * @throws Exception
	 */
	@Bean(name="jobOrderProdDaily")
	public Job jobOrderProdDaily() throws Exception {
		Job job = jobBuilderFactory.get("jobOrderProdDaily")
								   .incrementer(new RunIdIncrementer())
								//   .start(stepConfig.stepTruncateTable())
							//	   .next(stepConfig.stepOrderProdDailyExtract())
								//   .next(stepConfig.stepOrderProdDailyLoad())
								   .start(stepConfig.stepTruncateTableTasklet("stepOrderProdDailyJobTruncTable"))
								   .next(stepConfig.stepItem("stepOrderProdDailyExtract", readerB2C, writerIC)) 
								   .next(stepConfig.stepStoredProcedureCallTasklet("stepOrderProdDailyLoad")) 
								   .build();
		
				
	/*    
		Map<String,JobParameter> parameters = new HashMap<>();
        JobParameter ccReportIdParameter = new JobParameter("03061980");
        parameters.put("ccReportId", ccReportIdParameter);

        jobLauncher.run(job, new JobParameters(parameters));
       */
		
		return job;
	}
	

	
	/**
	 * 회원정보 적재
	 * @return
	 * @throws Exception 
	 */
	
	@Bean(name="jobMember")
	public Job jobMember() throws Exception {
		Job job = jobBuilderFactory.get("jobMember")
								   .incrementer(new RunIdIncrementer())
								//   .listener(new MemberListener(mapper))
//								   .start(stepConfig.stepTruncateTable("mrt.ch_mem_stg:mrt.ch_mem_other_agree_stg:mrt.ch_mobile_app_info_stg"))
/*								   .start(stepConfig.stepTruncateTable())
								   .next(stepConfig.stepMemberExtract())
								   .next(stepConfig.stepMemberOtherAgreeExtract())
								   .next(stepConfig.stepMemberMobileAppInfoExtract())
								   .next(stepConfig.stepMemberLoad())
*/								   
								   .start(stepConfig.stepTruncateTableTasklet("stepMemberJobTruncTable"))
								   .next(stepConfig.stepItem("stepMemberExtract", readerB2C, writerIC))
								   .next(stepConfig.stepItem("stepMemberOtherAgreeExtract", readerB2C, writerIC)) 
								   .next(stepConfig.stepItem("stepMemberMobileAppInfoExtract", readerB2C, writerIC)) 
								   .next(stepConfig.stepStoredProcedureCallTasklet("stepOrderProdDailyLoad"))
								   .build();		
		return job;
	}
	

	
	
	@Bean(name="jobProduct")
	public Job jobProduct() throws Exception {
		Job job = jobBuilderFactory.get("jobProduct")
								   .incrementer(new RunIdIncrementer())
								 /*  .start(stepConfig.stepTruncateTable("mrt.ch_prod_stg"))
								   .next(stepConfig.stepProductExtract())
								   .next(stepConfig.stepProductLoad())*/
								   .start(stepConfig.stepTruncateTableTasklet("stepProductJobTruncTable"))
								   .next(stepConfig.stepItem("stepProductExtract", readerB2C, writerIC))
								   .next(stepConfig.stepStoredProcedureCallTasklet("stepProductLoad"))								   
								   .build();
		return job;
	}
	
	
	@Bean(name="jobGrade")
	public Job jobGrade() throws Exception {
		Job job = jobBuilderFactory.get("jobGrade")
								   .incrementer(new RunIdIncrementer())
							//	   .listener(new GradeExtractListener(mapper)) // mrt.ch_grade_stg								   
								//   .start(stepGradeExtract())	
								   //.next(stepGradeLoad())
								   .start(stepConfig.stepTruncateTableTasklet("mrt.ch_grade_stg"))
								   .next(stepConfig.stepItem("stepGradeExtract", readerB2C, writerIC))
								   .next(stepConfig.stepStoredProcedureCallTasklet("stepGradeLoad"))		
								 
								   .build();
		return job;
	}
	
	
	
	@Bean(name="jobSumMemAgreeDaily")
	public Job jobSumMemAgreeDaily() throws Exception {
		Job job = jobBuilderFactory
				.get("jobSumMemAgreeDaily")
				.incrementer(new RunIdIncrementer())
                //.start(stepSumMemAgreeDaily())
				.start(stepConfig.stepStoredProcedureCallTasklet("stepSumMemAgreeDaily"))
				.build();
		return job;
	}
	
	
	
	/**
	 * 당월적립금 소멸 예정 고객
	 * @return
	 * @throws Exception
	 */
	@Bean(name="jobMonthPntExtncCust")
	public Job jobMonthPntExtncCust() throws Exception {
		Job job = jobBuilderFactory
				.get("jobMonthPntExtncCust")
				.incrementer(new RunIdIncrementer())				 
			//	.listener(new MonthPntExtncCustListener(mapper))  // mrt.ch_month_pnt_extnc_cust
			//	.start(stepMonthPntExtncCust())
				.start(stepConfig.stepTruncateTableTasklet("mrt.ch_month_pnt_extnc_cust"))
				.next(stepConfig.stepItem("stepMonthPntExtncCust", readerB2C, writerIC))
				.build();
		return job;
	}
	
	
	
	
	
	
	
	
}
