package icignal.batch.step.item;


import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.batch.item.ItemWriter;


public class MonthPntExtncCustItemWriter  {

	private final SqlSessionFactory sqlSessionFactoryIC;
	
	public MonthPntExtncCustItemWriter(SqlSessionFactory sqlSessionFactoryIC ) {
		this.sqlSessionFactoryIC = sqlSessionFactoryIC;
	}
	
	
	public ItemWriter<? super Map<String,?>> writer() throws Exception {	
		 final  MyBatisBatchItemWriter<? super Map<String,?>> writer = new  MyBatisBatchItemWriter<>();	
		 writer.setSqlSessionFactory(sqlSessionFactoryIC);
		 writer.setStatementId("icignal.batch.icg.repository.ICGMapper.loadMonthPntExtncCust");   	
		return writer;
	}

	
}
