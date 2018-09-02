package icignal.batch.job;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;



import icignal.batch.config.StepConfig;
import icignal.batch.icg.repository.ICGMapper;
import icignal.batch.step.item.CommonItemReader;
import icignal.batch.step.item.CommonItemWriter;
import icignal.batch.util.ICNStringUtility;



@Configuration
public class IcignalBatchJob {
	
	
	private static final Logger log = LoggerFactory.getLogger(IcignalBatchJob.class);	
	
	public JobBuilderFactory jobBuilderFactory;
		
	// @Autowired
	public StepConfig stepConfig;
	
//	@Autowired
	public ItemWriter<Map<String,Object>> writerIC;
	
	
//	@Autowired
	public ItemReader<Map<String, Object>> readerB2C;
	
	
	@Autowired
	public ItemReader<Map<String, Object>> readerIC;
	
	
    @Autowired
    public IcignalBatchJob(JobBuilderFactory jobBuilderFactory,  StepConfig stepConfig, ItemReader<Map<String, Object>> readerB2C,
    		ItemWriter<Map<String,Object>> writerIC ) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.readerB2C = readerB2C;
        this.writerIC = writerIC;
        this.stepConfig = stepConfig;
        
    }


	

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
	
	
	/*private Object stepCall(Map<String, Object> jobStep) {
		
		return null;
	}*/


	
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

    @Autowired
    private ApplicationContext appContext;
	
	/**
	 * 회원정보 적재
	 * @return
	 * @throws Exception 
	 */	
	@Bean(name="jobMember")	
	public Job jobMember() throws Exception {
		
	  Job job =	callJobProc("jobMember");
//	  System.out.println("job::::::::::::: " + job);
	/*	
	Job	job = jobBuilderFactory.get("jobMember")
								   .incrementer(new RunIdIncrementer())
								   .start(stepConfig.stepTruncateTableTasklet("stepMemberJobTruncTable"))
								   .next(stepConfig.stepItem("stepMemberExtract", readerB2C, writerIC))
								   .next(stepConfig.stepItem("stepMemberOtherAgreeExtract", readerB2C, writerIC)) 
								   .next(stepConfig.stepItem("stepMemberMobileAppInfoExtract", readerB2C, writerIC)) 
								   .next(stepConfig.stepStoredProcedureCallTasklet("stepMemberLoad"))
								   .build();*/
		return job;
	  

	  

	}

	
	public Job callJobProc(String jobName) throws Exception {
		
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
				  if(ICNStringUtility.isEquals(stepMethodNm, "stepItem") ) {
				//	stepObj =  stepConfig.stepItem(stepNm, this.getClass().getField((String)step.get("itemReaderNm")).get(this), this.getClass().getField((String)step.get("itemWriterNm")).get(this));
					Method method = Class.forName("icignal.batch.config.StepConfig").getMethod(stepMethodNm, String.class, Object.class, Object.class);
					stepObj = (Step)method.invoke( stepConfig , stepNm, this.getClass().getField((String)step.get("itemReaderNm")).get(this), this.getClass().getField((String)step.get("itemWriterNm")).get(this) );
				  }else {				  
					Method method = Class.forName("icignal.batch.config.StepConfig").getMethod(stepMethodNm,  String.class);
					stepObj = (Step)method.invoke( stepConfig , stepNm );

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
	
	
	
	
	
	@SuppressWarnings({ "unchecked"})	
	public Job callJob(String jobName)  throws Exception {
		System.out.println("callJob.......................start");
		
//	    JobBuilder jobBuilder =	 jobBuilderFactory.get("jobMember").incrementer(new RunIdIncrementer());
	    JobBuilder jobBuilder =	 jobBuilderFactory.get(jobName).incrementer(new RunIdIncrementer());
		List<Map<String, Object>> stepInfoList = findJobStepInfo(jobName); 
		
		System.out.println("stepInfoList.size(): " + stepInfoList.size());
		SimpleJobBuilder  sjb  = null;
		try {
		for(Map<String, Object> step : stepInfoList) {
		
			int seq =	(int)step.get("stepSeq");
			
				String stepMethodNm  =	(String)step.get("stepMethodNm");
			System.out.println("stepMethodNm: " + stepMethodNm);	
				
				String stepNm  =	(String)step.get("stepNm");
				
				Step stepObj  = null;
			
				if(ICNStringUtility.isEquals(stepMethodNm, "stepItem") ) {
					System.out.println("aaaaaaaaaaaaaaaaaaa!!!!");
					String itemReaderNm  =	(String)step.get("itemReaderNm");
					String itemWriterNm  =	(String)step.get("itemWriterNm");
					System.out.println("itemReaderNm: " + itemReaderNm);
					System.out.println("itemWriterNm: " + itemWriterNm);
					ItemReader<Map<String, Object>> itemReader = (ItemReader<Map<String, Object>>)appContext.getBean(itemReaderNm);
					System.out.println("itemReader: " + itemReader);
					ItemWriter<Map<String, Object>> itemWriter = (ItemWriter<Map<String, Object>>)appContext.getBean(itemWriterNm);
					System.out.println("itemWriter: " + itemWriter);
					
					
					stepObj = stepConfig.stepItem(stepNm, readerB2C, writerIC); 
//					Method method = Class.forName("icignal.batch.config.StepConfig").getMethod(stepMethodNm, readerB2C.getClass(), writerIC.getClass() );
				//	Method method = Class.forName("icignal.batch.config.StepConfig").getMethod(stepMethodNm, CommonItemReader.class, CommonItemWriter.class );
			//		System.out.println("method:::::::::: " + method);
					
			//		stepObj = (Step)method.invoke( stepConfig, stepNm , "",  "");
				}else {
					System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbb!!!");
					Method method = Class.forName("icignal.batch.config.StepConfig").getMethod(stepMethodNm,  String.class);
					System.out.println("method::: " + method);
					stepObj = (Step)method.invoke( stepConfig , stepNm );
					System.out.println("stepObj: " + stepObj);
				}
				
				if(seq == 1)	sjb = jobBuilder.start(stepObj);
				else sjb = sjb.next(stepObj);
	    
		}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		System.out.println("callJob.......................end");
	   
		return sjb.build();
		
	}
	
	
	/* 1:초(Seconds), 2:분(Minutes),  3:시(Hours), 4:일(Day-of-Month), 5:월(Months), 6:요일(Days-of-Week), 7:연도(Year) - optional  */
	 
    @Bean
    public CronTriggerFactoryBean jobMemberTrigger() throws Exception {
    	//  Map<String, Object>  jobInfo = findJobInfo("jobMember");
		//  String jobName = (String)jobInfo.get("jobNm");
		//  String execCycle = (String)jobInfo.get("execCycle");
		  
		/*  log.info("jobName::::::::: "  + jobName);
		  System.out.println("execCycle::::::::: "  + execCycle);
    	*/
        return BatchHelper.cronTriggerFactoryBeanBuilder()
                .cronExpression((String)findJobInfo("jobMember").get("execCycle"))
                .name((String)findJobInfo("jobMember").get("jobNm"))  
                .jobDetailFactoryBean(jobMemberJobDetail())                
                .build();
    }

    @Bean
    public JobDetailFactoryBean jobMemberJobDetail() throws Exception   {
        return BatchHelper.jobDetailFactoryBeanBuilder()
                .job(jobMember())
                .build();
    }
	
	

	

		
	 //등급
	@Bean(name="jobGrade")
	public Job jobGrade() throws Exception {
		Job job = jobBuilderFactory.get("jobGrade")
								   .incrementer(new RunIdIncrementer())
								   .start(stepConfig.stepTruncateTableTasklet("stepGradeJobTruncTable"))
								   .next(stepConfig.stepItem("stepGradeExtract", readerB2C, writerIC))
								   .next(stepConfig.stepItem("stepGradeLoad", readerIC, writerIC))
								   .build();
		return job;
	}
	
	
	
	@Autowired
	public ICGMapper mapper;
	
	
	
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
	
	
	
	
	  @Bean
	  public CronTriggerFactoryBean jobGradeTrigger() throws Exception {
		  Map<String, Object>  jobInfo = findJobInfo("jobGrade");
		  String jobName = (String)jobInfo.get("jobNm");
		  String execCycle = (String)jobInfo.get("execCycle");

		  
	        return BatchHelper.cronTriggerFactoryBeanBuilder()
//	                .cronExpression("0 0/1 * 1/1 * ? *")
	                .cronExpression(execCycle)
	                .name(jobName)      
	                .jobDetailFactoryBean(jobGradeJobDetail())	                
	                .build();
	  }

	  @Bean
	  public JobDetailFactoryBean jobGradeJobDetail() throws Exception   {
	       return BatchHelper.jobDetailFactoryBeanBuilder()
	                .job(jobGrade())
	                .build();
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
