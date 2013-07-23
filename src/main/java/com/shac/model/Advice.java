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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "sc_advice")
public class Advice implements java.io.Serializable {
	private String id;
	private User senderid;
	private User receiverid;
	private Date createTime;
	private IssueTask task;
	private String adlevel;
	private String bakeone;
	private String baketwo;
	private String status;
	private String fid;
	private String resultbake;
    
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
	@JoinColumn(name = "senderid")
	public User getSenderid() {
		return senderid;
	}

	public void setSenderid(User senderid) {
		this.senderid = senderid;
	}
    
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "receiverid")
	public User getReceiverid() {
		return receiverid;
	}

	public void setReceiverid(User receiverid) {
		this.receiverid = receiverid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
    
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "tskid")
	public IssueTask getTask() {
		return task;
	}

	public void setTask(IssueTask task) {
		this.task = task;
	}

	public String getAdlevel() {
		return adlevel;
	}

	public void setAdlevel(String adlevel) {
		this.adlevel = adlevel;
	}

	public String getBakeone() {
		return bakeone;
	}

	public void setBakeone(String bakeone) {
		this.bakeone = bakeone;
	}

	public String getBaketwo() {
		return baketwo;
	}

	public void setBaketwo(String baketwo) {
		this.baketwo = baketwo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getResultbake() {
		return resultbake;
	}

	public void setResultbake(String resultbake) {
		this.resultbake = resultbake;
	}

}
