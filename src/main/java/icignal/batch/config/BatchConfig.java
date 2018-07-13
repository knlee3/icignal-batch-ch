package icignal.batch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import icignal.batch.b2c.dao.MemberB2cDao;
import icignal.batch.member.step.MemberListener;
import icignal.batch.member.step.MemberReader;
import icignal.batch.member.step.MemberWriter;
import icignal.batch.model.MemberB2C;
import icignal.batch.mrt.dao.MemberDao;



@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public MemberDao memberDao;
	
	@Autowired
	public MemberB2cDao memberB2cDao;
	
	private static final Logger log = LoggerFactory.getLogger(MemberListener.class);
	
	/**
	 * 회원정보 적재
	 * @return
	 * @throws Exception 
	 */
	@Bean(name="jobMember")
	public Job jobMember() throws Exception {
		
		log.info("memberJob start...........");
		Job job = jobBuilderFactory.get("JOB_MEMBER").incrementer(new RunIdIncrementer()).listener(new MemberListener(memberDao))
				.flow(stepMember()).end().build();
		log.info("memberJob end............");
		return job;
	}

	@Bean(name="stepMember")
	public Step stepMember() throws Exception {
		return stepBuilderFactory.get("STEP_MEMBER").<MemberB2C, MemberB2C>chunk(5000)
				.reader( new MemberReader(memberB2cDao).readMember("20180709"))
			//	.reader( new MemberReader(memberB2cDao).read("19980709"))
				//.processor(new Processor())
				.writer(new MemberWriter(memberDao)).build();
	}
	
	
}
