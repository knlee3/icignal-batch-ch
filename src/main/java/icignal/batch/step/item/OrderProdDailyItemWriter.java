package icignal.batch.step.item;

import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.batch.item.ItemWriter;
import icignal.batch.model.Order;

public class OrderProdDailyItemWriter {
	private final SqlSessionFactory sqlSessionFactoryIC;
	
	public OrderProdDailyItemWriter(SqlSessionFactory sqlSessionFactoryIC ) {
		this.sqlSessionFactoryIC = sqlSessionFactoryIC;
	}
	
	

	public ItemWriter<Map<String,Object>> writer() throws Exception {	
		 final  MyBatisBatchItemWriter<Map<String,Object>> writer = new  MyBatisBatchItemWriter<>();	
		 writer.setSqlSessionFactory(sqlSessionFactoryIC);
		 writer.setStatementId("icignal.batch.icg.repository.ICGMapper.insertOrdProdDailySumStg");   	
		return writer;
	}
	
}
