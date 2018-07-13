package icignal.batch.b2c.dao.impl;


import javax.sql.DataSource;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import icignal.batch.b2c.dao.MemberB2cDao;

import icignal.batch.model.MemberB2C;

@Repository
public class MemberB2cDaoImpl implements MemberB2cDao{

		
	@Autowired
	@Qualifier("b2cDB")
	DataSource dataSource;
	
	@Override
	public ItemReader<MemberB2C> selMember(String updateDt) {
		String sql = "select " + 
				"	a.MemberCode	as memberCode		" + 
			//	",	a.MemberType			/*	회원유형	*/" + 
				",	'G'	as memberType		" + 
				",	MemberStatus as memberStatus	" + 
				",	MemberName		as memberName	" + 
				",	MemberCellnum	as memberCellnum	" + 
				",	MemberCellAgree	 as memberCellAgree	" + 
				",	MemberEmail	     as memberEmail		" + 
				",	MemberEmailAgree as memberEmailAgree " + 
				",	MemberID		as memberID 	" + 
				",	IGradeIDX		as IGradeIDX	" + 
				",	MAddJoinFrom	as MAddJoinFrom	" + 
				",	MAddGender		as MAddGender	" + 
				",	MAddBirthday	as MAddBirthday	" + 
				",	b.MAddLoginDate	 as MAddLoginDate	" + 
				",	MAddJoinDate	as MAddJoinDate	" + 
				",	b.MAddModDate	as MAddModDate	" + 
				" from member_basic a " + 
				" join member_add b on a.memberCode = b.memberCode " + 
				" where b.MAddModDate >= '" + updateDt +"'" ; 
				// " where b.MAddModDate >= '20180705' and  b.MAddModDate < '20180706' "; 
					
				
		JdbcCursorItemReader<MemberB2C> databaseReader = new JdbcCursorItemReader<>();
		 
        databaseReader.setDataSource(dataSource);
        databaseReader.setSql(sql);
        
        databaseReader.setRowMapper(new BeanPropertyRowMapper<>(MemberB2C.class));
 
        return databaseReader;
		
		
	
	}

	@Override
	public ItemReader<MemberB2C> selMemberOtherAgree(String updateDt) {
		String sql = "select " + 
				"	a.memberCode as memberCode" + 
				",	a.MAgrmagazine as MAgrmagazine" + 
				",	a.MAgrRcvCall	 as MAgrRcvCall " + 
				",	a.MAgrPIoffer  as MAgrPIoffer" + 
				" from MEMBER_OTHER_AGREE a" + 
				" where a.MAgrModDate  >= '" + updateDt +"'" ;  
			//	" and  a.MAgrModDate < '20180706' ;" + 
				 
				// " where b.MAddModDate >= '" + updateDt +"'" ; 
				// " where b.MAddModDate >= '20180705' and  b.MAddModDate < '20180706' "; 
					
		JdbcCursorItemReader<MemberB2C> databaseReader = new JdbcCursorItemReader<>();
		 
        databaseReader.setDataSource(dataSource);
        databaseReader.setSql(sql);
        
        databaseReader.setRowMapper(new BeanPropertyRowMapper<>(MemberB2C.class));
 
        return databaseReader;
		
	}

	@Override
	public ItemReader<MemberB2C> selMemberMobileAppPushAgree(String updateDt) {
		// TODO Auto-generated method stub
		return null;
	}

}
