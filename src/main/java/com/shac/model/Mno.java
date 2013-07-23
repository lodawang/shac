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
@Table(name = "sc_mno")
public class Mno implements java.io.Serializable{
	
	private String id;
	
	private User user;
	
	private Date noTime;
	
	private String notype;//编号类型
	
	private String nos;//获得号码
	
	private Integer nocount;
	
	private String jobno;
	
	

	public String getJobno() {
		return jobno;
	}
	public void setJobno(String jobno) {
		this.jobno = jobno;
	}
	public Integer getNocount() {
		return nocount;
	}
	public void setNocount(Integer nocount) {
		this.nocount = nocount;
	}
	
	public String getNos() {
		return nos;
	}
	public void setNos(String nos) {
		this.nos = nos;
	}
	public String getNotype() {
		return notype;
	}
	public void setNotype(String notype) {
		this.notype = notype;
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
	
	@Column(name = "notime", length = 19)
	public Date getNoTime() {
		return noTime;
	}
	public void setNoTime(Date noTime) {
		this.noTime = noTime;
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
}
