package icignal.batch.icg.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import icignal.batch.model.GradeB2C;
import icignal.batch.model.MemberB2C;
import icignal.batch.model.Order;
import icignal.batch.model.ProductB2C;
import icignal.batch.model.SampleReq;


@Mapper
public interface ICGMapper {
	
	////////// 공통 영역 //////////////////
	
	/**
	 * SETP_ID에 대한 STEP 정보를 가져온다.
	 * @return
	 */
	public Map<String,String> findByStepId(Map<String, Object> map);
	
	
	
	
	////////// 공통 영역 //////////////////
	
	
	
	
	
	
    /**
     * B2C 회원마스터 업데이트건 적재
     * @param members
     */
	public void insertChMemStg(List<? extends MemberB2C> members);

	/**
	 * B2C 회원 정보이용 동의 업데이트건 적재
	 * @param members
	 */
	public void insertChMemOtherAgreeStg(List<? extends MemberB2C> members);
	
	/**
	 * B2C 회원 모바일정보 (푸시동의) 업데이트건 적재
	 * @param members
	 */
	public void insertChMobileAppInfoStg(List<? extends MemberB2C> members);

	
	/**
	 * B2C 등급정보 업데이트건 적재
	 * @param members
	 */
	public void insertChGradeStg(List<? extends GradeB2C> members);

	/**
	 * B2C 상품정보 업데이트건 적재
	 * @param members
	 */
	public void insertChProdStg(List<? extends ProductB2C> members);

	
		
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
	public List<MemberB2C> findAllChMemStg();
	
	/**
	 * ch_grade_stg 테이블의 데이터 전제 추출
	 * @return
	 */
	public List<GradeB2C> findAllChGradeStg();
	
	/**
	 * ch_prod_stg 테이블의 데이터 전체 추출
	 * @return
	 */
	public List<ProductB2C> findAllChProdStg();
	
	
	//////////////// iciganl 테이블에 로딩  ////////////////////
	
	public void loadProduct();
	
	public void loadGrade();
	
	public void loadMember(@Param("pJobExecId") long jobExecId);

	
	
	
	
	
	
	///////////////////// summary ///////////////////////
	/**
	 * 회원 수신동의정보 일 집계  
	 */
	public void summaryMemAgreeDaily(@Param("pJobExecId") long jobExecId);
	
	
	

	
	/**
	 * 일별 무료샘플 신청 집계 
	 * @param sampleReq
	 */
	public void loadSmplReqDailySum(List<? extends SampleReq> sampleReq);

	
	/**
	 * 회원관심상품집계
	 * @param map
	 */
	public void loadProdSrchSum(List<? extends Map<String,?>> map);
	
	/**
	 * 일별 장바구니
	 * @param map
	 */
	public void loadShoppingCartDailySum(List<? extends Map<String,?>> map);

	/**
	 * 캠페인반응
	 * @param map
	 */
	public void loadCampRespDailySum(List<? extends Map<String,?>> map);
	
	/**
	 * 캠페인구매반응
	 * @param map
	 */
	public void loadCampOrdRespDailySum(List<? extends Map<String,?>> map);
	
	
	/**
	 * 당월말적립금소멸예정고객
	 * @param map
	 */
	public void loadMonthPntExtncCust(List<? extends Map<String,?>> map);

	
	/**
	 * 일별 주문 집계 -> staging table insert
	 * @param order
	 */
	public void insertOrdProdDailySumStg(List<? extends Order> order);
	
	
	/**
	 * 일별 주문 집계
	 * @param map
	 */
	public void loadOrderProdDailySum(Map<String, Object> map);





	
	            
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
}
