package icignal.batch.b2c.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import icignal.batch.model.GradeB2C;
import icignal.batch.model.MemberB2C;
import icignal.batch.model.ProductB2C;

@Mapper
public interface B2CMapper {

	/**
	 * B2C회원 마스터 업데이트건 추출
	 * @param map
	 * @return
	 */
	public List<MemberB2C> findMemberByUpdDt(HashMap<String, Object> map);
	
	/**
	 * B2C회원 정보이용동의 업데이트건 추출
	 * @param map
	 * @return
	 */
	public List<MemberB2C> findMemberOtherAgreeByUpdDt(HashMap<String, Object> map);
	
	/**
	 * B2C회원 모바일앱 정보 (푸시수신) 업데이트건 추출
	 * @param map
	 * @return
	 */
	public List<MemberB2C> findMobileAppInfoByUpdDt(HashMap<String, Object> map);

	/**
	 * B2C 등급 정보 업데이트건 추출
	 * @param map
	 * @return
	 */
	public List<GradeB2C> findGradeByUpdDt(HashMap<String, Object> map);
	
	/**
	 * B2C 상품정보 업데이트건 추출
	 * @param map
	 * @return
	 */
	public List<ProductB2C> findProductByUpdDt(HashMap<String, Object> map);
	
	
	
	
	
	
}
