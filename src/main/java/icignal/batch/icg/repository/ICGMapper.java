package icignal.batch.icg.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import icignal.batch.model.GradeB2C;
import icignal.batch.model.MemberB2C;
import icignal.batch.model.ProductB2C;

@Mapper
public interface ICGMapper {
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
	
	
	
	
	
	
}
