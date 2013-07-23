package com.shac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "sc_issuerule")
public class IssueRule implements java.io.Serializable{
	
	private String id;
	
	private String docType;//0:图纸,1:技术文件 2:工艺文档
	
	private String procMode;//工艺方式
	
	private String procDocClass;//工艺文档分类(工艺文件类型)
	
	private String processIn;//生产状态
	
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
	
	@Column(name="docType",length=10)
	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}
	
	@Column(name="procMode",length=100)
	public String getProcMode() {
		return procMode;
	}

	public void setProcMode(String procMode) {
		this.procMode = procMode;
	}
	
	@Column(name="procDocClass",length=100)
	public String getProcDocClass() {
		return procDocClass;
	}

	public void setProcDocClass(String procDocClass) {
		this.procDocClass = procDocClass;
	}
	
	@Column(name="processIn",length=20)
	public String getProcessIn() {
		return processIn;
	}

	public void setProcessIn(String processIn) {
		this.processIn = processIn;
	}
	
}
