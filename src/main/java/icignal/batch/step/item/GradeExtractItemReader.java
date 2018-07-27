package icignal.batch.step.item;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.springframework.batch.item.ItemReader;

import icignal.batch.model.GradeB2C;

public class GradeExtractItemReader {
	
	private final SqlSessionFactory sqlSessionFactoryB2C;
	
	public GradeExtractItemReader(SqlSessionFactory sqlSessionFactoryB2C ) {
		this.sqlSessionFactoryB2C = sqlSessionFactoryB2C;
		
	}

	/*
	 @Resource
	public B2CMapper mapper;
	*/
	
	
	public ItemReader<GradeB2C> read(String from, String to) throws Exception {	
	
		 final MyBatisCursorItemReader<GradeB2C> reader = new MyBatisCursorItemReader<>();	
		 reader.setSqlSessionFactory(sqlSessionFactoryB2C);
		 reader.setQueryId("icignal.batch.b2c.repository.B2CMapper.findGradeByUpdDt");

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
