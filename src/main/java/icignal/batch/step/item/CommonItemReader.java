package icignal.batch.step.item;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import icignal.batch.Listener.MemberListener;

@Component
public class CommonItemReader {

private  SqlSessionFactory sqlSessionFactory;

private static final Logger log = LoggerFactory.getLogger(CommonItemReader.class);	

	//@Autowired
    public CommonItemReader(@Qualifier("sqlSessionFactoryB2C")	SqlSessionFactory sqlSessionFactory) {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAA");
		this.sqlSessionFactory = sqlSessionFactory;
		
    	
    }




/*
	public CommonItemReader(SqlSessionFactory sqlSessionFactory ) {
		this.sqlSessionFactory = sqlSessionFactory;
		
	}*/

	@StepScope
	@SuppressWarnings("serial")	
	@Bean
	public MyBatisCursorItemReader<Map<String,Object>>  readerB2C(@Value("#{jobParameters}") Map<String,Object> jobParameters
			) throws Exception {	
	log.info("aaaaaaaaaaaaaaaaaaaaa!!!!!!!!!!!!!!");
		 final MyBatisCursorItemReader<Map<String,Object>> reader = new MyBatisCursorItemReader<>();
		 System.out.println("sqlSessionFactoryB2C:"  + sqlSessionFactory);
		 
		 
		 reader.setSqlSessionFactory(sqlSessionFactory);
		 reader.setQueryId("icignal.batch.b2c.repository.B2CMapper.findOrderProdDailySummary");

		String   from = "20180621";
	    String   to = "20180722";
		 
		 System.out.println("jobParameters: " + jobParameters);
		 System.out.println("000000000000000000000000000000");
		 
		 reader.setParameterValues(new HashMap<String, Object>() {	     
			{
				  // for( String key : params.keySet() )  put(key, params.get(key));
                put("from", from);
                put("to", to);
              
            }
        });
		
		 System.out.println("1111111111111111111aaaaaaaaaaaa");
		return reader;
	}

/*
	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		// TODO Auto-generated method stub
		return null;
	}*/

/*
	@Override
	public void beforeStep(StepExecution stepExecution) {
		log.info("beforStep!!!!!!!!!!!!!!");
		
            log.info("CommonItemReader beforeStep...");
           Map<String, JobParameter >  params = stepExecution.getJobParameters().getParameters();
           String param_from = (String)params.get("to").getValue();
           
           for( String key : params.keySet() ) {
        	   
        	  String param_value =  (String)params.get(key).getValue();
        	  log.info("param_value: " + param_value);
        	  
        	  
        	   //put(key, params.get(key));
           }
            
		
	}


	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		return null;
	}*/
	
	
}
