package icignal.batch.icg.repository;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface ICGMapper {
	
	////////// 공통 영역 //////////////////
	

	public Map<String,Object> findJobInfo(Map<String, Object> map);
	
	public Map<String,Object> findJobStepInfo(Map<String, Object> map);
	
	
	
	public Map<String,Object> findJobStepMapperInfo(Map<String, Object> map);
	

	
	
	
	////////// 공통 영역 //////////////////
	
	
	
	
	
	
    /**
     * B2C 회원마스터 업데이트건 적재
     * @param members
     */
	public void insertChMemStg(List<Map<String,Object>> members);

	/**
	 * B2C 회원 정보이용 동의 업데이트건 적재
	 * @param members
	 */
	public void insertChMemOtherAgreeStg(List<Map<String,Object>> members);
	
	/**
	 * B2C 회원 모바일정보 (푸시동의) 업데이트건 적재
	 * @param members
	 */
	public void insertChMobileAppInfoStg(List<Map<String,Object>> members);

	
	/**
	 * B2C 등급정보 업데이트건 적재
	 * @param members
	 */
	public void insertChGradeStg(List<Map<String,Object>> members);

	/**
	 * B2C 상품정보 업데이트건 적재
	 * @param members
	 */
	public void insertChProdStg(List<Map<String,Object>> members);

	
		
	/**
	 * staging table trucate
	 * @param tableName
	 */
	public void truncateTable(@Param("tableName")String tableName);
	
	/**
	 * 회원마스터 적재 카운트 조회
	 * @return
	 */
	public int cntChMemStg();
	
	/**
	 * 등급 적재 카운터 조회
	 * @return
	 */
	public int cntChGradeStg();
	
	/**
	 * 상품 적재 카운터 조회
	 * @return
	 */
	public int cntChProdStg();
	
	
	///////////// staging 테이블 데이터 추출 mapper ///////////////////
	/**
	 * ch_mem_stg 테이블의 데이터 전체를 추출
	 * @return
	 */
	public List<Map<String, Object>> findAllChMemStg();
	
	/**
	 * ch_grade_stg 테이블의 데이터 전제 추출
	 * @return
	 */
	public List<Map<String, Object>> findAllChGradeStg();
	
	/**
	 * ch_prod_stg 테이블의 데이터 전체 추출
	 * @return
	 */
	public List<Map<String, Object>> findAllChProdStg();
	
	
	//////////////// iciganl 테이블에 로딩  ////////////////////
	
	public void loadProduct();
	
	public void loadGrade();
	
	public void loadMember(Map<String, Object> map);

	
	
	
	
	
	
	///////////////////// summary ///////////////////////
	/**
	 * 회원 수신동의정보 일 집계  
	 */
	public void summaryMemAgreeDaily(Map<String, Object> map);
	
	
	/**
	 * 비구매관심상품 마트
	 * @param map
	 */
	public void summaryInterestProdMart(Map<String, Object> map);
	
	
	

	
	/**
	 * 일별 무료샘플 신청 집계 
	 * @param sampleReq
	 */
	public void loadSmplReqDailySum(Map<String, Object> map);

	
	/**
	 * 회원관심상품집계
	 * @param map
	 */
	public void loadProdSrchSum(Map<String, Object> map);
	
	/**
	 * 일별 장바구니
	 * @param map
	 */
	public void loadShoppingCartDailySum(Map<String, Object> map);

	
	
	/**
	 * 캠페인반응
	 * @param map
	 */
	public void loadCampRespDailySum(Map<String, Object> map);
	
	/**
	 * 캠페인구매반응
	 * @param map
	 */
	public void loadCampOrdRespDailySum(Map<String, Object> map);
	
	
	/**
	 * 당월말적립금소멸예정고객
	 * @param map
	 */
	public void loadMonthPntExtncCust(Map<String, Object> map);

	
	/**
	 * 일별 주문 집계 -> staging table insert
	 * @param order
	 */
	public void insertOrdProdDailySumStg(Map<String, Object> map);
	
	
	/**
	 * 일별 주문 집계
	 * @param map
	 */
	public void loadOrderProdDailySum(Map<String, Object> map);


	/**
	 * 장바구니 마트
	 * @param map
	 */
	public void summaryShoppingCartMart(Map<String, Object> map);
	
	
	/**
	 * 맞춤상품 마트
	 * @param map
	 */
	public void summaryMemberFitProductMart(Map<String, Object> map);
	
	/**
	 * 고객유형별상품주문마트
	 * @param map
	 */
	public void summaryCustTypeProdOrderMart(Map<String, Object> map);
	
	/**
	 * 상품구매 마트
	 * @param map
	 */
	public void summaryProdudctOrderMart(Map<String, Object> map);
	
		
	
	

   /**
    * batch_step 테이블 추출조건 시작일 및 종료일 업데이트
    * @param map
    */
	public void updateJobStepMapperExtrDt(Map<String, Object> map);





	
	            
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
}
