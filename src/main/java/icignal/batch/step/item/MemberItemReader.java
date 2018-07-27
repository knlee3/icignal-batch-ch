package icignal.batch.step.item;




import java.util.HashMap;



import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.springframework.batch.item.ItemReader;

import icignal.batch.model.MemberB2C;

public class MemberItemReader {

	//private final MemberB2cDao memberB2cDao;

/*	public MemberReader(MemberB2cDao memberB2cDao) {
		this.memberB2cDao = memberB2cDao;
	}*/
	
/*	@Autowired
	@Qualifier("sqlSessionFactoryB2C")
	SqlSessionFactory sqlSessionFactoryB2C;
	*/
	
	private final SqlSessionFactory sqlSessionFactoryB2C;
	
	public MemberItemReader(SqlSessionFactory sqlSessionFactoryB2C ) {
		this.sqlSessionFactoryB2C = sqlSessionFactoryB2C;
		
	}

	/*
	 @Resource
	public B2CMapper mapper;
	*/
	
	
	public ItemReader<MemberB2C> readMember(String from, String to) throws Exception {	
		//return memberB2cDao.selMember(updateDt);
		 final MyBatisCursorItemReader<MemberB2C> reader = new MyBatisCursorItemReader<>();	
		 reader.setSqlSessionFactory(sqlSessionFactoryB2C);
		// mapper.findMemberByUpdDt(startDt, endDt);
		 reader.setQueryId("icignal.batch.b2c.repository.B2CMapper.findMemberByUpdDt");

		 reader.setParameterValues(new HashMap<String, Object>() {	     
			private static final long serialVersionUID = 3682463164622319868L;
			{
                put("from", from);
                put("to", to);
            }
        });
		
		return reader;
	}
	
	public ItemReader<MemberB2C> readMemberOtherAgree(String from, String to) throws Exception {	
		 final MyBatisCursorItemReader<MemberB2C> reader = new MyBatisCursorItemReader<>();
		 reader.setSqlSessionFactory(sqlSessionFactoryB2C);
		 reader.setQueryId("icignal.batch.b2c.repository.B2CMapper.findMemberOtherAgreeByUpdDt");
		 reader.setParameterValues(new HashMap<String, Object>() {
			private static final long serialVersionUID = -6514758082060928753L;
			{
                put("from", from);
                put("to", to);
            }
        });
		return reader;
	}
	
	
	public ItemReader<MemberB2C> readMemberMobileAppInfo(String from, String to) throws Exception {	
		 final MyBatisCursorItemReader<MemberB2C> reader = new MyBatisCursorItemReader<>();
		 reader.setSqlSessionFactory(sqlSessionFactoryB2C);
		 reader.setQueryId("icignal.batch.b2c.repository.B2CMapper.findMobileAppInfoByUpdDt");
		 reader.setParameterValues(new HashMap<String, Object>() {		
			private static final long serialVersionUID = 807265541934365606L;
			{
                put("from", from);
                put("to", to);
            }
        });
		return reader;
	}
	
	//stepMemberMobileAppInfo
	
	
	
	
}
