package icignal.batch.member.step;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import icignal.batch.model.MemberB2C;
import icignal.batch.mrt.dao.MemberDao;



public class MemberWriter implements ItemWriter<MemberB2C> {

	private final MemberDao memberDao;

	public MemberWriter(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	@Override
	public void write(List<? extends MemberB2C> members) throws Exception {
		memberDao.insert(members);
	}

}
