package icignal.batch.mrt.dao;

import java.util.List;

import icignal.batch.model.MemberB2C;

//import com.javasampleapproach.batchcsvpostgresql.model.Customer;

public interface MemberDao {

	// public void insert(List<? extends MemberB2C> member);
	
	//List<MemberB2C> loadAllMembers();
	int countLoadMembers();

	void truncate(String tableName);

	void insertMemStg(List<? extends MemberB2C> members);

	void insertMemrOtherAgreeStg(List<? extends MemberB2C> members);
	
	
	
}
