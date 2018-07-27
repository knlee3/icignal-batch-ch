package icignal.batch.model;

import java.sql.Date;

public class Point  extends CommonModel {
	
	private String memCd;
	private String extncDt;
	private int extncPnt;
	private String createBy;
	private String modifyBy;
	private Date createDate;
	private Date modifyDate;
	
	
	public String getMemCd() {
		return memCd;
	}
	public void setMemCd(String memCd) {
		this.memCd = memCd;
	}
	public String getExtncDt() {
		return extncDt;
	}
	public void setExtncDt(String extncDt) {
		this.extncDt = extncDt;
	}
	public int getExtncPnt() {
		return extncPnt;
	}
	public void setExtncPnt(int extncPnt) {
		this.extncPnt = extncPnt;
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
