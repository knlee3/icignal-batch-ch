package icignal.batch.b2c.dao;



import org.springframework.batch.item.ItemReader;

import icignal.batch.model.MemberB2C;

public interface MemberB2cDao {
	
    /**
     * B2C 회원마스터 업데이트 정보 가져온다
     * @param updateDt 업데이된 날짜
     * @return
     */
	ItemReader<MemberB2C> selMember(String updateDt);

	/**
	 * B2C 개인 정보 활용 동의 정보를 가져온다.
	 * @param updateDt  업데이트된 날짜
	 * @return
	 */
	ItemReader<MemberB2C> selMemberOtherAgree(String updateDt);
	
	
	/**
	 * B2C 모바일 앱 푸시 동의 정보를 가져온다.
	 * @param updateDt  업데이트된 날짜
	 * @return
	 */
	ItemReader<MemberB2C> selMemberMobileAppPushAgree(String updateDt);
	
	

}
