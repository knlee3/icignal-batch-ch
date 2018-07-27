package icignal.batch.step.item;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.springframework.batch.item.ItemReader;

import icignal.batch.model.ProductB2C;

public class ProductExtractItemReader {


	private final SqlSessionFactory sqlSessionFactoryB2C;
	
	public ProductExtractItemReader(SqlSessionFactory sqlSessionFactoryB2C ) {
		this.sqlSessionFactoryB2C = sqlSessionFactoryB2C;
		
	}

	
	
	
	public ItemReader<ProductB2C> read(String from, String to) throws Exception {
		 final MyBatisCursorItemReader<ProductB2C> reader = new MyBatisCursorItemReader<>();	
		 reader.setSqlSessionFactory(sqlSessionFactoryB2C);
		 reader.setQueryId("icignal.batch.b2c.repository.B2CMapper.findProductByUpdDt");
		 reader.setParameterValues(new HashMap<String, Object>() {	     
			private static final long serialVersionUID = 3682463164622319868L;
			{
                put("from", from);
                put("to", to);
            }
        });
		
		 
		return reader;
	}
	
}
