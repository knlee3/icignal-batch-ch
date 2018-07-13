package icignal.batch.mrt.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import icignal.batch.mrt.dao.MemberDao;
import icignal.batch.model.MemberB2C;

@Repository
public class MemberDaoImpl extends JdbcDaoSupport implements MemberDao {
	
	@Autowired
	@Qualifier("icignalDB")
	DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}
	
	
/*	public void truncate(String tableName) {
		String sql = "truncate table " + tableName;
		getJdbcTemplate().execute(sql);
		
	}*/
	
	
	@Override
	public void insertMemStg(List<? extends MemberB2C> members) {		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO mrt.ch_mem_stg ( ")
		   .append("MemberCode, MAddPMcode, MemberType, MemberStatus, MemberName, MemberCellnum, MemberCellAgree, " )
		   .append("MemberEmail, MemberEmailAgree, MemberID, IGradeIDX, MAddJoinFrom, MAddGender, MAddBirthday, " ) 
		   .append("MAddLoginDate, MAddJoinDate, MAddModDate, MAgrmagazine, MAgrRcvCall, MAgrPIoffer, create_by, modify_by")
		   .append(" ) VALUES ( ")
		   .append(" ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ")
		   .append(" )");
		  
		getJdbcTemplate().batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				MemberB2C member = members.get(i);
				ps.setString(1, member.getMemberCode());
				ps.setString(2, member.getMAddPMcode());
				ps.setString(3, member.getMemberType());
				ps.setString(4, member.getMemberStatus());
				ps.setString(5, member.getMemberName());
				ps.setString(6, member.getMemberCellnum());
				ps.setString(7, member.getMemberCellAgree());
				ps.setString(8, member.getMemberEmail());
				ps.setString(9, member.getMemberEmailAgree());
				ps.setString(10, member.getMemberID());
				ps.setString(11, member.getIGradeIDX());
				ps.setString(12, member.getMAddJoinFrom());
				ps.setString(13, member.getMAddGender());
				ps.setString(14, member.getMAddBirthday());
				ps.setDate(15, member.getMAddLoginDate());				
				ps.setDate(16, member.getMAddJoinDate());
				ps.setDate(17, member.getMAddModDate());
				ps.setString(18, member.getMAgrmagazine());
				ps.setString(19, member.getMAgrRcvCall());
				ps.setString(20, member.getMAgrPIoffer());
				ps.setString(21, "batch_admin");
				ps.setString(22, "batch_admin");
			
			}

			public int getBatchSize() {
				return members.size();
			}
		});

		
	}
	
	
	@Override
	public void insertMemrOtherAgreeStg(List<? extends MemberB2C> members) {		
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE  loy.loy_mbr  ")
		   .append(" SET ")
		   .append("    MAgrmagazine = ? ")
		   .append("   ,MAgrRcvCall  = ? ")
		   .append("   ,MAgrPIoffer  = ? ")		
		   .append(" WHERE MemberCode = ? ");
		
		
		  
		getJdbcTemplate().batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				MemberB2C member = members.get(i);
		
				ps.setString(1, member.getMAgrmagazine());
				ps.setString(2, member.getMAgrRcvCall());
				ps.setString(3, member.getMAgrPIoffer());
				ps.setString(21, "batch_admin");
				ps.setString(22, "batch_admin");
			
			}

			public int getBatchSize() {
				return members.size();
			}
		});

		
	}
	
	

	@Override
	public int countLoadMembers() {
		String sql = "SELECT count(*) cnt FROM mrt.ch_mem_stg";
		//List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
		int result  = getJdbcTemplate().queryForObject(sql, Integer.class);
		 
	 
		
	//	List<MemberB2C> result = new ArrayList<MemberB2C>();
		/*for (Map<String, Object> row : rows) {
			result = row.get("cnt");
			MemberB2C customer = new MemberB2C();
			customer.setId((Long) row.get("id"));
			customer.setFirstName((String) row.get("first_name"));
			customer.setLastName((String) row.get("last_name"));
			result.add(customer);
		}*/

		return result;
	}


	@Override
	public void truncate(String tableName) {
		String sql = "truncate table " + tableName;
		getJdbcTemplate().execute(sql);
		
		
	}


}
