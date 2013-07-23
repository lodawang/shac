package com.shac.model;

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
@Table(name = "sc_rulerecipient")
public class RuleRecipient implements java.io.Serializable{
	
	private String id;
	private IssueRule issueRule;
	private DictData factory;//厂部
	private DictData workshop;//车间科室
	
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
	@JoinColumn(name = "rule_id")
	public IssueRule getIssueRule() {
		return issueRule;
	}
	public void setIssueRule(IssueRule issueRule) {
		this.issueRule = issueRule;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "factory_id")
	public DictData getFactory() {
		return factory;
	}
	public void setFactory(DictData factory) {
		this.factory = factory;
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
	
	

}
