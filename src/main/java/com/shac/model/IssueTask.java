package com.shac.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "sc_issuetask")
public class IssueTask implements Serializable{
	
	private String id;
	private String taskType;//TC触发-ISSUE_TASKTYPE_TC,手动-ISSUE_TASKTYPE_MANU
	private String taskStatus;//发放状态
	private String impStatus;
	private String history;//是否历史版本 1表示是历史版本
	private String updated;//由升级产生
	private Date createTime;
	private Date docDate;
	
	private String docType;//0:图纸,1:技术文件 2:工艺文档
	
	private String partid; //文档"id",对应TC中的物料ID,并不是唯一
	private String docVersion;//文档系统中的版本号
	private String veroftc;//tc中的版本号
	private String drawingNumb;
	private String drawingSize;
	private String assembly;//总成号
	private Boolean assembOrnot;//是否总成
	private String assembTitle;//总成名称
	private Date lastUpdate;
	private Integer drawingPage;
	private String client;
	private String cltPartNumb;
	private String modelCode;
	private String processIn;//生产状态
	private String attachFile;
	
	private String techDocClass;//技术文件文档类型
	
	private String viewFile;
	
	private String adminPrintFile;//相比viewFile文件上多一个水印“纸质文件，仅供参考”
	
	private String printFile;//相比viewFile文件上多“版本:xxxxx/001”
	
	/**
	 * 工艺文档分类
	 */
	private String procDocClass;
	/**
	 * 工艺方式
	 */
	private String procMode;
	/**
	 * 5月2日前的文档标记
	 */
	private String beta;
	
	/**
	 * 技术文档系统分配的文档ID,用以标记文档是否是同一文件的不同版本
	 */
	private String sysUID;
	
	/**
	 * 写入的用户
	 */
	private User user;
	
	/**
	 * 文档状态
	 */
	private String status;
	
	/**
	 * 分发单位临时保存
	 */
    private String taskdep; 
	
    /**
     * 标志TC接口修改为找到变新增的文件
     */
    private String missup;
    
    /**
     * 标志是文档是图纸类型，可附件不是pdf文件
     */
    private String erroattach;
    
    private Set<TaskDeptItem> depitem;
    
    
    @OneToMany(cascade={CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "tskid")
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("id")
	public Set<TaskDeptItem> getDepitem() {
		return depitem;
	}

	public void setDepitem(Set<TaskDeptItem> depitem) {
		this.depitem = depitem;
	}

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

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
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

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getPartid() {
		return partid;
	}

	public void setPartid(String partid) {
		this.partid = partid;
	}

	public String getDocVersion() {
		return docVersion;
	}

	public void setDocVersion(String docVersion) {
		this.docVersion = docVersion;
	}

	public String getDrawingNumb() {
		return drawingNumb;
	}

	public void setDrawingNumb(String drawingNumb) {
		this.drawingNumb = drawingNumb;
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

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Integer getDrawingPage() {
		return drawingPage;
	}

	public void setDrawingPage(Integer drawingPage) {
		this.drawingPage = drawingPage;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getCltPartNumb() {
		return cltPartNumb;
	}

	public void setCltPartNumb(String cltPartNumb) {
		this.cltPartNumb = cltPartNumb;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
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

	public String getTechDocClass() {
		return techDocClass;
	}

	public void setTechDocClass(String techDocClass) {
		this.techDocClass = techDocClass;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getViewFile() {
		return viewFile;
	}

	public void setViewFile(String viewFile) {
		this.viewFile = viewFile;
	}

	public Boolean getAssembOrnot() {
		return assembOrnot;
	}

	public void setAssembOrnot(Boolean assembOrnot) {
		this.assembOrnot = assembOrnot;
	}

	public String getVeroftc() {
		return veroftc;
	}

	public void setVeroftc(String veroftc) {
		this.veroftc = veroftc;
	}

	public String getAssembTitle() {
		return assembTitle;
	}

	public void setAssembTitle(String assembTitle) {
		this.assembTitle = assembTitle;
	}

	@Column(name="IMPSTATUS",length=10)
	public String getImpStatus() {
		return impStatus;
	}

	public void setImpStatus(String impStatus) {
		this.impStatus = impStatus;
	}
	
	@Column(name="procDocClass",length=100)
	public String getProcDocClass() {
		return procDocClass;
	}
	public void setProcDocClass(String procDocClass) {
		this.procDocClass = procDocClass;
	}
	@Column(name="procMode",length=100)
	public String getProcMode() {
		return procMode;
	}

	public void setProcMode(String procMode) {
		this.procMode = procMode;
	}
	@Column(name="beta",length=2)
	public String getBeta() {
		return beta;
	}

	public void setBeta(String beta) {
		this.beta = beta;
	}
	
	@Column(name="docDate",columnDefinition="date")
	public Date getDocDate() {
		return docDate;
	}

	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}
	
	@Column(name="sysUID",length=32)
	public String getSysUID() {
		return sysUID;
	}

	public void setSysUID(String sysUID) {
		this.sysUID = sysUID;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "userid")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getTaskdep() {
		return taskdep;
	}

	public void setTaskdep(String taskdep) {
		this.taskdep = taskdep;
	}
	
	@Column(name="adminPrintFile",length=255)
	public String getAdminPrintFile() {
		return adminPrintFile;
	}

	public void setAdminPrintFile(String adminPrintFile) {
		this.adminPrintFile = adminPrintFile;
	}
	
	public String getMissup() {
		return missup;
	}

	public void setMissup(String missup) {
		this.missup = missup;
	}
	
	
	@Column(name="printFile",length=255)
	public String getPrintFile() {
		return printFile;
	}

	public void setPrintFile(String printFile) {
		this.printFile = printFile;
	}
	
	public String getErroattach() {
		return erroattach;
	}

	public void setErroattach(String erroattach) {
		this.erroattach = erroattach;
	}
}
