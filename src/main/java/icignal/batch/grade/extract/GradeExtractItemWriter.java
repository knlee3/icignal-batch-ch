package icignal.batch.grade.extract;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.batch.item.ItemWriter;

import icignal.batch.model.GradeB2C;

public class GradeExtractItemWriter {


	private final SqlSessionFactory sqlSessionFactoryIC;
	
	public GradeExtractItemWriter(SqlSessionFactory sqlSessionFactoryIC ) {
		this.sqlSessionFactoryIC = sqlSessionFactoryIC;
	}
	
	

	public ItemWriter<GradeB2C> writer() throws Exception {	
		 final  MyBatisBatchItemWriter<GradeB2C> writer = new  MyBatisBatchItemWriter<GradeB2C>();	
		 writer.setSqlSessionFactory(sqlSessionFactoryIC);
		 writer.setStatementId("icignal.batch.icg.repository.ICGMapper.insertChGradeStg");   	
		return writer;
	}
	
}
