package icignal.batch.step.item;



import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.springframework.batch.item.ItemReader;

import icignal.batch.model.ProductB2C;

public class ProductLoadItemReader {

	private final SqlSessionFactory sqlSessionFactoryIC;
	
	public ProductLoadItemReader(SqlSessionFactory sqlSessionFactoryIC ) {
		this.sqlSessionFactoryIC = sqlSessionFactoryIC;
		
	}

	
	
	
	public ItemReader<ProductB2C> read() throws Exception {
		 final MyBatisCursorItemReader<ProductB2C> reader = new MyBatisCursorItemReader<>();	
		 reader.setSqlSessionFactory(sqlSessionFactoryIC);
		 reader.setQueryId("icignal.batch.icg.repository.ICGMapper.findAllChProdStg");
		
		return reader;
	}

}
