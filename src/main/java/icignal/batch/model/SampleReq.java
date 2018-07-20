package icignal.batch.model;

import java.sql.Date;

public class SampleReq {

    private Date reqDt;   
    private String memCd;  
    private String smplCd;  
    private String chnlCd;
    private String createBy;
    private String modifyBy;
    
    
	public Date getReqDt() {
		return reqDt;
	}
	public void setReqDt(Date reqDt) {
		this.reqDt = reqDt;
	}
	public String getMemCd() {
		return memCd;
	}
	public void setMemCd(String memCd) {
		this.memCd = memCd;
	}
	public String getSmplCd() {
		return smplCd;
	}
	public void setSmplCd(String smplCd) {
		this.smplCd = smplCd;
	}
	public String getChnlCd() {
		return chnlCd;
	}
	public void setChnlCd(String chnlCd) {
		this.chnlCd = chnlCd;
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
	
    
    
}
