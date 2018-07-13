package icignal.batch.member.step;


import org.springframework.batch.item.ItemReader;

import icignal.batch.model.MemberB2C;
import icignal.batch.b2c.dao.MemberB2cDao;

public class MemberReader {

	private final MemberB2cDao memberB2cDao;

	public MemberReader(MemberB2cDao memberB2cDao) {
		this.memberB2cDao = memberB2cDao;
	}

	
	public ItemReader<MemberB2C> readMember(String updateDt) throws Exception {	
		return memberB2cDao.selMember(updateDt);
		
	}
	
	
}
