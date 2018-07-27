package icignal.batch.step.item;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.batch.item.ItemWriter;


import icignal.batch.model.ProductB2C;

public class ProductExtractItemWriter {

	private final SqlSessionFactory sqlSessionFactoryIC;
	
	public ProductExtractItemWriter(SqlSessionFactory sqlSessionFactoryIC ) {
		this.sqlSessionFactoryIC = sqlSessionFactoryIC;
	}
	
	

	public ItemWriter<ProductB2C> writer() throws Exception {	
		 final  MyBatisBatchItemWriter<ProductB2C> writer = new  MyBatisBatchItemWriter<>();	
		 writer.setSqlSessionFactory(sqlSessionFactoryIC);
		 writer.setStatementId("icignal.batch.icg.repository.ICGMapper.insertChProdStg");   	
		return writer;
	}
	

	
}
