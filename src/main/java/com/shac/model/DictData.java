package com.shac.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "sc_dictdata")
public class DictData implements java.io.Serializable{
	
	private String id;
	private String dictValue;
	private String dictType;
	private DictData parent;
	private String status;
	private Integer order;
	
	private String sent;     //辅助字段,仅用于显示—签收、销毁回收状态
	private Date signTime;   //辅助字段,仅用于显示-签收时间
	private Date destroyTime;//辅助字段,仅用于显示-销毁回收时间
	private String scope;//厂部的发放,范围,比如只发图纸1,或工艺2.或者全部发送0
	
	private String issue;//用于存放不同“技术文件类型”(如-追溯目录)对应的发送范围

	private Integer depno;//厂号,当初用来取号用的
	
	private Boolean alone;//用于标记“厂部”有无下级车间科室,二次发放时需要判断有些厂部没有下级单位

	public DictData(){
		
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
	
	@Column(name = "dictvalue", length = 50)
	public String getDictValue() {
		return dictValue;
	}
	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}
	
	@Column(name = "dicttype", length = 50)
	public String getDictType() {
		return dictType;
	}
	public void setDictType(String dictType) {
		this.dictType = dictType;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "d_parent")
	public DictData getParent() {
		return parent;
	}
	public void setParent(DictData parent) {
		this.parent = parent;
	}
	
	@Column(name = "d_status", length = 4)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "d_order")
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	
	@Column(name = "d_scope", length = 4)
	public String getScope(){
		return scope;
	}
	
	public void setScope(String scope){
		this.scope = scope;
	}

	@Transient
	public String getSent() {
		return sent;
	}

	public void setSent(String sent) {
		this.sent = sent;
	}
	
	@Column(name = "d_issue", length = 1000)
	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}
	
	@Column(name = "d_depno")
	public Integer getDepno() {
		return depno;
	}

	public void setDepno(Integer depno) {
		this.depno = depno;
	}
	
	@Column(name = "alone")
	public Boolean getAlone() {
		return alone;
	}

	public void setAlone(Boolean alone) {
		this.alone = alone;
	}
	
	@Transient
	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	
	@Transient
	public Date getDestroyTime() {
		return destroyTime;
	}

	public void setDestroyTime(Date destroyTime) {
		this.destroyTime = destroyTime;
	}
	
	
}
