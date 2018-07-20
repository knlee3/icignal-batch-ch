package icignal.batch.grade.load;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.springframework.batch.item.ItemReader;

import icignal.batch.model.GradeB2C;

public class GradeLoadItemReader {
	
	
	private final SqlSessionFactory sqlSessionFactoryIC;
	
	public GradeLoadItemReader(SqlSessionFactory sqlSessionFactoryIC ) {
		this.sqlSessionFactoryIC = sqlSessionFactoryIC;
		
	}

	
	



	public ItemReader<GradeB2C> read() throws Exception {
		 final MyBatisCursorItemReader<GradeB2C> reader = new MyBatisCursorItemReader<>();	
		 reader.setSqlSessionFactory(sqlSessionFactoryIC);
		 reader.setQueryId("icignal.batch.icg.repository.ICGMapper.findAllChGradeStg");
		
		return reader;
	}

}
