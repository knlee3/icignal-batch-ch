package icignal.batch.config;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import icignal.batch.model.GradeB2C;
import icignal.batch.model.MemberB2C;
import icignal.batch.model.ProductB2C;
import icignal.batch.product.extract.ProductExtractItemReader;
import icignal.batch.product.extract.ProductExtractItemWriter;
import icignal.batch.product.extract.ProductExtractListener;
import icignal.batch.product.load.ProductLoadItemReader;
import icignal.batch.product.load.ProductLoadItemWriter;
import icignal.batch.tasklet.MemberLoadTasklet;
import icignal.batch.tasklet.SumMemAgreeDailyTasklet;
import icignal.batch.grade.extract.GradeExtractItemReader;
import icignal.batch.grade.extract.GradeExtractItemWriter;
import icignal.batch.grade.extract.GradeExtractListener;
import icignal.batch.grade.load.GradeLoadItemReader;
import icignal.batch.grade.load.GradeLoadItemWriter;
import icignal.batch.icg.repository.ICGMapper;
import icignal.batch.member.MemberItemReader;
import icignal.batch.member.MemberItemWriter;
import icignal.batch.member.MemberListener;



@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	@Qualifier("sqlSessionFactoryB2C")
	SqlSessionFactory sqlSessionFactoryB2C;

	@Autowired
	@Qualifier("sqlSessionFactoryIC")
	SqlSessionFactory sqlSessionFactoryIC;
	
	

	/*
	@Autowired
	public MemberDao memberDao;
	*/
	@Resource
	public ICGMapper mapper;
	
	private static final Logger log = LoggerFactory.getLogger(MemberListener.class);
	
	/**
	 * 회원정보 적재
	 * @return
	 * @throws Exception 
	 */
	@Bean(name="jobMember")
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
	
	

	@Bean(name="stepGradeExtract")
	public Step stepGradeExtract() throws Exception {
		return stepBuilderFactory.get("stepGradeExtract").<GradeB2C, GradeB2C>chunk(1000)		
				.reader(new GradeExtractItemReader(sqlSessionFactoryB2C).read("19800709", "20180717"))
				.writer(new GradeExtractItemWriter(sqlSessionFactoryIC).writer())
				.build();
	}
	
	@Bean(name="stepGradeLoad")
	public Step stepGradeLoad() throws Exception {
		return stepBuilderFactory.get("stepGradeLoad").<GradeB2C, GradeB2C>chunk(1000)		
				.reader(new GradeLoadItemReader(sqlSessionFactoryIC).read())
				.writer(new GradeLoadItemWriter(sqlSessionFactoryIC).writer())
				.build();
	}
	
	
	
	
	@Bean(name="jobProduct")
	public Job jobProduct() throws Exception {
		Job job = jobBuilderFactory.get("jobProduct")
								   .incrementer(new RunIdIncrementer())
								   .listener(new ProductExtractListener(mapper))
								   .start(stepProductExtract())
								   .next(stepProductLoad())
								   .build();
		return job;
	}
	
	
	
	
	
	
	@Bean(name="jobSumMemAgreeDaily")
	public Job jobSumMemAgreeDaily() throws Exception {
		Job job = jobBuilderFactory.get("jobSumMemAgreeDaily")
								   .incrementer(new RunIdIncrementer())
                                   .start(stepSumMemAgreeDaily())
								   .build();
		return job;
	}
	
	
	
	
	@Bean(name="stepProductExtract")
	public Step stepProductExtract() throws Exception {
		return stepBuilderFactory.get("stepProductExtract").<ProductB2C, ProductB2C>chunk(1000)		
				.reader(new ProductExtractItemReader(sqlSessionFactoryB2C).read("19800709", "20180717"))
				.writer(new ProductExtractItemWriter(sqlSessionFactoryIC).writer())
				.build();
	}
	
	
	
	@Bean(name="stepProductLoad")
	public Step stepProductLoad() throws Exception {
		return stepBuilderFactory.get("stepProductLoad").<ProductB2C, ProductB2C>chunk(1000)		
				.reader(new ProductLoadItemReader(sqlSessionFactoryIC).read())
				.writer(new ProductLoadItemWriter(sqlSessionFactoryIC).writer())
				.build();
	}
	
	
	
	
	

	@Bean(name="stepMemberExtract")
	public Step stepMemberExtract() throws Exception {
		return stepBuilderFactory.get("stepMemberExtract").<MemberB2C, MemberB2C>chunk(1000)
		//		.reader( new MemberReader(memberB2cDao).readMember("20180709"))
				.reader(new MemberItemReader(sqlSessionFactoryB2C).readMember("20180709", "20180710"))
			//	.reader( new MemberReader(memberB2cDao).read("19980709"))
				//.processor(new Processor())
			//	.writer(new MemberWriter(memberDao)).build();
				.writer(new MemberItemWriter(sqlSessionFactoryIC).writerMember())
				.build();
	}
	
	
	@Bean(name="stepMemberOtherAgreeExtract")
	public Step stepMemberOtherAgreeExtract() throws Exception {
		return stepBuilderFactory.get("stepMemberOtherAgreeExtract").<MemberB2C, MemberB2C>chunk(1000)
				.reader(new MemberItemReader(sqlSessionFactoryB2C).readMemberOtherAgree("20180709", "20180710"))
				.writer(new MemberItemWriter(sqlSessionFactoryIC).writerMemberOtherAgree())
				.build();
	}
	
	
	@Bean(name="stepMemberMobileAppInfoExtract")
	public Step stepMemberMobileAppInfoExtract() throws Exception {
		return stepBuilderFactory.get("stepMemberMobileAppInfoExtract").<MemberB2C, MemberB2C>chunk(1000)
				.reader(new MemberItemReader(sqlSessionFactoryB2C).readMemberMobileAppInfo("20180709", "20180710"))
				.writer(new MemberItemWriter(sqlSessionFactoryIC).writerMemberMobileAppInfo())
				.build();
	}
	
	
	@Bean(name="stepMemberLoad")
	public Step stepMemberLoad() throws Exception {
		return stepBuilderFactory.get("stepMemberLoad")
				.tasklet(new MemberLoadTasklet(mapper))
				.build();
	}
	
	
	@Bean(name="stepSumMemAgreeDaily")
	public Step stepSumMemAgreeDaily() throws Exception {
		return stepBuilderFactory.get("stepMemberLoad")
				.tasklet(new SumMemAgreeDailyTasklet(mapper))
				.build();
	}
	
	
	
}
