package icignal.batch.step.item;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.batch.item.ItemWriter;

import icignal.batch.model.GradeB2C;

public class GradeLoadItemWriter {
	private final SqlSessionFactory sqlSessionFactoryIC;
	
	public GradeLoadItemWriter(SqlSessionFactory sqlSessionFactoryIC ) {
		this.sqlSessionFactoryIC = sqlSessionFactoryIC;
	}
	
	
	public ItemWriter<GradeB2C> writer() throws Exception {	
		 final  MyBatisBatchItemWriter<GradeB2C> writer = new  MyBatisBatchItemWriter<>();	
		 writer.setSqlSessionFactory(sqlSessionFactoryIC);
		 writer.setStatementId("icignal.batch.icg.repository.ICGMapper.loadGrade");   	
		return writer;
	}
	
	
}
