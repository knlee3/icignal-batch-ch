package icignal.batch.member;



import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;

import org.springframework.batch.item.ItemWriter;

import icignal.batch.model.MemberB2C;




public class MemberItemWriter // implements ItemWriter<MemberB2C> 
{
	
	// private final MemberDao memberDao;
/*
	public MemberWriter(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	@Override
	public void write(List<? extends MemberB2C> members) throws Exception {
		memberDao.insertMemStg(members);
		
	}
*/

	private final SqlSessionFactory sqlSessionFactoryIC;
	
	public MemberItemWriter(SqlSessionFactory sqlSessionFactoryIC ) {
		this.sqlSessionFactoryIC = sqlSessionFactoryIC;
	}
	
	

	public ItemWriter<MemberB2C> writerMember() throws Exception {	
		 final  MyBatisBatchItemWriter<MemberB2C> writer = new  MyBatisBatchItemWriter<MemberB2C>();	
		 writer.setSqlSessionFactory(sqlSessionFactoryIC);
		 writer.setStatementId("icignal.batch.icg.repository.ICGMapper.insertChMemStg");   	
		return writer;
	}
	
	
	public ItemWriter<MemberB2C> writerMemberOtherAgree() throws Exception {	
		 final  MyBatisBatchItemWriter<MemberB2C> writer = new  MyBatisBatchItemWriter<MemberB2C>();	
		 writer.setSqlSessionFactory(sqlSessionFactoryIC);
		 writer.setStatementId("icignal.batch.icg.repository.ICGMapper.insertChMemOtherAgreeStg");   	
		return writer;
	}
	
	
	public ItemWriter<MemberB2C> writerMemberMobileAppInfo() throws Exception {	
		 final  MyBatisBatchItemWriter<MemberB2C> writer = new  MyBatisBatchItemWriter<MemberB2C>();	
		 writer.setSqlSessionFactory(sqlSessionFactoryIC);
		 writer.setStatementId("icignal.batch.icg.repository.ICGMapper.insertChMobileAppInfoStg");   	
		return writer;
	}
	
	
	
}
