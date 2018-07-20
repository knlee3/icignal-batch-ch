package icignal.batch.model;


import java.sql.Date;

public class Order {
	private Date ordDt;
	
	private String memCd;
	private String prodCd;
	private int    prodCnt;
	private long    ordAmt;
	private int    ordCnt;
	private String createBy;
	private String modifyBy;
	private Date createDate;
	private Date modifyDate;
	
	
	public Date getOrdDt() {
		return ordDt;
	}
	public void setOrdDt(Date ordDt) {
		this.ordDt = ordDt;
	}
	public String getMemCd() {
		return memCd;
	}
	public void setMemCd(String memCd) {
		this.memCd = memCd;
	}
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public int getProdCnt() {
		return prodCnt;
	}
	public void setProdCnt(int prodCnt) {
		this.prodCnt = prodCnt;
	}
	public long getOrdAmt() {
		return ordAmt;
	}
	public void setOrdAmt(long ordAmt) {
		this.ordAmt = ordAmt;
	}
	public int getOrdCnt() {
		return ordCnt;
	}
	public void setOrdCnt(int ordCnt) {
		this.ordCnt = ordCnt;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	
	
}
