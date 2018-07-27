package icignal.batch.step.item;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.springframework.batch.item.ItemReader;

import icignal.batch.model.Point;



public class MonthPntExtncCustItemReader {
	
private final SqlSessionFactory sqlSessionFactoryB2C;
	
	public MonthPntExtncCustItemReader(SqlSessionFactory sqlSessionFactoryB2C ) {
		this.sqlSessionFactoryB2C = sqlSessionFactoryB2C;
		
	}

	
	public ItemReader<Map<String,?>> read() throws Exception {	
	
		 final MyBatisCursorItemReader<Map<String,?>> reader = new MyBatisCursorItemReader<>();	
		 reader.setSqlSessionFactory(sqlSessionFactoryB2C);
		 reader.setQueryId("icignal.batch.b2c.repository.B2CMapper.findMonthPntExtncCust");
		 
		return reader;
	}
	

}
