package icignal.batch.b2c.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface B2CMapper {

	/**
	 * B2C회원 마스터 업데이트건 추출
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> findMemberByUpdDt(Map<String, Object> map);
	
	/**
	 * B2C회원 정보이용동의 업데이트건 추출
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> findMemberOtherAgreeByUpdDt(Map<String, Object> map);
	
	/**
	 * B2C회원 모바일앱 정보 (푸시수신) 업데이트건 추출
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> findMobileAppInfoByUpdDt(Map<String, Object> map);

	/**
	 * B2C 등급 정보 업데이트건 추출
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> findGradeByUpdDt(Map<String, Object> map);
	
	/**
	 * B2C 상품정보 업데이트건 추출
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> findProductByUpdDt(Map<String, Object> map);
	
	
	/**
	 * 일별주문집계
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> findOrderProdDailySummary(Map<String, Object> map);
	
	/**
	 * 일별 장바구니 집계
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> findShoppingCartDailySummary(Map<String, Object> map);
	
	
	
	/**
	 * 일별 무료샘플신청 집계
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> findSampleRequestDailySummary(Map<String, Object> map);
	
	
	
	
	
	
	
	
	
}
