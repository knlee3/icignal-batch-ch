package icignal.batch.job;

import java.lang.reflect.Method;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import icignal.batch.config.StepConfig;
import icignal.batch.icg.repository.ICGMapper;
import icignal.batch.job.BatchHelper.CronTriggerFactoryBeanBuilder;
import icignal.batch.job.BatchHelper.JobDetailFactoryBeanBuilder;
import icignal.batch.util.ICNStringUtility;

@Configuration
public class IcignalBatchJob extends IcignalBatchCommonJob {
	
	
	private static final Logger log = LoggerFactory.getLogger(IcignalBatchJob.class);	
	

    @Autowired
    public IcignalBatchJob(JobBuilderFactory jobBuilderFactory,  StepConfig stepConfig, 
    		ItemReader<Map<String, Object>> readerB2C,
    		ItemReader<Map<String, Object>> readerIC,
    		ItemWriter<Map<String,Object>> writerIC,
    		ICGMapper mapper
    		) {
		        super.jobBuilderFactory = jobBuilderFactory;
		        super.readerB2C = readerB2C;
		        super.readerIC = readerIC;
		        super.writerIC = writerIC;
		        super.stepConfig = stepConfig;
		        super.mapper = mapper;
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
		
	  //Job job =	callSimpleJobProc("jobMember");
	/*	
	Job	job = jobBuilderFactory.get("jobMember")
								   .incrementer(new RunIdIncrementer())
								   .start(stepConfig.stepTruncateTableTasklet("stepMemberJobTruncTable"))
								   .next(stepConfig.stepItem("stepMemberExtract", readerB2C, writerIC))
								   .next(stepConfig.stepItem("stepMemberOtherAgreeExtract", readerB2C, writerIC)) 
								   .next(stepConfig.stepItem("stepMemberMobileAppInfoExtract", readerB2C, writerIC)) 
								   .next(stepConfig.stepStoredProcedureCallTasklet("stepMemberLoad"))
								   .build();*/
	/*	StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement e = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = e.getMethodName();
	    System.out.println("methodName::::::::::::: " + methodName);*/
		
		return buildSimpleJob(Thread.currentThread().getStackTrace()[1].getMethodName());
	  
	}
	
	
	/* 1:초(Seconds), 2:분(Minutes),  3:시(Hours), 4:일(Day-of-Month), 5:월(Months), 6:요일(Days-of-Week), 7:연도(Year) - optional  */
	 
    @Bean
    public CronTriggerFactoryBean jobMemberTrigger() throws Exception {
      /*  return BatchHelper.cronTriggerFactoryBeanBuilder()
                .cronExpression((String)findJobInfo("jobMember").get("execCycle"))
                .name((String)findJobInfo("jobMember").get("jobNm"))  
                .jobDetailFactoryBean(jobMemberJobDetail())                
                .build();
        */    	
    	 return buildCronTrigger( ICNStringUtility.getStringLastCut(Thread.currentThread().getStackTrace()[1].getMethodName(), "Trigger") );
    }

  /*  @Bean
    public JobDetailFactoryBean jobMemberJobDetail() throws Exception   {
        return BatchHelper.jobDetailFactoryBeanBuilder()
                .job(jobMember())
                .build();
    }*/
	
		
	 //등급
	@Bean(name="jobGrade")
	public Job jobGrade() throws Exception {
	/*	Job job = jobBuilderFactory.get("jobGrade")
								   .incrementer(new RunIdIncrementer())
								   .start(stepConfig.stepTruncateTableTasklet("stepGradeJobTruncTable"))
								   .next(stepConfig.stepItem("stepGradeExtract", readerB2C, writerIC))
								   .next(stepConfig.stepItem("stepGradeLoad", readerIC, writerIC))
								   .build();*/
		return buildSimpleJob("jobGrade");
	}	
	
	
	  @Bean
	  public CronTriggerFactoryBean jobGradeTrigger() throws Exception {
	/*	  Map<String, Object>  jobInfo = findJobInfo("jobGrade");
		  String jobName = (String)jobInfo.get("jobNm");
		  String execCycle = (String)jobInfo.get("execCycle");		  */
	     /* return BatchHelper.cronTriggerFactoryBeanBuilder()
	             .cronExpression(execCycle)
	             .name(jobName)      
	             .jobDetailFactoryBean(jobGradeJobDetail())	                
	             .build();*/
	/*	  
		  CronTriggerFactoryBeanBuilder ctfbb =  BatchHelper.cronTriggerFactoryBeanBuilder();
		  ctfbb = ctfbb.cronExpression(execCycle).name(jobName);
		  String buildJobDetail = "buildJobDetail";
		  
		  
		  Method method = this.getClass().getMethod(buildJobDetail, String.class);
		  JobDetailFactoryBean jdfb   = (JobDetailFactoryBean)method.invoke( this , jobName);
		  ctfbb.jobDetailFactoryBean(jdfb);
		  CronTriggerFactoryBean ctfb = ctfbb.build();*/
		       
		 return buildCronTrigger("jobGrade"); 
	  }

	  
	  /*	
		@Bean(name="jobProduct")
		public Job jobProduct() throws Exception {
			Job job = jobBuilderFactory.get("jobProduct")
									   .incrementer(new RunIdIncrementer())
									   .start(stepConfig.stepTruncateTableTasklet("stepProductJobTruncTable"))
									   .next(stepConfig.stepItem("stepProductExtract", readerB2C, writerIC))
									   .next(stepConfig.stepItem("stepProductLoad", readerIC, writerIC))								   
									   .build();
			return job;
		}
	*/
	
	 /**
	  * 상품마스터 Job
	  * @return Job
	  * @throws Exception
	  */
/*	@Bean(name="jobProduct")
	public Job jobProduct() throws Exception {
		return  buildSimpleJob("jobProduct");
	}
*/
	 /**
	  * Job 상품마스터 Triger
	  * @return
	  * @throws Exception
	  */
	/* @Bean
	 public CronTriggerFactoryBean jobProductTrigger() throws Exception {
		 return buildCronTrigger("jobProduct"); 
	  }
*/
	
	
	
	
/*
    // 회원수신동의 집계
	@Bean(name="jobMeberAgreeSumMrt")
	public Job jobMeberAgreeSumMrt() throws Exception {
		Job job = jobBuilderFactory
				.get("jobMeberAgreeSumMrt")
				.incrementer(new RunIdIncrementer())
				.start(stepConfig.stepStoredProcedureCallTasklet("stepSummaryMemberAgreeDailyMart"))
				.build();
		return job;
	}

	*/
	  
	  
	/**
	 *  일별 회원수신동의 집계
	 * @return
	 * @throws Exception
	 */
	/*@Bean(name="jobMeberAgreeSumMrt")
	public Job jobMeberAgreeSumMrt() throws Exception {
		return  buildSimpleJob("jobMeberAgreeSumMrt");
	}*/
	
	/**
	 * 일별 회원 수신동의 집계 트리거
	 * @return CronTriggerFactoryBean
	 * @throws Exception
	 */
	/* @Bean
	 public CronTriggerFactoryBean jobMeberAgreeSumMrtTrigger() throws Exception {
		 return buildCronTrigger("jobProduct"); 
	  }
*/
	
	
	
	/*
	
	 //일별 장바구니 (FromB2C)
	@Bean(name="jobShoppingCartDailyFromB2C")
	public Job jobShoppingCartDailyFromB2C() throws Exception {
		Job job = jobBuilderFactory.get("jobShoppingCartDailyFromB2C")
								   .incrementer(new RunIdIncrementer())
								   .start(stepConfig.stepTruncateTableTasklet("stepShoppingCartDailyTruncateTable"))
								   .next(stepConfig.stepItem("stepShoppingCartDaily", readerB2C, writerIC))
								   .build();
		return job;
	}
	
	*/
	 
	 
	    /**
	     * 일별장바구니 데이터 수집(FromB2C)
	     * @return
	     * @throws Exception
	     */
	/*	@Bean(name="jobShoppingCartDailyFromB2C")
		public Job jobShoppingCartDailyFromB2C() throws Exception {
			return  buildSimpleJob("jobShoppingCartDailyFromB2C");
		}
		*/
	  
	  /*
		 @Bean
		 public CronTriggerFactoryBean jobShoppingCartDailyFromB2CTrigger() throws Exception {
			 return buildCronTrigger("jobShoppingCartDailyFromB2C"); 
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
	}*/
	 
	
	
	/**
	 * 비구매관심상품 집계 마트(iCignal)
	 * @return
	 * @throws Exception
	 */
/*	@Bean(name="jobInterestProdMartMrt")
	public Job jobInterestProdMartMrt() throws Exception {
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
	/*@Bean(name="jobCustProdSumMrt")
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
	/*@Bean(name="jobProdOrdMrt")
	public Job jobProdOrdMrt() throws Exception {
		Job job = jobBuilderFactory
				.get("jobProdOrdMrt")
				.incrementer(new RunIdIncrementer())
				.start(stepConfig.stepStoredProcedureCallTasklet("stepSummaryProdOrdMart"))
				.build();
		return job;
	}*/
	
	
	
	
}
