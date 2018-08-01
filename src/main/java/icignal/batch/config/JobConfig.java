package icignal.batch.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import icignal.batch.Listener.ProductExtractListener;


@Configuration
@EnableBatchProcessing
public class JobConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;
		
	@Autowired
	public StepConfig stepConfig;
	
	
	private static final Logger log = LoggerFactory.getLogger(JobConfig.class);
	
	
	/**
	 * 일별주문상품 집계 잡
	 * @return
	 * @throws Exception
	 */
	@Bean(name="jobOrderProdDaily")
	public Job jobOrderProdDaily() throws Exception {
		Job job = jobBuilderFactory.get("jobOrderProdDaily")
								   .incrementer(new RunIdIncrementer())
								   .start(stepConfig.stepTruncateTable("mrt.ch_ord_prod_daily_sum_stg"))
								 //  .next(stepConfig.callStep("stepOrderProdDailyExtract"))
								   .next(stepConfig.stepOrderProdDailyExtract())
								   .next(stepConfig.stepOrderProdDailyLoad())								   
								   .build();
	   /* 
		Map<String,JobParameter> parameters = new HashMap<>();
        JobParameter ccReportIdParameter = new JobParameter("03061980");
        parameters.put("ccReportId", ccReportIdParameter);

        jobLauncher.run(job, new JobParameters(parameters));
       */
		
		return job;
	}
	
	
	
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
	
	
	
	

	
	/**
	 * 회원정보 적재
	 * @return
	 * @throws Exception 
	 */
	/*@Bean(name="jobMember")
	public Job jobMember() throws Exception {
		Job job = jobBuilderFactory.get("jobMember")
								   .incrementer(new RunIdIncrementer())
								   .listener(new MemberListener(mapper))
								   .start(stepMemberExtract())
								   .next(stepMemberOtherAgreeExtract())
								   .next(stepMemberMobileAppInfoExtract())
								   .next(stepMemberLoad())
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
