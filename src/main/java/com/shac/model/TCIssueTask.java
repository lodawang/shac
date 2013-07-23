package com.shac.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "sc_tc_issuetask")
public class TCIssueTask implements Serializable{
	private String id;
	private String partid;
	private String updatestatus;
	private String previousitem;
	private String previousassem;
	private String previousver;
	private String doctype;
	private Date docDate;
	private Date executeDate;
	private String modifynumb;
	private String veroftc;
	private String techdocclass;
	private String drawingNumb;
	private Integer drawingPage;
	private String drawingSize;
	private String assembly;//总成号
	private String assembTitle;//总成名称
	private String assembOrnot;//是否总成
	private String client;
	private String modelCode;
	private String cltPartNumb;
	private String processIn;//生产状态
	private String attachFile;
	private String issueto;
	private String taskStatus;//发放状态
	private Date createTime;
	private String dealstatus;
	private Date dealtime;
	private String faillog;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")   
	@Column(name = "id", nullable = false, length = 32)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPartid() {
		return partid;
	}
	public void setPartid(String partid) {
		this.partid = partid;
	}
	public String getUpdatestatus() {
		return updatestatus;
	}
	public void setUpdatestatus(String updatestatus) {
		this.updatestatus = updatestatus;
	}
	public String getPreviousitem() {
		return previousitem;
	}
	public void setPreviousitem(String previousitem) {
		this.previousitem = previousitem;
	}
	public String getPreviousassem() {
		return previousassem;
	}
	public void setPreviousassem(String previousassem) {
		this.previousassem = previousassem;
	}
	public String getPreviousver() {
		return previousver;
	}
	public void setPreviousver(String previousver) {
		this.previousver = previousver;
	}
	public String getDoctype() {
		return doctype;
	}
	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}
	public Date getDocDate() {
		return docDate;
	}
	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}
	public Date getExecuteDate() {
		return executeDate;
	}
	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}
	public String getModifynumb() {
		return modifynumb;
	}
	public void setModifynumb(String modifynumb) {
		this.modifynumb = modifynumb;
	}
	public String getVeroftc() {
		return veroftc;
	}
	public void setVeroftc(String veroftc) {
		this.veroftc = veroftc;
	}
	public String getTechdocclass() {
		return techdocclass;
	}
	public void setTechdocclass(String techdocclass) {
		this.techdocclass = techdocclass;
	}
	public String getDrawingNumb() {
		return drawingNumb;
	}
	public void setDrawingNumb(String drawingNumb) {
		this.drawingNumb = drawingNumb;
	}
	public Integer getDrawingPage() {
		return drawingPage;
	}
	public void setDrawingPage(Integer drawingPage) {
		this.drawingPage = drawingPage;
	}
	public String getDrawingSize() {
		return drawingSize;
	}
	public void setDrawingSize(String drawingSize) {
		this.drawingSize = drawingSize;
	}
	public String getAssembly() {
		return assembly;
	}
	public void setAssembly(String assembly) {
		this.assembly = assembly;
	}
	public String getAssembTitle() {
		return assembTitle;
	}
	public void setAssembTitle(String assembTitle) {
		this.assembTitle = assembTitle;
	}
	public String getAssembOrnot() {
		return assembOrnot;
	}
	public void setAssembOrnot(String assembOrnot) {
//		if(assembOrnot.equalsIgnoreCase("Y")){
//			this.assembOrnot = true;
//		}else{
//			this.assembOrnot = false;
//		}
		this.assembOrnot = assembOrnot;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	public String getCltPartNumb() {
		return cltPartNumb;
	}
	public void setCltPartNumb(String cltPartNumb) {
		this.cltPartNumb = cltPartNumb;
	}
	public String getProcessIn() {
		return processIn;
	}
	public void setProcessIn(String processIn) {
		this.processIn = processIn;
	}
	public String getAttachFile() {
		return attachFile;
	}
	public void setAttachFile(String attachFile) {
		this.attachFile = attachFile;
	}
	public String getIssueto() {
		return issueto;
	}
	public void setIssueto(String issueto) {
		this.issueto = issueto;
	}
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDealstatus() {
		return dealstatus;
	}
	public void setDealstatus(String dealstatus) {
		this.dealstatus = dealstatus;
	}
	public Date getDealtime() {
		return dealtime;
	}
	public void setDealtime(Date dealtime) {
		this.dealtime = dealtime;
	}
	public String getFaillog() {
		return faillog;
	}
	public void setFaillog(String faillog) {
		this.faillog = faillog;
	}
}
