package icignal.batch.model;

import java.sql.Date;

public class GradeB2C extends CommonModel {

	private String IGradeIDX;
	private String IGradeName;
	private String IGradeRegDate;
	private String IGradeModDate;
		
	private String createBy;
	private String modifyBy;
	private Date createDate;
	private Date modifyDate;
	
	
	public String getIGradeIDX() {
		return IGradeIDX;
	}
	public void setIGradeIDX(String iGradeIDX) {
		IGradeIDX = iGradeIDX;
	}
	public String getIGradeName() {
		return IGradeName;
	}
	public void setIGradeName(String iGradeName) {
		IGradeName = iGradeName;
	}
	public String getIGradeRegDate() {
		return IGradeRegDate;
	}
	public void setIGradeRegDate(String iGradeRegDate) {
		IGradeRegDate = iGradeRegDate;
	}
	public String getIGradeModDate() {
		return IGradeModDate;
	}
	public void setIGradeModDate(String iGradeModDate) {
		IGradeModDate = iGradeModDate;
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
