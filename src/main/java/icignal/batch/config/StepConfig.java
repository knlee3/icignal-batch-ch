package icignal.batch.config;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import icignal.batch.model.GradeB2C;
import icignal.batch.model.MemberB2C;
import icignal.batch.model.ProductB2C;
import icignal.batch.step.item.CommonItemReader;
import icignal.batch.step.item.CommonItemWriter;
import icignal.batch.step.item.GradeExtractItemReader;
import icignal.batch.step.item.GradeExtractItemWriter;
import icignal.batch.step.item.GradeLoadItemReader;
import icignal.batch.step.item.GradeLoadItemWriter;
import icignal.batch.step.item.MemberItemReader;
import icignal.batch.step.item.MemberItemWriter;
import icignal.batch.step.item.MonthPntExtncCustItemReader;
import icignal.batch.step.item.MonthPntExtncCustItemWriter;

import icignal.batch.step.item.OrderProdDailyItemWriter;
import icignal.batch.step.item.ProductExtractItemReader;
import icignal.batch.step.item.ProductExtractItemWriter;
import icignal.batch.step.item.ProductLoadItemReader;
import icignal.batch.step.item.ProductLoadItemWriter;
import icignal.batch.tasklet.MemberLoadTasklet;
import icignal.batch.tasklet.StoredProcedureCallTasklet;
import icignal.batch.tasklet.SumMemAgreeDailyTasklet;
import icignal.batch.tasklet.TruncateTableTasklet;
import icignal.batch.icg.repository.ICGMapper;

@Component
public class StepConfig {

	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	@Qualifier("sqlSessionFactoryB2C")
	SqlSessionFactory sqlSessionFactoryB2C;

	@Autowired
	@Qualifier("sqlSessionFactoryIC")
	SqlSessionFactory sqlSessionFactoryIC;
	

	
	@Autowired
	public ItemWriter<Map<String,Object>> writerIC;
	
	
	@Autowired
	public ItemReader<Map<String, Object>> readerB2C;
	
	
	@Autowired
	public ItemReader<Map<String, Object>> readerIC;
	
	
	@Autowired
	CommonStepExecutionListener commonStepExecutionListener;
	
	/*@Autowired
	CommonItemReaderListener commonItemReaderListener;
	*/

	/*
	@Autowired
	public MemberDao memberDao;
	*/
	@Resource
	public ICGMapper mapper;
	
	private static final Logger log = LoggerFactory.getLogger(StepConfig.class);
	
	//소멸예정고객
//	@Bean(name="stepMonthPntExtncCust")
	public Step stepMonthPntExtncCust() throws Exception {
		return stepBuilderFactory.get("stepMonthPntExtncCust")
				.<Map<String,?>, Map<String,?>>chunk(1000)		
				.reader(new MonthPntExtncCustItemReader(sqlSessionFactoryB2C).read())
				.writer(new MonthPntExtncCustItemWriter(sqlSessionFactoryIC).writer())
				.build();
	}
	
	
	/*
	
	public Step callStep(String stepName) throws Exception {
		
//		StepBuilder sb = stepBuilderFactory.get("stepOrderProdDailyExtract");
		StepBuilder stepBuilder = stepBuilderFactory.get(stepName);
		
		Object obj = stepBuilder.getClass().getMethod("chunk", int.class).invoke(stepBuilder, 10);
		
		obj = obj.getClass().getMethod("reader", ItemReader.class).invoke(obj, readerB2C);
		return (Step) obj.getClass().getMethod("build").invoke(obj);
		
		// Method method = mepper.getClass().getMethod("get", String.class);
		//method.invoke( mapper , new HashMap<String, Object>().put("pJobExecId", je.getId()));
		
		
		
	//	return null;
		
	}
	
	
	
	*/
	
	
	
	
	//@Bean(name="stepOrderProdDailyExtract")
	@SuppressWarnings("unchecked")
	public Step stepOrderProdDailyExtract() throws Exception {
		
		// 최근 2주일건 조회로 변경
		return stepBuilderFactory
				.get("stepOrderProdDailyExtract")
				.<Map<String,Object>, Map<String,Object>>chunk(1000)
				.reader(readerB2C)
				.writer(writerIC)
			//	.listener(new CommonItemReaderListener("stepOrderProdDailyExtract"))
				.listener(commonStepExecutionListener)
				.build();
	}
	
	
	
	//@Bean(name="stepOrderProdDailyLoad")
	public Step stepOrderProdDailyLoad() throws Exception {
		// 최근 2주일건 조회로 변경
		return stepBuilderFactory.get("stepOrderProdDailyLoad")		
				.tasklet(new StoredProcedureCallTasklet(mapper))
				.build();
	}
	
	
	/*
//	@Bean(name="stepProductExtract")
	public Step stepProductExtract() throws Exception {
		return stepBuilderFactory.get("stepProductExtract").<ProductB2C, ProductB2C>chunk(1000)		
				.reader(new ProductExtractItemReader(sqlSessionFactoryB2C).read("19800709", "20180717"))
				.writer(new ProductExtractItemWriter(sqlSessionFactoryIC).writer())
				.build();
	}*/
	
	
//	@Bean(name="stepProductExtract")
	public Step stepProductExtract() throws Exception {
		return stepBuilderFactory.get("stepProductExtract")
				.<Map<String,Object>, Map<String,Object>>chunk(1000)		
				.reader(readerB2C)
				.writer(writerIC)
				.listener(commonStepExecutionListener)
				.build();
	}
	
	
	
/*	
	
//	@Bean(name="stepProductLoad")
	public Step stepProductLoad() throws Exception {
		return stepBuilderFactory.get("stepProductLoad").<ProductB2C, ProductB2C>chunk(1000)		
				.reader(new ProductLoadItemReader(sqlSessionFactoryIC).read())
				.writer(new ProductLoadItemWriter(sqlSessionFactoryIC).writer())
				.build();
	}
	
	*/
	
	
	
	
//	@Bean(name="stepProductLoad")
	public Step stepProductLoad() throws Exception {
		return stepBuilderFactory.get("stepProductLoad")
				.<Map<String,Object>, Map<String,Object>>chunk(1000)		
				.reader(readerIC)
				.writer(writerIC)
				.listener(commonStepExecutionListener)
				.build();
	}
	
	
	
	

//	@Bean(name="stepGradeExtract")
	public Step stepGradeExtract() throws Exception {
		return stepBuilderFactory.get("stepGradeExtract").<GradeB2C, GradeB2C>chunk(1000)		
				.reader(new GradeExtractItemReader(sqlSessionFactoryB2C).read("19800709", "20180717"))
				.writer(new GradeExtractItemWriter(sqlSessionFactoryIC).writer())
				.build();
	}
	
//	@Bean(name="stepGradeLoad")
	public Step stepGradeLoad() throws Exception {
		return stepBuilderFactory.get("stepGradeLoad").<GradeB2C, GradeB2C>chunk(1000)		
				.reader(new GradeLoadItemReader(sqlSessionFactoryIC).read())
				.writer(new GradeLoadItemWriter(sqlSessionFactoryIC).writer())
				.build();
	}
	
		
	
    public Step stepCall() throws Exception {
    	
    	return null;
    }

	
	
	

    /**
     * 회원정보 추출
     * @return
     * @throws Exception
     */
	public Step stepMemberExtract() throws Exception {
		return stepBuilderFactory
				.get("stepMemberExtract")
				.<Map<String,Object>, Map<String,Object>>chunk(1000)
			//	.reader(new MemberItemReader(sqlSessionFactoryB2C).readMember("20180709", "20180710"))
			//	.writer(new MemberItemWriter(sqlSessionFactoryIC).writerMember())
				.reader(readerB2C)
				.writer(writerIC)
				.listener(commonStepExecutionListener)
				.build();
	}
	
	
//	@Bean(name="stepMemberOtherAgreeExtract")
	public Step stepMemberOtherAgreeExtract() throws Exception {
		return stepBuilderFactory
				.get("stepMemberOtherAgreeExtract")
				//.<MemberB2C, MemberB2C>chunk(1000)
				//.reader(new MemberItemReader(sqlSessionFactoryB2C).readMemberOtherAgree("20180709", "20180710"))
				//.writer(new MemberItemWriter(sqlSessionFactoryIC).writerMemberOtherAgree())
				.<Map<String,Object>, Map<String,Object>>chunk(1000)
				.reader(readerB2C)
				.writer(writerIC)
				.listener(commonStepExecutionListener)
				.build();
	}
	
	
//	@Bean(name="stepMemberMobileAppInfoExtract")
	public Step stepMemberMobileAppInfoExtract() throws Exception {
		return stepBuilderFactory
				.get("stepMemberMobileAppInfoExtract")
				/*.<MemberB2C, MemberB2C>chunk(1000)
				.reader(new MemberItemReader(sqlSessionFactoryB2C).readMemberMobileAppInfo("20180709", "20180710"))
				.writer(new MemberItemWriter(sqlSessionFactoryIC).writerMemberMobileAppInfo())*/
				.<Map<String,Object>, Map<String,Object>>chunk(1000)
				.reader(readerB2C)
				.writer(writerIC)
				.listener(commonStepExecutionListener)
				.build();
	}
	
	
//	@Bean(name="stepMemberLoad")
	public Step stepMemberLoad() throws Exception {
		return stepBuilderFactory
				.get("stepMemberLoad")
			//	.tasklet(new MemberLoadTasklet(mapper))
				.tasklet(new StoredProcedureCallTasklet(mapper))
				.build();
	}
	
	
//	@Bean(name="stepSumMemAgreeDaily")
	public Step stepSumMemAgreeDaily() throws Exception {
		return stepBuilderFactory
				.get("stepSumMemAgreeDaily")
			//	.tasklet(new SumMemAgreeDailyTasklet())
				.tasklet(new StoredProcedureCallTasklet(mapper))
				.build();
	}
	
	
//	
	public Step stepTruncateTable() throws Exception {
		return stepBuilderFactory
				.get("stepTruncateTable")
				.tasklet(new TruncateTableTasklet(mapper))
				.build();
	}
	
	
	
	public Step stepStoredProcedureCallTasklet( String stepName) {
		return stepBuilderFactory
				//.get("stepSumMemAgreeDaily")
				.get(stepName)
				.tasklet(new StoredProcedureCallTasklet(mapper))
				.build();
	}
	
	
	
	public Step stepTruncateTableTasklet(String stepName) throws Exception {
		return stepBuilderFactory
			//	.get("stepTruncateTable")
				.get(stepName)
				.tasklet(new TruncateTableTasklet(mapper))
				.build();
	}
	
	
	public Step stepItem(String stepName , ItemReader<Map<String, Object>>  reader, ItemWriter<Map<String, Object>>  writer) throws Exception {
		return stepBuilderFactory
				.get(stepName)
				.<Map<String,Object>, Map<String,Object>>chunk(1000)		
			//	.reader(readerB2C)
			//	.writer(writerIC)
				.reader(reader)
				.writer(writer)
				.listener(commonStepExecutionListener)
				.build();
	}
	
	
	
	
	
}
