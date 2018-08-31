package icignal.batch.config;

import java.util.Map;
import javax.annotation.Resource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import icignal.batch.tasklet.StoredProcedureCallTasklet;
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
	
	
	
	
	
    public Step stepCall() throws Exception {
    	
    	return null;
    }

	
	
	public Step stepStoredProcedureCallTasklet( String stepName) {
		return stepBuilderFactory
				.get(stepName)
				.tasklet(new StoredProcedureCallTasklet(mapper))
				.build();
	}
	
	
	
	public Step stepTruncateTableTasklet(String stepName) throws Exception {
		return stepBuilderFactory
				.get(stepName)
				.tasklet(new TruncateTableTasklet(mapper))
				.build();
	}
	
	
	public Step stepItem(String stepName 
						,ItemReader<Map<String, Object>>  reader
						,ItemWriter<Map<String, Object>>  writer) throws Exception {
		return stepBuilderFactory
				.get(stepName)
				.<Map<String,Object>, Map<String,Object>>chunk(1000)
				.reader(reader)
				.writer(writer)
				.listener(commonStepExecutionListener)
				.build();
	}
	
	

	
	
}
