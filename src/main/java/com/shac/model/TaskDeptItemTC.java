package com.shac.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "sc_taskDeptItem_tc")
public class TaskDeptItemTC implements Serializable{	
	private String id;
	private DictData dept;
	private IssueTaskTC task;
	private Date createTime;
	private String status;//签收状态  0:未签收   1:已签收   2:已标记 @see TCPortlistener#distribute  3:已销毁
	private String history;//历史
	private String issueType;//发放类型- 1:需要转发到车间
	private DictData workshop;//部门
	private String recover;//历史版本回收（废弃不用了，使用status来表示了）
	private Date signTime; //签收时间
	private Date destroyTime;//回收销毁时间
	
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "cityid")
	public DictData getDept() {
		return dept;
	}
	public void setDept(DictData dept) {
		this.dept = dept;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "tskid")
	public IssueTaskTC getTask() {
		return task;
	}
	
	public void setTask(IssueTaskTC task) {
		this.task = task;
	}
	
	@Column(name = "createTime", nullable = false, length = 19)
	public Date getCreateTime() {
		return createTime;
	}
	
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name="t_status",length=10)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getHistory() {
		return history;
	}
	public void setHistory(String history) {
		this.history = history;
	}
	@Column(name="issueType",length=5)
	public String getIssueType() {
		return issueType;
	}
	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "workshop_id")
	public DictData getWorkshop() {
		return workshop;
	}
	public void setWorkshop(DictData workshop) {
		this.workshop = workshop;
	}
	
	public String getRecover() {
		return recover;
	}
	
	public void setRecover(String recover) {
		this.recover = recover;
	}
	
	@Column(name = "signTime", length = 19)
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	
	@Column(name = "destroyTime", length = 19)
	public Date getDestroyTime() {
		return destroyTime;
	}
	public void setDestroyTime(Date destroyTime) {
		this.destroyTime = destroyTime;
	}
}
