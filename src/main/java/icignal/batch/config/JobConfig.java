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
	

	/*
	
	@Bean(name="jobProduct")
	public Job jobProduct() throws Exception {
		Job job = jobBuilderFactory.get("jobProduct")
								   .incrementer(new RunIdIncrementer())
								//   .listener(new ProductExtractListener(mapper))
								   .start(stepConfig.stepTruncateTable("mrt.ch_prod_stg"))
								   .next(stepConfig.stepProductExtract())
								   .next(stepConfig.stepProductLoad())
								   .build();
		return job;
	}
	
	*/
	
	

	
	
	/*
	@Bean(name="jobGrade")
	public Job jobGrade() throws Exception {
		Job job = jobBuilderFactory.get("jobGrade")
								   .incrementer(new RunIdIncrementer())
								   .listener(new GradeExtractListener(mapper))
								   .start(stepGradeExtract())	
								   .next(stepGradeLoad())
								   .build();
		return job;
	}
	*/
	
	/*	
	@Bean(name="jobSumMemAgreeDaily")
	public Job jobSumMemAgreeDaily() throws Exception {
		Job job = jobBuilderFactory.get("jobSumMemAgreeDaily")
								   .incrementer(new RunIdIncrementer())
                                   .start(stepSumMemAgreeDaily())
								   .build();
		return job;
	}
	*/
	

	
	
	/*
	@Bean(name="jobMonthPntExtncCust")
	public Job jobMonthPntExtncCust() throws Exception {
		Job job = jobBuilderFactory.get("jobMonthPntExtncCust")
								   .incrementer(new RunIdIncrementer())
								   .listener(new MonthPntExtncCustListener(mapper))
								   .start(stepMonthPntExtncCust())
								   .build();
		return job;
	}
	*/
	
	
	
	
	
	
	
}
