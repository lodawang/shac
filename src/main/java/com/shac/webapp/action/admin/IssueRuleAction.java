package com.shac.webapp.action.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.web.util.WebUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.shac.dao.hibernate.DictDataDaoHibernate;
import com.shac.dao.hibernate.IssueRuleDaoHibernate;
import com.shac.dao.hibernate.RuleRecipientDaoHibernate;
import com.shac.model.DictData;
import com.shac.model.IssueRule;
import com.shac.model.RuleRecipient;
import com.shac.util.Constants;

public class IssueRuleAction extends ActionSupport{
	
	private IssueRuleDaoHibernate issueRuleDao;
	private RuleRecipientDaoHibernate ruleRecipientDao;
	private DictDataDaoHibernate dictDataDao;
	
	private IssueRule issueRule;
	private List ruleList;
	private String id;
	private String orgids;
	
	private List procModeList;
	private List procDocClassList;
	
	@Action(value="issueRuleEdit",results={@Result(name="success",location="issueRuleEdit.jsp")})
	public String edit(){
		procModeList = dictDataDao.findByDictType(Constants.DICTTYPE_PROCMODE);
		procDocClassList = dictDataDao.findByDictType(Constants.DICTTYPE_PROCDOCCLASS);
		if(id!=null){
			issueRule = issueRuleDao.get(id);
		}
		return SUCCESS;
	}
	
	@Action(value="issueRuleList",results={@Result(name="success",location="issueRuleList.jsp")})
	public String list(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Map filterMap = WebUtils.getParametersStartingWith(request, "issueRule.");
		Map orderMap = new HashMap();
		ruleList = issueRuleDao.findByAll(filterMap, orderMap);
		return SUCCESS;
	}
	
	@Action(value="saveIssueRule",results={@Result(name="success",location="issueRuleList.do",type="redirect")},interceptorRefs={@InterceptorRef("params"),@InterceptorRef("defaultStack")})
	public String save(){
		IssueRule ir = issueRuleDao.save(issueRule);
		ruleRecipientDao.deleteByRule(ir.getId());
		String orgidArray[] = orgids.split(",");
		for(String s:orgidArray){
			if(s!=null && !s.equals("")){
				RuleRecipient rr = new RuleRecipient();
				DictData org = dictDataDao.get(s);
				if(org.getDictType().equals(Constants.DICTTYPE_WORKSHOP)){
					rr.setFactory(org.getParent());
					rr.setIssueRule(ir);
					rr.setWorkshop(org);
					ruleRecipientDao.save(rr);
				}
			}
		}
		return SUCCESS;
	}
	
	@Action(value="delIssueRule",results={@Result(name="success",location="issueRuleList.do",type="redirect")},interceptorRefs={@InterceptorRef("params"),@InterceptorRef("defaultStack")})
	public String delete(){
		ruleRecipientDao.deleteByRule(id);
		issueRuleDao.remove(id);
		return SUCCESS;
	}
	
	@Action(value="saveRecipient",results={@Result(name="success",location="issueRuleList.do",type="redirect")},interceptorRefs={@InterceptorRef("params"),@InterceptorRef("defaultStack")})
	public String saveRecipient(){
		return SUCCESS;
	}

	public IssueRuleDaoHibernate getIssueRuleDao() {
		return issueRuleDao;
	}

	public void setIssueRuleDao(IssueRuleDaoHibernate issueRuleDao) {
		this.issueRuleDao = issueRuleDao;
	}

	public RuleRecipientDaoHibernate getRuleRecipientDao() {
		return ruleRecipientDao;
	}

	public void setRuleRecipientDao(RuleRecipientDaoHibernate ruleRecipientDao) {
		this.ruleRecipientDao = ruleRecipientDao;
	}

	public IssueRule getIssueRule() {
		return issueRule;
	}

	public void setIssueRule(IssueRule issueRule) {
		this.issueRule = issueRule;
	}

	public List getRuleList() {
		return ruleList;
	}

	public void setRuleList(List ruleList) {
		this.ruleList = ruleList;
	}

	public DictDataDaoHibernate getDictDataDao() {
		return dictDataDao;
	}

	public void setDictDataDao(DictDataDaoHibernate dictDataDao) {
		this.dictDataDao = dictDataDao;
	}

	public List getProcModeList() {
		return procModeList;
	}

	public void setProcModeList(List procModeList) {
		this.procModeList = procModeList;
	}

	public List getProcDocClassList() {
		return procDocClassList;
	}

	public void setProcDocClassList(List procDocClassList) {
		this.procDocClassList = procDocClassList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrgids() {
		return orgids;
	}

	public void setOrgids(String orgids) {
		this.orgids = orgids;
	}
}
