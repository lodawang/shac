package com.shac.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;



@Entity
@Table(name = "sc_sno")
@SequenceGenerator(name = "sc_sno_sq",sequenceName = "SC_SNO_SQ",allocationSize=1)
public class Sno implements Serializable{
	
	private int id;
	
	private User user;
	
	private Date noTime;
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO,generator="sc_sno_sq")  
	@Column(name = "id", nullable = false, length = 32)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	@Column(name = "notime", length = 19)
	public Date getNoTime() {
		return noTime;
	}
	public void setNoTime(Date noTime) {
		this.noTime = noTime;
	}
}
