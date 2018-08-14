package icignal.batch.config;



import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
/*	
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
	*/
	
	
	private Object stepCall(Map<String, Object> jobStep) {
		
		return null;
	}


	
	/*    
	Map<String,JobParameter> parameters = new HashMap<>();
    JobParameter ccReportIdParameter = new JobParameter("03061980");
    parameters.put("ccReportId", ccReportIdParameter);

    jobLauncher.run(job, new JobParameters(parameters));
   */

	/**
	 * 일별주문상품 집계 잡
	 * @return
	 * @throws Exception
	 */
	/*@Bean(name="jobOrderProdDaily")
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
		
				
	
		
		return job;
	}
	*/

	
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
//								   .start(stepConfig.stepTruncateTable("mrt.ch_mem_stg,mrt.ch_mem_other_agree_stg,mrt.ch_mobile_app_info_stg"))
//								   .start(stepConfig.stepTruncateTable())
//								   .next(stepConfig.stepMemberExtract())
//								   .next(stepConfig.stepMemberOtherAgreeExtract())
//								   .next(stepConfig.stepMemberMobileAppInfoExtract())
//								   .next(stepConfig.stepMemberLoad())
								   
								   .start(stepConfig.stepTruncateTableTasklet("stepMemberJobTruncTable"))
								   .next(stepConfig.stepItem("stepMemberExtract", readerB2C, writerIC))
								   .next(stepConfig.stepItem("stepMemberOtherAgreeExtract", readerB2C, writerIC)) 
								   .next(stepConfig.stepItem("stepMemberMobileAppInfoExtract", readerB2C, writerIC)) 
								   .next(stepConfig.stepStoredProcedureCallTasklet("stepMemberLoad"))
								   .build();		
		return job;
	}
	

	
	
/*	@Bean(name="jobProduct")
	public Job jobProduct() throws Exception {
		Job job = jobBuilderFactory.get("jobProduct")
								   .incrementer(new RunIdIncrementer())
//								   .start(stepConfig.stepTruncateTable("mrt.ch_prod_stg"))
//								   .next(stepConfig.stepProductExtract())
//								   .next(stepConfig.stepProductLoad())
								   .start(stepConfig.stepTruncateTableTasklet("stepProductJobTruncTable"))
								   .next(stepConfig.stepItem("stepProductExtract", readerB2C, writerIC))
								   .next(stepConfig.stepStoredProcedureCallTasklet("stepProductLoad"))								   
								   .build();
		return job;
	}
	*/
	
	
	/*
	 //등급
	@Bean(name="jobGrade")
	public Job jobGrade() throws Exception {
		Job job = jobBuilderFactory.get("jobGrade")
								   .incrementer(new RunIdIncrementer())
								// .listener(new GradeExtractListener(mapper)) // mrt.ch_grade_stg								   
								// .start(stepGradeExtract())	
								// .next(stepGradeLoad())
                                // .start(stepConfig.stepTruncateTableTasklet("mrt.ch_grade_stg"))
                                 
								   .start(stepConfig.stepTruncateTableTasklet("stepGradeJobTruncTable"))
								   .next(stepConfig.stepItem("stepGradeExtract", readerB2C, writerIC))
								   .next(stepConfig.stepItem("stepGradeLoad", readerIC, writerIC))
								   
								 //  .next(stepConfig.stepStoredProcedureCallTasklet("stepGradeLoad"))		
								 
								   .build();
		return job;
	}
	*/
	
/*	
   // 회원수신동의 집계
	@Bean(name="jobSumMemAgreeDaily")
	public Job jobSumMemAgreeDaily() throws Exception {
		Job job = jobBuilderFactory
				.get("jobSumMemAgreeDaily")
				.incrementer(new RunIdIncrementer())
				.start(stepConfig.stepStoredProcedureCallTasklet("stepSummaryMemberAgreeDailyMart"))
				.build();
		return job;
	}
*/
	
	
	
	
	/*
	 //일별 장바구니 (FromB2C)
	@Bean(name="jobShoppingCartDailyFromB2C")
	public Job jobShoppingCartDailyFromB2C() throws Exception {
		Job job = jobBuilderFactory.get("jobShoppingCartDailyFromB2C")
								   .incrementer(new RunIdIncrementer())
								   .start(stepConfig.stepItem("stepShoppingCartDaily", readerB2C, writerIC))
								   .build();
		return job;
	}
	*/
	
	
	
	
	/*
	 //일별무료샘플신청 (FromB2C)
	@Bean(name="jobSampleReqDailyFromB2C")
	public Job jobSampleReqDailyFromB2C() throws Exception {
		Job job = jobBuilderFactory.get("jobSampleReqDailyFromB2C")
								   .incrementer(new RunIdIncrementer())
								   .start(stepConfig.stepItem("stepSampleRequestDaily", readerB2C, writerIC))
								   .build();
		return job;
	}
	*/
	
	
	
	/*
	 //일별회원관심상품 (FromB2C)
	@Bean(name="jobInterestProductFromB2C")
	public Job jobInterestProductFromB2C() throws Exception {
		Job job = jobBuilderFactory.get("jobInterestProductFromB2C")
								   .incrementer(new RunIdIncrementer())
								   .start(stepConfig.stepItem("stepInterestProductDaily", readerB2C, writerIC))
								   .build();
		return job;
	}
	*/
	

	
	
	/*
	 //캠페인반응 (FromB2C)
	@Bean(name="jobCampResposeFromB2C")
	public Job jobCampResposeFromB2C() throws Exception {
		Job job = jobBuilderFactory.get("jobCampResposeFromB2C")
								   .incrementer(new RunIdIncrementer())
								   .start(stepConfig.stepItem("stepCampResponseDaily", readerB2C, writerIC))
								   .build();
		return job;
	}
	*/
	
	
	
	
	
	/*
	 //캠페인구매반응 (FromB2C)
	@Bean(name="jobCampOrdResponseFromB2C")
	public Job jobCampOrdResponseFromB2C() throws Exception {
		Job job = jobBuilderFactory.get("jobCampOrdResponseFromB2C")
								   .incrementer(new RunIdIncrementer())
								   .start(stepConfig.stepItem("stepCampOrdResponseDaily", readerB2C, writerIC))
								   .build();
		return job;
	}
	*/

	
	
	
	
	/**
	 * 장바구니 집계 마트(iCignal)
	 * @return
	 * @throws Exception
	 */
/*	@Bean(name="jobShoppingCartMrt")
	public Job jobShoppingCartMrt() throws Exception {
		Job job = jobBuilderFactory
				.get("jobShoppingCartMrt")
				.incrementer(new RunIdIncrementer())
				.start(stepConfig.stepStoredProcedureCallTasklet("stepSummaryShoppingCartMart"))
				.build();
		return job;
	}
*/
	
	
	
	/**
	 * 회원 마트(iCignal)
	 * @return
	 * @throws Exception
	 */
/*	@Bean(name="jobMemberMrt")
	public Job jobMemberMrt() throws Exception {
		Job job = jobBuilderFactory
				.get("jobMemberMrt")
				.incrementer(new RunIdIncrementer())
				.start(stepConfig.stepStoredProcedureCallTasklet("stepSummaryMemberMart"))
				.build();
		return job;
	}
*/
	
	
	/**
	 * 회원맞춤상품 집계 마트(iCignal)
	 * @return
	 * @throws Exception
	 */
	/*	@Bean(name="jobMemberFitProdMrt")
	public Job jobMemberFitProdMrt() throws Exception {
		Job job = jobBuilderFactory
				.get("jobMemberFitProdMrt")
				.incrementer(new RunIdIncrementer())
				.start(stepConfig.stepStoredProcedureCallTasklet("stepSummaryFitProdMart"))
				.build();
		return job;
	}
	 */
	
	
	/**
	 * 비구매관심상품 집계 마트(iCignal)
	 * @return
	 * @throws Exception
	 */
	/*	@Bean(name="jobInterestProdMartMrt")
	public Job jobProdSrchMrt() throws Exception {
		Job job = jobBuilderFactory
				.get("jobInterestProdMartMrt")
				.incrementer(new RunIdIncrementer())
				.start(stepConfig.stepStoredProcedureCallTasklet("stepSummaryInterestProdMart"))
				.build();
		return job;
	}
	 */
	
	
	
	
	
	
	/**
	 * 고객유형별구매상품(iCignal)
	 * @return
	 * @throws Exception
	 */
	/*	@Bean(name="jobCustProdSumMrt")
	public Job jobCustProdSumMrt() throws Exception {
		Job job = jobBuilderFactory
				.get("jobCustProdSumMrt")
				.incrementer(new RunIdIncrementer())
				.start(stepConfig.stepStoredProcedureCallTasklet("stepSummaryCustProdSumMart"))
				.build();
		return job;
	}
	 */
	
	
	
	/**
	 * 상품별구매(iCignal)
	 * @return
	 * @throws Exception
	 */
	/*	@Bean(name="jobProdOrdMrt")
	public Job jobProdOrdMrt() throws Exception {
		Job job = jobBuilderFactory
				.get("jobProdOrdMrt")
				.incrementer(new RunIdIncrementer())
				.start(stepConfig.stepStoredProcedureCallTasklet("stepSummaryProdOrdMart"))
				.build();
		return job;
	}
	 */
	
	
	
	
	
	
	
	
	
}
